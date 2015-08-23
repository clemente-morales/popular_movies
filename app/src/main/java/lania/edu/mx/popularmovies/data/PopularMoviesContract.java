package lania.edu.mx.popularmovies.data;

import android.content.ContentResolver;
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
    public static final String MOVIE_PATH = "movies";

    /**
     * Path to look for the videos of a movie.
     */
    public static final String VIDEO_PATH = "videos";

    /**
     * Path to look for the reviews of a movie.
     */
    public static final String REVIEW_PATH = "reviews";

    /**
     * Defines the contents of the Movie table.
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
         *
         * @param id Id of the movie.
         * @return Uri with the id appended to the end of the path.
         */
        public static Uri buildMovieUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }

    /**
     * Defines the contents of the Video table.
     */
    public static final class VideoEntry implements BaseColumns {
        /**
         * Table name to save the data for the video.
         */
        public static final String TABLE_NAME = "video";

        /**
         * Column name to store the id of the movie.
         */
        public static final String COLUMN_MOVIE_ID = "movie_id";

        /**
         * Column name to store the name of the video.
         */
        public static final String COLUMN_NAME = "name";

        /**
         * Column name to store the key for the video.
         */
        public static final String COLUMN_KEY = "key";

        /**
         * Uri to look for the videos of a movie.
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(VIDEO_PATH).build();

        /**
         * MIME type to represent a directory with the videos of a movie.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_PATH;

        /**
         * MIME type to represent a video of a movie.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_PATH;

        public static Uri buildMoviePathWithVideos(long movieId) {
            return BASE_CONTENT_URI.buildUpon().appendPath(MOVIE_PATH)
                    .appendPath(Long.toString(movieId)).appendPath(VIDEO_PATH).build();
        }

        /**
         * Creates a new uri with the id appended to the end of the path.
         * @param id Id of the video.
         * @return Uri with the id appended to the end of the path.
         */
        public static Uri buildVideoUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }

    /**
     * Defines the contents of the Review table.
     */
    public static final class ReviewEntry implements BaseColumns {
        /**
         * Table name to save the data for the Review.
         */
        public static final String TABLE_NAME = "review";

        /**
         * Column name to store the id of the movie.
         */
        public static final String COLUMN_MOVIE_ID = "movie_id";

        /**
         * Column name to store the author of the review.
         */
        public static final String COLUMN_AUTHOR = "author";

        /**
         * Column name to store the content of the review.
         */
        public static final String COLUMN_CONTENT = "content";

        /**
         * Uri to look for the reviews of a movie.
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(REVIEW_PATH).build();

        /**
         * MIME type to represent a directory with the videos of a movie.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_PATH;

        /**
         * MIME type to represent a video of a movie.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_PATH;

        public static Uri buildMoviePathWithReviews(long movieId) {
            return BASE_CONTENT_URI.buildUpon().appendPath(MOVIE_PATH)
                    .appendPath(Long.toString(movieId)).appendPath(REVIEW_PATH).build();
        }

        /**
         * Creates a new uri with the id appended to the end of the path.
         * @param id Id of the video.
         * @return Uri with the id appended to the end of the path.
         */
        public static Uri buildReviewUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }
}
