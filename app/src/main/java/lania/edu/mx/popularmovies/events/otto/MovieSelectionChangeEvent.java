package lania.edu.mx.popularmovies.events.otto;

import lania.edu.mx.popularmovies.models.Movie;

/**
 * Created by clemente on 8/3/15.
 */
public class MovieSelectionChangeEvent {
    private final Movie movieUri;

    public MovieSelectionChangeEvent(Movie movieUri) {
        this.movieUri = movieUri;
    }

    public Movie getMovieUri() {
        return movieUri;
    }
}
