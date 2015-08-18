package lania.edu.mx.popularmovies.data;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;

/**
 * Test the provider class for the popular movies database.
 * Created by clemente on 7/31/15.
 */
public class PopularMoviesProviderTest extends AndroidTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteRecords();
    }

    /**
     * Allows to clean the database on setUp.
     */
    private void deleteRecords() {
        PopularMoviesDbHelper dbHelper = new PopularMoviesDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(PopularMoviesContract.MovieEntry.TABLE_NAME, null, null);
        database.close();
    }

    /**
     * Deletes all the records from the database with the provider.
     */
    private void deleteRecordsFromProvider() {
        mContext.getContentResolver().delete(PopularMoviesContract.MovieEntry.CONTENT_URI, null, null);
        Cursor cursor = mContext.getContentResolver().query(
                PopularMoviesContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from mov   ie table during delete", 0, cursor.getCount());
        cursor.close();
    }

    /**
     * Checks that the  provider is registered correctly.
     */
    public void testProviderRegistry() {
        PackageManager packageManager = mContext.getPackageManager();
        ComponentName componentName = new ComponentName(mContext.getPackageName(), PopularMoviesProvider.class.getName());

        try {
            ProviderInfo providerInfo = packageManager.getProviderInfo(componentName, 0);
            assertEquals(String.format("The content authority of the registered provider for %s " +
                    "doesn't match", providerInfo.authority), PopularMoviesContract.CONTENT_AUTHORITY, providerInfo.authority);
        } catch (PackageManager.NameNotFoundException exception) {
            assertTrue("PopularMoviesProvider not registered at " + mContext.getPackageName(), false);
        }
    }

    /**
     * It verifies that the ContentProvider returns the correct type for each type of Uri that it
     * can handle.
     */
    public void testGetType() {
        String type = mContext.getContentResolver().getType(PopularMoviesContract.MovieEntry.CONTENT_URI);
        assertEquals("The MovieEntry CONTENT_URI should return MovieEntry.CONTENT_TYPE", PopularMoviesContract.MovieEntry.CONTENT_TYPE, type);
    }

    /**
     * Test the select all queries.
     */
    public void testSelectAllMoviesQuery() {
        PopularMoviesDbHelper dbHelper = new PopularMoviesDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = TestUtilities.createMovieDummyValues();
        long id = database.insert(PopularMoviesContract.MovieEntry.TABLE_NAME, null, contentValues);
        database.close();

        Cursor movieCursor = mContext.getContentResolver().query(PopularMoviesContract.MovieEntry.CONTENT_URI,
                null, null, null, null, null);

        TestUtilities.validateCursor("testSelectAllMoviesQuery, movie query", movieCursor, contentValues);
    }

    /**
     * Allows to test the insertion with the provider.
     */
    public void testInsertProvider() {
        ContentValues values = TestUtilities.createMovieDummyValues();

        // Register a content observer for our insert. This observer is linked with the Content Uri.
        TestUtilities.TestContentObserver movieContentObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(PopularMoviesContract.MovieEntry.CONTENT_URI, true, movieContentObserver);

        Uri movieUri = mContext.getContentResolver().insert(PopularMoviesContract.MovieEntry.CONTENT_URI, values);

        // Wait for the notification of content change or fail
        movieContentObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(movieContentObserver);

        long rowId = ContentUris.parseId(movieUri);
        assertTrue(rowId != -1);

        // Check if the data was correctly inserted
        Cursor movieCursor = mContext.getContentResolver().query(PopularMoviesContract.MovieEntry.CONTENT_URI,
                null, null, null, null, null);

        TestUtilities.validateCursor("testSelectAllMoviesQuery, movie query", movieCursor, values);
    }

    /**
     * Test the delete of the provider.
     */
    public void testDeleteProvider() {
        testInsertProvider();

        // Register a content observer for our movie delete. This observer is linked with the Content Uri.
        TestUtilities.TestContentObserver movieContentObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(PopularMoviesContract.MovieEntry.CONTENT_URI, true, movieContentObserver);

        deleteRecordsFromProvider();

        movieContentObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(movieContentObserver);
    }

    /**
     * Test the bulk insert for the provider.
     */
    public void testBulkInsert() {
        ContentValues[] values = TestUtilities.createBulkDummyInsert();

        TestUtilities.TestContentObserver movieContentObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(PopularMoviesContract.MovieEntry.CONTENT_URI, true, movieContentObserver);

        int insertCount = mContext.getContentResolver().bulkInsert(PopularMoviesContract.MovieEntry.CONTENT_URI, values);

        movieContentObserver.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(movieContentObserver);

        assertEquals("The expected number of values to insert in the bulk was not correct", values.length, insertCount);

        Cursor movieCursor = mContext.getContentResolver().query(PopularMoviesContract.MovieEntry.CONTENT_URI,
                null, null, null, null, null);
        assertEquals("The expected number of values inserted in the bulkInsert was not correct", values.length, movieCursor.getCount());
        movieCursor.close();
    }

}
