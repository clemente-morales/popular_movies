package lania.edu.mx.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Popular movies data provider.
 * Created by clemente on 7/29/15.
 */
public class PopularMoviesProvider extends ContentProvider {
    /**
     * Id to request the full list of movies.
     */
    public static final int MOVIE = 100;

    /**
     * Id to request the Movies sorted by a criteria.
     */
    public static final int SORTED_MOVIE = 101;

    /**
     * Manages the data base.
     */
    private PopularMoviesDbHelper dbHelper;

    /**
     * Uri matcher used for this provider.
     */
    private static final UriMatcher uriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        dbHelper = new PopularMoviesDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor result;

        final int match = buildUriMatcher().match(uri);
        switch (match) {
            case MOVIE:
                result = dbHelper.getReadableDatabase().query(PopularMoviesContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return result;
    }

    @Override
    public String getType(Uri uri) {
        final int match = buildUriMatcher().match(uri);
        switch (match) {
            case MOVIE:
            case SORTED_MOVIE:
                return PopularMoviesContract.MovieEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);

        Uri result;

        switch (match) {
            case MOVIE: {
                long id = database.insert(PopularMoviesContract.MovieEntry.TABLE_NAME, null, values);
                if (id > 0)
                    result = PopularMoviesContract.MovieEntry.buildMovieUri(id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);

                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        database.close();
        getContext().getContentResolver().notifyChange(result, null);

        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);

        int rowsDeleted = 0;

        if (null == selection) selection = "1";

        switch (match) {
            case MOVIE: {
                rowsDeleted = database.delete(PopularMoviesContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        database.close();

        if (rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);

        int rowsUpdated = 0;

        switch (match) {
            case MOVIE: {
                rowsUpdated = database.update(PopularMoviesContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        database.close();

        if (rowsUpdated != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        switch (match) {
            case MOVIE: {
                database.beginTransaction();
                int rowsInserted = 0;

                try {
                    for (ContentValues value : values) {
                        long id = database.insert(PopularMoviesContract.MovieEntry.TABLE_NAME, null, value);
                        if (id != -1)
                            rowsInserted++;
                    }
                    database.setTransactionSuccessful();
                } finally {
                    database.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return rowsInserted;
            }
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public void shutdown() {
        dbHelper.close();
        super.shutdown();
    }

    /***
     * This UriMatcher will match each URI to the MOVIE and SORTED_MOVIE integer constants defined
     * above. UriMatcher to select the query to execute.
     *
     * @return UriMatcher to select the query to execute.
     */
    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(PopularMoviesContract.CONTENT_AUTHORITY, PopularMoviesContract.MOVIE_PATH, MOVIE);
        return matcher;
    }
}
