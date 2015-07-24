package lania.edu.mx.popularmovies.tos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clemente on 7/23/15.
 */
public final class MovieConverter {
    private MovieConverter() {

    }

    public static lania.edu.mx.popularmovies.models.Movie toModel(lania.edu.mx.popularmovies.tos.Movie movie) {
        return new lania.edu.mx.popularmovies.models.Movie(movie.getTitle(), movie.getPosterImage(), movie.getOverview(),
                movie.getPopulariy(), movie.getReleaseDate(), movie.getBackDropImage(), movie.getLanguage());
    }

    public static List<lania.edu.mx.popularmovies.models.Movie> toModel(MovieResponse response) {
        List<lania.edu.mx.popularmovies.models.Movie> movies = new ArrayList<lania.edu.mx.popularmovies.models.Movie>();

        for (lania.edu.mx.popularmovies.tos.Movie movie : response.getMovies()) {
            movies.add(toModel(movie));
        }

        return movies;
    }
}
