package lania.edu.mx.popularmovies.asynctasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lania.edu.mx.popularmovies.PopularMoviesApplication;
import lania.edu.mx.popularmovies.data.PopularMoviesContract;
import lania.edu.mx.popularmovies.models.DataResult;
import lania.edu.mx.popularmovies.models.Movie;
import lania.edu.mx.popularmovies.models.SortOption;
import lania.edu.mx.popularmovies.net.resources.MoviesResource;
import lania.edu.mx.popularmovies.tos.MovieConverter;
import lania.edu.mx.popularmovies.tos.MovieResponse;

/**
 * Created by clemente on 7/22/15.
 */
public class FetchMoviesTask extends AsyncTask<SortOption, Void, DataResult<ArrayList<Movie>, Exception>> {
    private static final String TAG = FetchMoviesTask.class.getSimpleName();
    public static final String BASE_URI_TO_DISCOVER_MOVIES = "http://api.themoviedb.org/3/discover/movie";
    public static final String SORT_BY_PARAMETER = "sort_by";
    public static final String API_KEY_PARAMETER = "api_key";
    public static final String MOVIEDB_API_KEY_PROPERTY = "themoviedb_api_key";
    private final Context context;

    public FetchMoviesTask(Context context) {
        this.context = context;
    }

    @Override
    protected DataResult<ArrayList<Movie>, Exception> doInBackground(SortOption... params) {
        SortOption sortOption = params[0];
        DataResult<ArrayList<Movie>, Exception> result = getRealData(sortOption);

        if (!result.isException()) {
            insertDataInDb(result.getData());
        }
        return result;
    }

    private void insertDataInDb(ArrayList<Movie> movies) {
        List<ContentValues> data = new ArrayList<>();
        String[] projection = new String[]{PopularMoviesContract.MovieEntry.ID};
        String selection = PopularMoviesContract.MovieEntry.ID + "=?";
        String[] selectionArgs = new String[1];
        String order = null;

        for (Movie movie : movies) {
            selectionArgs[0] = "" + movie.getId();
            Cursor movieCursor = context.getContentResolver().query(PopularMoviesContract.MovieEntry.CONTENT_URI, projection, selection, selectionArgs, order);
            if (!movieCursor.moveToFirst()) {
                data.add(MovieConverter.toContentValues(movie));
            }
        }

        context.getContentResolver().bulkInsert(PopularMoviesContract.MovieEntry.CONTENT_URI, (ContentValues[]) data.toArray());
    }

    private DataResult<ArrayList<Movie>, Exception> getRealData(SortOption sortOption) {
        try {
            MoviesResource resource = PopularMoviesApplication.getObjectGraph().providesMoviesResource();
            MovieResponse response = resource.getMovies(sortOption.getOrder(), getKey());
            ArrayList<Movie> result = MovieConverter.toModel(response);
            return DataResult.createDataResult(result);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return DataResult.createExceptionResult(e);
        }
    }

    @NonNull
    private String getKey() {
        Properties properties = PopularMoviesApplication.getObjectGraph().providesProperties();
        return properties.getProperty(MOVIEDB_API_KEY_PROPERTY);
    }

    @Override
    protected void onPostExecute(DataResult<ArrayList<Movie>, Exception> result) {
        PopularMoviesApplication.getPopularMoviesApplication(context).getEventBus().post(result);
    }
}
