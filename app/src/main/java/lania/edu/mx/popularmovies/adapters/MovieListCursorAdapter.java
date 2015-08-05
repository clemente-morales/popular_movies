package lania.edu.mx.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import lania.edu.mx.popularmovies.fragments.MovieListFragment;

/**
 * Created by clerks on 8/1/15.
 */
public class MovieListCursorAdapter extends CursorAdapter {
    private final Context context;

    public MovieListCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ImageView imageView;
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
        String posterImage = cursor.getString(MovieListFragment.COLUMN_POSTER_IMAGE_INDEX);
        displayImage(posterImage, imageView);

        return imageView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
    }

    /**
     * Allows to display the movie thumbail image
     *
     * @param imageName Name of the image to display.
     * @param imageView Control to display the thumbail image for the movie
     */
    private void displayImage(String imageName, ImageView imageView) {
        Picasso.with(context).load(String.format("http://image.tmdb.org/t/p/w185/%s", imageName)).into(imageView);
    }
}
