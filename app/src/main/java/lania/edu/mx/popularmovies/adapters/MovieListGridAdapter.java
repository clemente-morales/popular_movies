package lania.edu.mx.popularmovies.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Collections;
import java.util.List;

import lania.edu.mx.popularmovies.models.Movie;
import lania.edu.mx.popularmovies.utils.IOHelper;

/**
 * Adapter used to display images in the Grid View for the requested movies.
 * Created by clemente on 7/29/15.
 */
public class MovieListGridAdapter extends BaseAdapter {
    /**
     * Id for the class in the log of events.
     */
    private static final String TAG = MovieListGridAdapter.class.getSimpleName();

    /**
     * Format to query the image for a movie.
     */
    public static final String MOVIE_IMAGE_URL_FORMAT = "http://image.tmdb.org/t/p/w185/%s";

    /**
     * Context to inflate the views.
     */
    private final Context context;

    /**
     * Movie list to display.
     */
    private List<Movie> movies = Collections.emptyList();

    /**
     * Allows to create an instance of this class to provide the views for each element of a movie in
     * request from a list view in each movie item.
     *
     * @param context Context of the application.
     * @param movies  Movie list to display.
     */
    public MovieListGridAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        Movie movie = movies.get(position);
        Log.d(TAG, "Displaying the movie "+movie);
        if (movie.isMarkedAsFavorite()) {
            File folder = IOHelper.getDataFolder(context, Movie.IMAGE_PATH);
            File imageFile = new File(folder, movie.getPosterImageName());
            Log.d(TAG, "" + imageFile);
            Picasso.with(context).load(imageFile).into(imageView);
        } else {
            Log.d(TAG, "Displaying image "+movie.getPosterImageName());
            displayImage(String.format(MOVIE_IMAGE_URL_FORMAT, movie.getPosterImageName()), imageView);
            Log.d(TAG, "image displayed");
        }

        return imageView;
    }

    /**
     * Allows to display the movie thumbail image
     *
     * @param imagePath Path to the image to load.
     * @param imageView Control to display the thumbail image for the movie
     */
    private void displayImage(String imagePath, ImageView imageView) {
        Picasso.with(context).load(imagePath).into(imageView);
    }
}