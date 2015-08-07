package lania.edu.mx.popularmovies.services;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

import lania.edu.mx.popularmovies.R;
import lania.edu.mx.popularmovies.data.PopularMoviesContract;
import lania.edu.mx.popularmovies.models.Movie;
import lania.edu.mx.popularmovies.tos.MovieConverter;
import lania.edu.mx.popularmovies.utils.IOHelper;

/**
 * Created by clemente on 8/5/15.
 */
public class MoviesService extends IntentService {

    private static final String TAG = MoviesService.class.getSimpleName();

    public static final String MOVIE_DATA_KEY = "MovieData";
    public static final String MOVIE_IMAGE_URL_FORMAT = "http://image.tmdb.org/t/p/w185/%s";


    private Movie mMovie;

    private String currentImageFile;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MoviesService() {
        super(TAG);
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            File folder = IOHelper.getDataFolder(getApplicationContext(), Movie.IMAGE_PATH);
            File imageFile = new File(folder, currentImageFile);
            try {
                imageFile.createNewFile();
                FileOutputStream stream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.close();
            } catch (Exception exception) {
                Log.e(TAG, exception.getMessage(), exception);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

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
            saveImage(mMovie.getPosterImageName());

            saveImage(mMovie.getBackDropImageName());

            getApplicationContext().getContentResolver().insert(PopularMoviesContract.MovieEntry.CONTENT_URI,
                    MovieConverter.toContentValues(mMovie));

            notifyResult(R.string.movieMarkAsFavorite);
        } else {
            notifyResult(R.string.movieAlreadyMarkAsFavorite);
        }
        movieCursor.close();
    }

    private void saveImage(final String image) {

        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                currentImageFile = image;
                Picasso.with(getApplicationContext()).load(String.format(
                        MOVIE_IMAGE_URL_FORMAT, image)).into(target);
            }
        });
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
