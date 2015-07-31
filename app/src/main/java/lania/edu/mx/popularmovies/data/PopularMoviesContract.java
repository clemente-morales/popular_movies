package lania.edu.mx.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

/**
 * Defines the tables and column names for the popular movies data base.
 * Created by clemente on 7/30/15.
 */
public class PopularMoviesContract {
    /**
     * Name for the content provider.
     */
    public static final String CONTENT_AUTHORITY = "lania.edu.mx.popularmovies.app";

    /**
     * The base of all URI's which apps will use to contact the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Path to look for movie data.
     */
    public static final String MOVIE_PATH = "movie";

    /**
     * Defines the contents of the movie table.
     */
    public static final class MovieEntry implements BaseColumns {
        /**
         * Table name to save the movie data.
         */
        public static final String TABLE_NAME = "movie";

        /**
         * Column name to store the original title for the movie.
         */
        public static final String COLUMN_TITLE = "title";

        /**
         * Column name to store the original title for the movie.
         */
        public static final String COLUMN_SYNOPSIS = "synopsis";

        /**
         * Column name to store the original title for the movie.
         */
        public static final String COLUMN_RELEASE_DATE = "release_date";

        /**
         * Column name to store the original title for the movie.
         */
        public static final String COLUMN_POPULARITY = "popularity";

        /**
         * Column name to store the vote average for the movie.
         */
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        /**
         * Column name to store the original title for the movie.
         */
        public static final String COLUMN_POSTER_IMAGE = "poster_image";

        /**
         * Column name to store the original title for the movie.
         */
        public static final String COLUMN_BACKDROP_IMAGE = "backdrop_image";

        /**
         * Uri to look for movie data.
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(MOVIE_PATH).build();

        /**
         * MIME type to represent a directory with movie items.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_PATH;

        /**
         * MIME type to represent a movie item.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_PATH;

        /**
         * Creates a new uri with the id appended to the end of the path.
         * @param id Id of the movie.
         * @return Uri with the id appended to the end of the path.
         */
        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
