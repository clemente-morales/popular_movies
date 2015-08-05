package lania.edu.mx.popularmovies.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import lania.edu.mx.popularmovies.models.Movie;

/**
 * Created by clemente on 7/29/15.
 */
public class MovieListGridAdapter extends BaseAdapter {
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
        displayImage(movie.getPosterImageName(), imageView);

        return imageView;
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