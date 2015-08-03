package lania.edu.mx.popularmovies.events.otto;

import java.util.ArrayList;

import lania.edu.mx.popularmovies.models.DataResult;
import lania.edu.mx.popularmovies.models.Movie;

/**
 * Created by clemente on 8/3/15.
 */
public final class FinishingFetchingMoviesEvent {
    public final DataResult<ArrayList<Movie>, Exception> result;

    public FinishingFetchingMoviesEvent(DataResult<ArrayList<Movie>, Exception> result) {
        this.result = result;
    }

    public DataResult<ArrayList<Movie>, Exception> getResult() {
        return result;
    }
}
