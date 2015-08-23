package lania.edu.mx.popularmovies.data;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

import lania.edu.mx.popularmovies.utils.PollingCheck;

/**
 * Utility class to test the database.
 * Created by clemente on 7/31/15.
 */
public class TestUtilities extends AndroidTestCase {
    /**
     * Allows to test if a result of a query has the expected values.
     * @param error Error to display it the projection, doesn't include a value.
     * @param valueCursor Current result of a query.
     * @param expectedValues Values to look for in a cursor.
     */
    public static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    /**
     * Allows to validate a cursor with the expected values that it needs to carry.
     * @param error Error to display in case of a value or column doesn't match.
     * @param valueCursor Current cursor.
     * @param expectedValues Expected values for the cursor.
     */
    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    /**
     * Allows to create a dummy value to insert for a movie.
     * @return Dummy value to insert for a movie.
     */
    public static ContentValues createMovieDummyValues() {
        return createMovieDummyValues(135397);
    }

    /**
     * Allows to create a bunch of dummy values to insert.
     * @return Bunch of dummy values to insert.
     */
    public static ContentValues[] createBulkDummyInsert() {
        ContentValues[] values = new ContentValues[5];

        int movieIdBase = 135397;
        for (int i = 0; i < 5; i++) {
            values[i] = createMovieDummyValues(++movieIdBase);
        }

        return values;
    }

    /**
     * Allows to create a dummy movie to insert.
     * @param movieId Id of the movie.
     * @return ContentValue with the data of the movie.
     */
    private static ContentValues createMovieDummyValues(int movieId) {
        ContentValues values = new ContentValues();
        values.put(PopularMoviesContract.MovieEntry.ID, movieId);
        values.put(PopularMoviesContract.MovieEntry.COLUMN_BACKDROP_IMAGE, "dkMD5qlogeRMiEixC4YNPUvax2T.jpg");
        // TODO check this test, because it looks like the Real Data Type only allows 4 decimal places
        values.put(PopularMoviesContract.MovieEntry.COLUMN_POPULARITY, 62.5871);
        values.put(PopularMoviesContract.MovieEntry.COLUMN_POSTER_IMAGE, "uXZYawqUsChGSj54wcuBtEdUJbh.jpg");
        values.put(PopularMoviesContract.MovieEntry.COLUMN_RELEASE_DATE, "2015-06-12");
        values.put(PopularMoviesContract.MovieEntry.COLUMN_SYNOPSIS, "Twenty-two years after the events " +
                "of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, " +
                "Jurassic World, as originally envisioned by John Hammond.");
        values.put(PopularMoviesContract.MovieEntry.COLUMN_TITLE, "Jurassic World");
        values.put(PopularMoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE, 7.7);
        return values;
    }

    public static ContentValues createVideoDummyValues(long movieId) {
        ContentValues values = new ContentValues();
        values.put(PopularMoviesContract.VideoEntry.ID, movieId);
        values.put(PopularMoviesContract.VideoEntry.COLUMN_MOVIE_ID, Long.toString(movieId));
        values.put(PopularMoviesContract.VideoEntry.COLUMN_KEY, "ttre");
        values.put(PopularMoviesContract.VideoEntry.COLUMN_NAME, "trerte");
        return values;
    }

    /**
     * Class to test the changes occurred in a block of data.
     */
    public static class TestContentObserver extends ContentObserver {
        /**
         * Helper class to create the Thread with a looper.
         */
        private final HandlerThread handlerThread;

        /**
         * Indicates that the content has change.
         */
        private boolean contentChanged;

        /**
         * Creates a content observer.
         *
         * @param handlerThread The handler to run {@link #onChange} on, or null if none.
         */
        public TestContentObserver(HandlerThread handlerThread) {
            super(new Handler(handlerThread.getLooper()));
            this.handlerThread = handlerThread;
        }

        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            contentChanged = true;
        }

        public void waitForNotificationOrFail() {
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return contentChanged;
                }
            }.run();
            handlerThread.quit();
        }
    }

    /**
     * Allows to get a content observer.
     * @return Content observer.
     */
    public static TestContentObserver getTestContentObserver() {
        HandlerThread handlerThread = new HandlerThread("ContentObserverThread");
        handlerThread.start();
        return new TestContentObserver(handlerThread);
    }
}
