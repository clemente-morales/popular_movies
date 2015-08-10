package lania.edu.mx.popularmovies.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Properties;

import lania.edu.mx.popularmovies.PopularMoviesApplication;
import lania.edu.mx.popularmovies.models.DataResult;
import lania.edu.mx.popularmovies.models.Movie;
import lania.edu.mx.popularmovies.net.resources.MoviesResource;
import lania.edu.mx.popularmovies.tos.MovieConverter;
import lania.edu.mx.popularmovies.tos.ReviewsResponse;
import lania.edu.mx.popularmovies.tos.VideosResponse;

/**
 * Created by clemente on 8/5/15.
 */
public class FetchMovieDetailTask extends AsyncTask<Movie, Void, DataResult<Movie, Exception>> {
    private static final String TAG = FetchMoviesTask.class.getSimpleName();
    public static final String MOVIEDB_API_KEY_PROPERTY = "themoviedb_api_key";
    private final Context context;

    private MovieDetailListener movieListener;

    public interface MovieDetailListener {
        void onPreExecute();

        void update(DataResult<Movie, Exception> data);
    }

    public FetchMovieDetailTask(Context context, MovieDetailListener movieListener) {
        this.context = context;
        this.movieListener = movieListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        movieListener.onPreExecute();
    }

    @Override
    protected DataResult<Movie, Exception> doInBackground(Movie... params) {
        Movie sortOption = params[0];
        return getRealData(sortOption);
    }

    private DataResult<Movie, Exception> getRealData(Movie movie) {
        try {
            MoviesResource resource = PopularMoviesApplication.getObjectGraph().providesMoviesResource();

            ReviewsResponse reviewsResponse = resource.getReviews(movie.getId(), getKey());
            VideosResponse videosResponse = resource.getVideos(movie.getId(), getKey());
            movie.setReviews(MovieConverter.toModel(reviewsResponse));
            movie.setVideos(MovieConverter.toModel(videosResponse));
            return DataResult.createDataResult(movie);
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
    protected void onPostExecute(DataResult<Movie, Exception> moviesResult) {
        super.onPostExecute(moviesResult);
        movieListener.update(moviesResult);
    }
}
