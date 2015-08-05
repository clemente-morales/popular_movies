package lania.edu.mx.popularmovies.services;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.widget.Toast;

import lania.edu.mx.popularmovies.R;
import lania.edu.mx.popularmovies.data.PopularMoviesContract;
import lania.edu.mx.popularmovies.models.Movie;
import lania.edu.mx.popularmovies.tos.MovieConverter;

/**
 * Created by clemente on 8/5/15.
 */
public class MoviesService extends IntentService {

    private static final String TAG = MoviesService.class.getSimpleName();

    public static final String MOVIE_DATA_KEY = "MovieData";

    private Movie mMovie;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MoviesService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mMovie = intent.getParcelableExtra(MOVIE_DATA_KEY);

        String[] projection = new String[]{PopularMoviesContract.MovieEntry.ID};
        String selection = PopularMoviesContract.MovieEntry.ID + "=?";
        String[] selectionArgs = new String[1];
        String order = null;

        selectionArgs[0] = "" + mMovie.getId();
        Cursor movieCursor = getApplicationContext().getContentResolver().query(PopularMoviesContract.MovieEntry.CONTENT_URI,
                projection, selection, selectionArgs, order);
        if (!movieCursor.moveToFirst()) {
            getApplicationContext().getContentResolver().insert(PopularMoviesContract.MovieEntry.CONTENT_URI, MovieConverter.toContentValues(mMovie));

            notifyResult(R.string.movieMarkAsFavorite);
        } else {
            notifyResult(R.string.movieAlreadyMarkAsFavorite);
        }
        movieCursor.close();
    }

    private void notifyResult(final int messageId) {
        final String message = String.format(getString(messageId), mMovie.getTitle());
        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
