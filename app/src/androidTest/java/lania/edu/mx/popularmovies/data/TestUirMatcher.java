package lania.edu.mx.popularmovies.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

/**
 * Created by clemente on 7/31/15.
 */
public class TestUirMatcher extends AndroidTestCase {
    private static final Uri TEST_WEATHER_DIR = PopularMoviesContract.MovieEntry.CONTENT_URI;

    public void testUriMatcher() {
        UriMatcher testMatcher = PopularMoviesProvider.buildUriMatcher();
        assertEquals("Error: The MOVIE URI was matched incorrectly.",
                testMatcher.match(TEST_WEATHER_DIR), PopularMoviesProvider.MOVIE);
    }
}
