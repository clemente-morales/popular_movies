package lania.edu.mx.popularmovies.fragments;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import lania.edu.mx.popularmovies.PopularMoviesApplication;
import lania.edu.mx.popularmovies.R;
import lania.edu.mx.popularmovies.data.PopularMoviesContract;
import lania.edu.mx.popularmovies.events.otto.MovieSelectionChangeEvent;
import lania.edu.mx.popularmovies.models.Movie;
import lania.edu.mx.popularmovies.tos.MovieConverter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = MovieDetailActivityFragment.class.getSimpleName();
    public static final String DETAIL_URI_KEY = "MovieUri";
    private Uri mUri;
    private static final int DETAIL_LOADER = 22;

    private static final String[] MOVIE_COLUMNS = {
            PopularMoviesContract.MovieEntry.ID,
            PopularMoviesContract.MovieEntry.COLUMN_BACKDROP_IMAGE,
            PopularMoviesContract.MovieEntry.COLUMN_POPULARITY,
            PopularMoviesContract.MovieEntry.COLUMN_POSTER_IMAGE,
            PopularMoviesContract.MovieEntry.COLUMN_RELEASE_DATE,
            PopularMoviesContract.MovieEntry.COLUMN_SYNOPSIS,
            PopularMoviesContract.MovieEntry.COLUMN_TITLE,
            PopularMoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE
    };
    private ImageView thumbailImage;
    private TextView title;
    private TextView synopsis;
    private TextView release;
    private RatingBar raitingBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(DETAIL_URI_KEY);
        }
        Log.d(TAG, "Uri " + mUri);
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        thumbailImage = (ImageView) view.findViewById(R.id.movieThumbailImageView);
        title = (TextView) view.findViewById(R.id.movieTitleTextView);
        synopsis = (TextView) view.findViewById(R.id.movieSynopsisTextView);
        release = (TextView) view.findViewById(R.id.movieReleaseDateTextView);
        raitingBar = (RatingBar) view.findViewById(R.id.movieRatingBar);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated ");
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        PopularMoviesApplication.getEventBus().register(this);
    }

    @Override
    public void onStop() {
        PopularMoviesApplication.getEventBus().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onMovieSelectionChange(MovieSelectionChangeEvent event) {
        mUri = event.getMovieUri();
        getLoaderManager().restartLoader(DETAIL_LOADER, null, this);
    }

    /**
     * Allows to dipslay the detail of a movie.
     */
    private void displayData(Movie movie) {
        displayImage(movie.getPosterImageName(), thumbailImage);
        title.setText(movie.getTitle());
        synopsis.setText(movie.getSynopsis());
        release.setText(formatDate(movie.getReleaseDate()));
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
        return null != releaseDate ? simpleDateFormat.format(releaseDate) : null;
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != mUri) {
            String selection = PopularMoviesContract.MovieEntry.ID + "=?";
            String[] selectionArgs = new String[1];
            selectionArgs[0] = "" + ContentUris.parseId(mUri);
            return new CursorLoader(getActivity(), mUri, MOVIE_COLUMNS, selection, selectionArgs, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        if (null != data && data.moveToFirst()) {
            Log.d(TAG, "onLoadFinished model " + MovieConverter.toModel(data));
            displayData(MovieConverter.toModel(data));
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }

}
