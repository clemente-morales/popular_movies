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
import lania.edu.mx.popularmovies.models.Review;
import lania.edu.mx.popularmovies.models.Video;
import lania.edu.mx.popularmovies.net.resources.MoviesResource;
import lania.edu.mx.popularmovies.tos.ReviewConverter;
import lania.edu.mx.popularmovies.tos.ReviewsResponse;
import lania.edu.mx.popularmovies.tos.VideoConverter;
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
        Movie movie = params[0];
        return movie.isMarkedAsFavorite() ? queryDataFromLocalDb(movie) : queryDataFromServer(movie);
    }

    private DataResult<Movie, Exception> queryDataFromLocalDb(Movie movie) {
        movie.setVideos(getVideos(movie));
        movie.setReviews(getReviews(movie));
        return DataResult.createDataResult(movie);
    }

    private ArrayList<Review> getReviews(Movie movie) {
        final String[] REVIEW_COLUMNS = {
                PopularMoviesContract.VideoEntry.ID,
                PopularMoviesContract.VideoEntry.COLUMN_MOVIE_ID,
                PopularMoviesContract.ReviewEntry.COLUMN_AUTHOR,
                PopularMoviesContract.ReviewEntry.COLUMN_CONTENT
        };

        String selectionClause = PopularMoviesContract.ReviewEntry.COLUMN_MOVIE_ID +"=?";
        String[] selectionArgs = new String[1];
        selectionArgs[0] = Integer.toString(movie.getId());

        Cursor reviewCursor = context.getContentResolver().query(PopularMoviesContract.ReviewEntry.buildMoviePathWithReviews(movie.getId()),REVIEW_COLUMNS,
                selectionClause, selectionArgs, null);

        ArrayList<Review> reviews = new ArrayList<>();
        while(reviewCursor.moveToNext()) {
            reviews.add(ReviewConverter.toModel(reviewCursor));
        }
        reviewCursor.close();
        return reviews;
    }

    @NonNull
    private ArrayList<Video> getVideos(Movie movie) {
        final String[] VIDEO_COLUMNS = {
                PopularMoviesContract.VideoEntry.ID,
                PopularMoviesContract.VideoEntry.COLUMN_MOVIE_ID,
                PopularMoviesContract.VideoEntry.COLUMN_NAME,
                PopularMoviesContract.VideoEntry.COLUMN_KEY
        };

        String selectionClause = PopularMoviesContract.ReviewEntry.COLUMN_MOVIE_ID +"=?";
        String[] selectionArgs = new String[1];
        selectionArgs[0] = Integer.toString(movie.getId());

        Cursor videoCursor = context.getContentResolver().query(PopularMoviesContract.VideoEntry.buildMoviePathWithVideos(movie.getId()),VIDEO_COLUMNS,
                selectionClause, selectionArgs, null);

        ArrayList<Video> videos = new ArrayList<>();
        while(videoCursor.moveToNext()) {
            videos.add(VideoConverter.toModel(videoCursor));
        }
        videoCursor.close();
        return videos;
    }

    private DataResult<Movie, Exception> queryDataFromServer(Movie movie) {
        try {
            MoviesResource resource = PopularMoviesApplication.getObjectGraph().providesMoviesResource();

            ReviewsResponse reviewsResponse = resource.getReviews(movie.getId(), getKey());
            VideosResponse videosResponse = resource.getVideos(movie.getId(), getKey());
            movie.setReviews(ReviewConverter.toModel(Integer.toString(movie.getId()), reviewsResponse));
            movie.setVideos(VideoConverter.toModel(Integer.toString(movie.getId()), videosResponse));
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
