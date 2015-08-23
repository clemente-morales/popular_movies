package lania.edu.mx.popularmovies.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

/**
 * Created by clemente on 7/31/15.
 */
public class TestUirMatcher extends AndroidTestCase {
    private static final Uri TEST_MOVIE_DIR = PopularMoviesContract.MovieEntry.CONTENT_URI;
    public static final String ROW_ID = "23";

    public void testUriMatcher() {
        UriMatcher testMatcher = PopularMoviesProvider.buildUriMatcher();
        assertEquals("Error: The MOVIE URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_DIR), PopularMoviesProvider.MOVIE);
        assertEquals("Error: The MOVIE_BY_ID URI was matched incorrectly.",
                testMatcher.match(PopularMoviesContract.MovieEntry.buildMovieUri(ROW_ID)),PopularMoviesProvider.MOVIE_BY_ID );
    }
}
