package lania.edu.mx.popularmovies.models;

import java.util.ArrayList;
import java.util.List;

import lania.edu.mx.popularmovies.tos.MovieResponse;

/**
 * Created by clemente on 7/23/15.
 */
public final class MovieConverter {
    private MovieConverter() {

    }

    public static Movie toModel(lania.edu.mx.popularmovies.tos.Movie movie) {
        return new Movie(movie.getTitle(), movie.getPosterImage(), movie.getOverview(), movie.getPopulariy(), movie.getReleaseDate());
    }

    public static List<Movie> toModel(MovieResponse response) {
        List<Movie> movies = new ArrayList<Movie>();

        for (lania.edu.mx.popularmovies.tos.Movie movie : response.getMovies()) {
            movies.add(toModel(movie));
        }

        return movies;
    }
}
