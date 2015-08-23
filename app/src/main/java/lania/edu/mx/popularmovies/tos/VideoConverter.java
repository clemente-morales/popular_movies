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
public final class VideoConverter {
    private static final int COLUMN_ID_INDEX = 0;
    private static final int COLUMN_MOVIE_ID_INDEX = 1;
    private static final int COLUMN_NAME_INDEX = 2;
    private static final int COLUMN_KEY_INDEX = 3;

    private VideoConverter() {
    }

    public static lania.edu.mx.popularmovies.models.Video toModel(Cursor cursor) {
        return new lania.edu.mx.popularmovies.models.Video(cursor.getString(COLUMN_ID_INDEX),
                cursor.getString(COLUMN_MOVIE_ID_INDEX), cursor.getString(COLUMN_NAME_INDEX), cursor.getString(COLUMN_KEY_INDEX));
    }

    public static lania.edu.mx.popularmovies.models.Video toModel(String movieId, lania.edu.mx.popularmovies.tos.Video video) {
        return new lania.edu.mx.popularmovies.models.Video(video.getId(), movieId, video.getKey(), video.getName(), video.getSize(), video.getType(), video.getSite(), video.getIso());
    }

    public static ArrayList<lania.edu.mx.popularmovies.models.Video> toModel(String movieId, VideosResponse response) {
        ArrayList<lania.edu.mx.popularmovies.models.Video> reviews = new ArrayList<lania.edu.mx.popularmovies.models.Video>();

        for (lania.edu.mx.popularmovies.tos.Video Video : response.getVideos()) {
            reviews.add(toModel(movieId, Video));
        }

        return reviews;
    }

    public static ContentValues toContentValues(lania.edu.mx.popularmovies.models.Video video) {
        Log.d("Test", "" + video);
        ContentValues values = new ContentValues();
        values.put(PopularMoviesContract.VideoEntry.ID, video.getId());
        values.put(PopularMoviesContract.VideoEntry.COLUMN_MOVIE_ID, video.getMovieId());
        values.put(PopularMoviesContract.VideoEntry.COLUMN_NAME, video.getName());
        values.put(PopularMoviesContract.VideoEntry.COLUMN_KEY, video.getKey());
        return values;
    }

    public static ContentValues[] toContentValues(List<lania.edu.mx.popularmovies.models.Video> videos) {
        ContentValues[] values = new ContentValues[videos.size()];
        int index = 0;
        for (lania.edu.mx.popularmovies.models.Video video : videos) {
            values[index] = toContentValues(video);
            index++;
        }

        return values;
    }

}
