package lania.edu.mx.popularmovies.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

/**
 * Created by clemente on 7/31/15.
 */
public class DataBaseTest extends AndroidTestCase {
    public void setUp() {
        deleteDatabase();
    }

    private void deleteDatabase() {
        mContext.deleteDatabase(PopularMoviesDbHelper.DATABASE_NAME);
    }

    public void testCreateDataBase() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(PopularMoviesContract.MovieEntry.TABLE_NAME);
        tableNameHashSet.add(PopularMoviesContract.VideoEntry.TABLE_NAME);
        tableNameHashSet.add(PopularMoviesContract.ReviewEntry.TABLE_NAME);

        SQLiteDatabase sqliteDatabase = new PopularMoviesDbHelper(mContext).getWritableDatabase();
        assertTrue("An exception has occurred opening the database " + PopularMoviesDbHelper.DATABASE_NAME,
                sqliteDatabase.isOpen());

        Cursor cursor = sqliteDatabase.rawQuery("select name from sqlite_master where type='table'", null);

        assertTrue("The database has not been created correctly", cursor.moveToFirst());

        /*final HashSet<String> movieColumnHashSet = new HashSet<String>();
        tableNameHashSet.add(PopularMoviesContract.MovieEntry.COLUMN_BACKDROP_IMAGE);
        tableNameHashSet.add(PopularMoviesContract.MovieEntry.COLUMN_POPULARITY);
        tableNameHashSet.add(PopularMoviesContract.MovieEntry.COLUMN_POSTER_IMAGE);
        tableNameHashSet.add(PopularMoviesContract.MovieEntry.COLUMN_RELEASE_DATE);
        tableNameHashSet.add(PopularMoviesContract.MovieEntry.COLUMN_SYNOPSIS);
        tableNameHashSet.add(PopularMoviesContract.MovieEntry.COLUMN_TITLE);
        tableNameHashSet.add(PopularMoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE);
        tableNameHashSet.add(PopularMoviesContract.MovieEntry.ID);

        int columnNameIndex = cursor.getColumnIndex("name");
        do {
            String columnName = cursor.getString(columnNameIndex);
            movieColumnHashSet.remove(columnName);
        } while(cursor.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                movieColumnHashSet.isEmpty());
        */
        cursor.close();
        sqliteDatabase.close();
    }

    public void testMovieTableInsertion() {
        PopularMoviesDbHelper dbHelper = new PopularMoviesDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = TestUtilities.createMovieDummyValues();
        long id = database.insert(PopularMoviesContract.MovieEntry.TABLE_NAME, null, contentValues);

        Cursor movieCursor = database.query(PopularMoviesContract.MovieEntry.TABLE_NAME, null, null,
                null, null, null, null);
        assertTrue("No records return from movie query", movieCursor.moveToFirst());
        TestUtilities.validateCurrentRecord("The query doesn't return the espected values and columns " +
                "for the movie ", movieCursor, contentValues);

        assertFalse(movieCursor.moveToNext());

        movieCursor.close();
        database.close();
    }

    public void testVideoTableInsertion() {
        PopularMoviesDbHelper dbHelper = new PopularMoviesDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = TestUtilities.createMovieDummyValues();
        long movieId = database.insert(PopularMoviesContract.MovieEntry.TABLE_NAME, null, contentValues);

        contentValues = TestUtilities.createVideoDummyValues(movieId);
        long videoId = database.insert(PopularMoviesContract.VideoEntry.TABLE_NAME, null, contentValues);

        Cursor videoCursor = database.query(PopularMoviesContract.VideoEntry.TABLE_NAME, null, null,
                null, null, null, null);
        assertTrue("No records return from the video query", videoCursor.moveToFirst());
        TestUtilities.validateCurrentRecord("The query doesn't return the espected values and columns " +
                "for the video ", videoCursor, contentValues);

        assertFalse(videoCursor.moveToNext());

        videoCursor.close();
        database.close();
    }
}
