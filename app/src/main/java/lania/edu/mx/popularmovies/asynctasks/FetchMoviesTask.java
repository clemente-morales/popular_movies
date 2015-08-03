package lania.edu.mx.popularmovies.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Properties;

import lania.edu.mx.popularmovies.PopularMoviesApplication;
import lania.edu.mx.popularmovies.models.DataResult;
import lania.edu.mx.popularmovies.models.Movie;
import lania.edu.mx.popularmovies.models.SortOption;
import lania.edu.mx.popularmovies.net.resources.MoviesResource;
import lania.edu.mx.popularmovies.tos.MovieConverter;
import lania.edu.mx.popularmovies.tos.MovieResponse;

/**
 * Created by clemente on 7/22/15.
 */
public class FetchMoviesTask extends AsyncTask<SortOption, Void, Void> {
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
    protected Void doInBackground(SortOption... params) {
        SortOption sortOption = params[0];
        getRealData(sortOption);
        return null;
    }

    private void getRealData(SortOption sortOption) {
        try {
            MoviesResource resource = PopularMoviesApplication.getObjectGraph().providesMoviesResource();
            MovieResponse response = resource.getMovies(sortOption.getOrder(), getKey());
            ArrayList<Movie> result = MovieConverter.toModel(response);
            DataResult.createDataResult(result);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            DataResult.createExceptionResult(e);
        }
    }

    @NonNull
    private String getKey() {
        Properties properties = PopularMoviesApplication.getObjectGraph().providesProperties();
        return properties.getProperty(MOVIEDB_API_KEY_PROPERTY);
    }
}
