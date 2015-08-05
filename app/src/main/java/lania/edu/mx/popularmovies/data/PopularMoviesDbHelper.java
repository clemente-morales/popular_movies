package lania.edu.mx.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Manages a local data base for popular movies data.
 * Created by clemente on 7/30/15.
 */
public class PopularMoviesDbHelper extends SQLiteOpenHelper {
    /**
     * Scheme version of the data base.
     */
    private static final int DATABASE_VERSION = 5;

    /**
     * Name of the database.
     */
    public static final String DATABASE_NAME = "popular_movies.db";

    /**
     * Allows to create an instance of this helper to manage the creation of the database for popular
     * movie data.
     *
     * @param context Context of the application.
     */
    public PopularMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + PopularMoviesContract.MovieEntry.TABLE_NAME + " (" +
                PopularMoviesContract.MovieEntry.ID + " INTEGER PRIMARY KEY," +
                PopularMoviesContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                PopularMoviesContract.MovieEntry.COLUMN_SYNOPSIS + " TEXT NULL," +
                PopularMoviesContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL," +
                PopularMoviesContract.MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL," +
                PopularMoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL," +
                PopularMoviesContract.MovieEntry.COLUMN_POSTER_IMAGE + " TEXT NULL," +
                PopularMoviesContract.MovieEntry.COLUMN_BACKDROP_IMAGE + " TEXT NULL);";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // We don't need to keep data on schema update
        db.execSQL("DROP TABLE IF EXISTS " + PopularMoviesContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
