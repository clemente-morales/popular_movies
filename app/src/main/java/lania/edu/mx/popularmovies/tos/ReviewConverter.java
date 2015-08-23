package lania.edu.mx.popularmovies.tos;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lania.edu.mx.popularmovies.data.PopularMoviesContract;

/**
 * Created by clerks on 8/22/15.
 */
public final class ReviewConverter {
    private static final int COLUMN_ID_INDEX = 0;
    private static final int COLUMN_MOVIE_ID_INDEX = 1;
    private static final int COLUMN_AUTHOR_INDEX = 2;
    private static final int COLUMN_CONTENT_INDEX = 3;

    private ReviewConverter() {}

    public static lania.edu.mx.popularmovies.models.Review toModel(Cursor cursor) {
        return new lania.edu.mx.popularmovies.models.Review(cursor.getString(COLUMN_ID_INDEX),
                cursor.getString(COLUMN_MOVIE_ID_INDEX), cursor.getString(COLUMN_AUTHOR_INDEX), cursor.getString(COLUMN_CONTENT_INDEX), null);
    }

    public static lania.edu.mx.popularmovies.models.Review toModel(String movieId, lania.edu.mx.popularmovies.tos.Review review) {
        return new lania.edu.mx.popularmovies.models.Review(review.getId(), movieId, review.getAuthor(), review.getContent(), review.getUrl());
    }

    public static ArrayList<lania.edu.mx.popularmovies.models.Review> toModel(String movieId, ReviewsResponse response) {
        ArrayList<lania.edu.mx.popularmovies.models.Review> reviews = new ArrayList<lania.edu.mx.popularmovies.models.Review>();

        for (lania.edu.mx.popularmovies.tos.Review review : response.getReviews()) {
            reviews.add(toModel(movieId, review));
        }

        return reviews;
    }

    public static ContentValues toContentValues(lania.edu.mx.popularmovies.models.Review review) {
        Log.d("Test", ""+review);
        ContentValues values = new ContentValues();
        values.put(PopularMoviesContract.ReviewEntry.ID, review.getId());
        values.put(PopularMoviesContract.ReviewEntry.COLUMN_MOVIE_ID, review.getMovieId());
        values.put(PopularMoviesContract.ReviewEntry.COLUMN_AUTHOR, review.getAuthor());
        values.put(PopularMoviesContract.ReviewEntry.COLUMN_CONTENT, review.getContent());
        return values;
    }

    public static ContentValues[] toContentValues(List<lania.edu.mx.popularmovies.models.Review> reviews) {
        ContentValues[] values = new ContentValues[reviews.size()];

        int index=0;
        for(lania.edu.mx.popularmovies.models.Review review : reviews) {
            values[index] = toContentValues(review);
            index++;
        }

        return values;
    }
}
