package lania.edu.mx.popularmovies.tos;

import android.content.ContentValues;

import java.util.ArrayList;

import lania.edu.mx.popularmovies.data.PopularMoviesContract;

/**
 * Created by clemente on 7/23/15.
 */
public final class MovieConverter {
    private MovieConverter() {

    }

    public static lania.edu.mx.popularmovies.models.Movie toModel(lania.edu.mx.popularmovies.tos.Movie movie) {
        return new lania.edu.mx.popularmovies.models.Movie(movie.getId(), movie.getTitle(), movie.getPosterImage(), movie.getOverview(),
                movie.getPopulariy(), movie.getReleaseDate(), movie.getBackDropImage(), movie.getLanguage(), movie.getVoteAverage());
    }

    public static ArrayList<lania.edu.mx.popularmovies.models.Movie> toModel(MovieResponse response) {
        ArrayList<lania.edu.mx.popularmovies.models.Movie> movies = new ArrayList<lania.edu.mx.popularmovies.models.Movie>();

        for (lania.edu.mx.popularmovies.tos.Movie movie : response.getMovies()) {
            movies.add(toModel(movie));
        }

        return movies;
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
