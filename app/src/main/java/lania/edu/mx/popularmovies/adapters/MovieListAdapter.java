package lania.edu.mx.popularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lania.edu.mx.popularmovies.R;
import lania.edu.mx.popularmovies.models.Movie;

/**
 * Provides the list view with the view corresponding to each element of a movie.
 * Created by clemente on 7/22/15.
 */
public class MovieListAdapter extends BaseAdapter {
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
    public MovieListAdapter(Context context, List<Movie> movies) {
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
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_item_movie, null);
            holder = new ViewHolder();
            holder.thumbailImage = (ImageView) convertView.findViewById(
                    R.id.movieThumbailImageView);
            holder.title = (TextView) convertView.findViewById(R.id.movieTitleTextView);
            holder.synopsis = (TextView) convertView.findViewById(R.id.movieSynopsisTextView);
            holder.release = (TextView) convertView.findViewById(R.id.movieReleaseDateTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        holder.synopsis.setText(movie.getSynopsis());
        holder.release.setText(formatDate(movie.getReleaseDate()));
        holder.raitingBar.setRating(movie.getRaiting());
        displayImage(movie.getImageName());

        return convertView;
    }

    /**
     * Allows to display the movie thumbail image
     * @param imageName Name of the image to display.
     */
    private void displayImage(String imageName) {
        // TODO Display image with picasso.
    }

    /**
     * Format {@link Date} to dd/MM/yyyy.
     * @param releaseDate Release date of a movie.
     * @return Release date in the format dd/MM/yyyy.
     */
    private String formatDate(Date releaseDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(releaseDate);
    }

    /**
     * Encapsulates the view controls that represent a movie. This is an element of the ViewHolder pattern
     * to limit the number of calls to findViewByID
     */
    private static class ViewHolder {
        /**
         * Thumbail image for the movie.
         */
        private ImageView thumbailImage;

        /**
         * Original title of the movie.
         */
        private TextView title;

        /**
         * Synopsis of the movie.
         */
        private TextView synopsis;

        /**
         * Release date of the movie.
         */
        private TextView release;

        /**
         * Raiting of the movie.
         */
        private RatingBar raitingBar;
    }
}
