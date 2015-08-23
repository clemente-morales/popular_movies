package lania.edu.mx.popularmovies.tos;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lania.edu.mx.popularmovies.data.PopularMoviesContract;

/**
 * Created by clemente on 7/23/15.
 */
public final class MovieConverter {
    private static final String TAG = MovieConverter.class.getSimpleName();

    public static int COLUMN_ID_INDEX = 0;
    public static int COLUMN_BACKDROG_IMAGE_INDEX = 1;
    public static int COLUMN_POPULARITY_INDEX = 2;
    public static int COLUMN_POSTER_IMAGE_INDEX = 3;
    public static int COLUMN_RELEASE_DATE_INDEX = 4;
    public static int COLUMN_SYNOPSIS_INDEX = 5;
    public static int COLUMN_TITLE_INDEX = 6;
    public static int COLUMN_VOTE_AVERAGE_INDEX = 7;

    private MovieConverter() {
    }

    public static lania.edu.mx.popularmovies.models.Movie toModel(lania.edu.mx.popularmovies.tos.Movie movie) {
        return new lania.edu.mx.popularmovies.models.Movie(movie.getId(), movie.getTitle(), movie.getPosterImage(), movie.getOverview(),
                movie.getPopulariy(), movie.getReleaseDate(), movie.getBackDropImage(), movie.getVoteAverage());
    }

    public static ArrayList<lania.edu.mx.popularmovies.models.Movie> toModel(MovieResponse response) {
        ArrayList<lania.edu.mx.popularmovies.models.Movie> movies = new ArrayList<lania.edu.mx.popularmovies.models.Movie>();

        for (lania.edu.mx.popularmovies.tos.Movie movie : response.getMovies()) {
            movies.add(toModel(movie));
        }

        return movies;
    }

    public static lania.edu.mx.popularmovies.models.Movie toModel(Cursor cursor) {
        return new lania.edu.mx.popularmovies.models.Movie(cursor.getInt(COLUMN_ID_INDEX), cursor.getString(COLUMN_TITLE_INDEX),
                cursor.getString(COLUMN_POSTER_IMAGE_INDEX),
                cursor.getString(COLUMN_SYNOPSIS_INDEX), cursor.getFloat(COLUMN_POPULARITY_INDEX),
                toDate(cursor.getString(COLUMN_RELEASE_DATE_INDEX)), cursor.getString(COLUMN_BACKDROG_IMAGE_INDEX),
                cursor.getFloat(COLUMN_VOTE_AVERAGE_INDEX));
    }

    private static Date toDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    public static ContentValues toContentValues(lania.edu.mx.popularmovies.models.Movie movie) {
        ContentValues values = new ContentValues();
        values.put(PopularMoviesContract.MovieEntry.ID, movie.getId());
        values.put(PopularMoviesContract.MovieEntry.COLUMN_BACKDROP_IMAGE, movie.getBackDropImageName());
        values.put(PopularMoviesContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
        values.put(PopularMoviesContract.MovieEntry.COLUMN_POSTER_IMAGE, movie.getPosterImageName());
        values.put(PopularMoviesContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getFormatReleaseDate());
        values.put(PopularMoviesContract.MovieEntry.COLUMN_SYNOPSIS, movie.getSynopsis());
        values.put(PopularMoviesContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        values.put(PopularMoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        return values;
    }


}
