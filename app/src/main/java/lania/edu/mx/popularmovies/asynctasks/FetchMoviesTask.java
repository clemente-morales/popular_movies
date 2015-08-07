package lania.edu.mx.popularmovies.asynctasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
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
    public static final String MOVIEDB_API_KEY_PROPERTY = "themoviedb_api_key";
    private final Context context;

    private MovieListener movieListener;

    public interface MovieListener {
        void onPreExecute();

        void update(DataResult<ArrayList<Movie>, Exception> data);
    }

    public FetchMoviesTask(Context context, MovieListener movieListener) {
        this.context = context;
        this.movieListener = movieListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        movieListener.onPreExecute();
    }

    @Override
    protected DataResult<ArrayList<Movie>, Exception> doInBackground(SortOption... params) {
        SortOption sortOption = params[0];
        return sortOption == SortOption.FAVORITE ? getFavoriteMovies() : getRealData(sortOption);
    }

    public DataResult<ArrayList<Movie>,Exception> getFavoriteMovies() {

        final String[] MOVIE_COLUMNS = {
                PopularMoviesContract.MovieEntry.ID,
                PopularMoviesContract.MovieEntry.COLUMN_BACKDROP_IMAGE,
                PopularMoviesContract.MovieEntry.COLUMN_POPULARITY,
                PopularMoviesContract.MovieEntry.COLUMN_POSTER_IMAGE,
                PopularMoviesContract.MovieEntry.COLUMN_RELEASE_DATE,
                PopularMoviesContract.MovieEntry.COLUMN_SYNOPSIS,
                PopularMoviesContract.MovieEntry.COLUMN_TITLE,
                PopularMoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE
        };

        Cursor movieCursor = context.getContentResolver().query(PopularMoviesContract.MovieEntry.CONTENT_URI, MOVIE_COLUMNS, null, null, null);

        ArrayList<Movie> movies = new ArrayList<>();
        while(movieCursor.moveToNext()) {
            Movie movie = MovieConverter.toModel(movieCursor);
            movie.setMarkedAsFavorite(true);
            movies.add(movie);
        }
        movieCursor.close();

        return DataResult.createDataResult(movies);
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
    protected void onPostExecute(DataResult<ArrayList<Movie>, Exception> moviesResult) {
        super.onPostExecute(moviesResult);
        movieListener.update(moviesResult);
    }
}