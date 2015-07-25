package lania.edu.mx.popularmovies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import lania.edu.mx.popularmovies.R;
import lania.edu.mx.popularmovies.models.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    private Movie movie;

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restoreState(savedInstanceState);
        extractMovieDetailDataFromIntent();
        displayData();
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState==null) {
            return;
        }

        this.movie = savedInstanceState.getParcelable("MovieSerializationId");
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("MovieSerializationId", movie);
    }

    /**
     * Allows to dipslay the detail of a movie.
     */
    private void displayData() {
        ImageView thumbailImage = (ImageView) getActivity().findViewById(R.id.movieThumbailImageView);
        displayImage(movie.getPosterImageName(), thumbailImage);

        TextView title = (TextView) getActivity().findViewById(R.id.movieTitleTextView);
        title.setText(movie.getTitle());

        TextView synopsis = (TextView) getActivity().findViewById(R.id.movieSynopsisTextView);
        synopsis.setText(movie.getSynopsis());

        TextView release = (TextView) getActivity().findViewById(R.id.movieReleaseDateTextView);
        release.setText(formatDate(movie.getReleaseDate()));

        RatingBar raitingBar = (RatingBar) getActivity().findViewById(R.id.movieRatingBar);
        raitingBar.setRating(movie.getPopularity());
    }

    /**
     * Format {@link Date} to dd/MM/yyyy.
     *
     * @param releaseDate Release date of a movie.
     * @return Release date in the format dd/MM/yyyy.
     */
    private String formatDate(Date releaseDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(releaseDate);
    }

    /**
     * Allows to display the movie thumbail image
     *
     * @param imageName Name of the image to display.
     * @param imageView Control to display the thumbail image for the movie
     */
    private void displayImage(String imageName, ImageView imageView) {
        Picasso.with(getActivity()).load(String.format("http://image.tmdb.org/t/p/w185/%s", imageName)).into(imageView);
    }

    /**
     * It extract the data from the Intent.
     */
    private void extractMovieDetailDataFromIntent() {
        Intent intent = getActivity().getIntent();
        this.movie = intent.getParcelableExtra(MovieListFragment.MOVIE_DATA_EXTRA);
    }
}
