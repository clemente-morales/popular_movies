package lania.edu.mx.popularmovies.events.otto;

import android.net.Uri;

/**
 * Created by clemente on 8/3/15.
 */
public class MovieSelectionChangeEvent {
    private final Uri movieUri;

    public MovieSelectionChangeEvent(Uri movieUri) {
        this.movieUri = movieUri;
    }

    public Uri getMovieUri() {
        return movieUri;
    }
}
