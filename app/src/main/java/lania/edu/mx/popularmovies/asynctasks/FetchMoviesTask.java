package lania.edu.mx.popularmovies.asynctasks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import lania.edu.mx.popularmovies.DependencyModuleApplication;
import lania.edu.mx.popularmovies.models.Movie;
import lania.edu.mx.popularmovies.models.SortOption;
import lania.edu.mx.popularmovies.tos.MovieConverter;
import lania.edu.mx.popularmovies.tos.MovieResponse;
import lania.edu.mx.popularmovies.utils.JsonSerializacionHelper;

/**
 * Created by clemente on 7/22/15.
 */
public class FetchMoviesTask extends AsyncTask<SortOption, Void, ArrayList<Movie>> {
    private static final String TAG = FetchMoviesTask.class.getSimpleName();
    public static final String BASE_URI_TO_DISCOVER_MOVIES = "http://api.themoviedb.org/3/discover/movie";
    public static final String SORT_BY_PARAMETER = "sort_by";
    public static final String API_KEY_PARAMETER = "api_key";
    public static final String MOVIEDB_API_KEY_PROPERTY = "themoviedb_api_key";
    private final Context context;

    private MovieListener movieListener;

    public interface MovieListener {
        void onPreExecute();
        void update(ArrayList<Movie> data);
    }

    public FetchMoviesTask(Context context, MovieListener movieListener) {
        this.context = context;
        this.movieListener = movieListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        movieListener.onPreExecute();
    }

    @Override
    protected ArrayList<Movie> doInBackground(SortOption... params) {
        SortOption sortOption = params[0];
        return getRealData(sortOption);
    }

    private ArrayList<Movie> getRealData(SortOption sortOption) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String jsonMovies = "";
        ArrayList<Movie> result = new ArrayList<>();
        try {
            Uri uri = Uri.parse(BASE_URI_TO_DISCOVER_MOVIES).buildUpon()
                    .appendQueryParameter(SORT_BY_PARAMETER, sortOption.getOrder())
                    .appendQueryParameter(API_KEY_PARAMETER, getKey()).build();

            Log.d(TAG, uri.toString());
            URL url = new URL(uri.toString());

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
                return null;

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();

            while (line != null) {
                buffer.append(line);
                buffer.append("\n");
                line = reader.readLine();
            }

            if (buffer.length() == 0)
                return null;

            jsonMovies = buffer.toString();
            MovieResponse response = JsonSerializacionHelper.deserializeObject(MovieResponse.class, jsonMovies);
            result = MovieConverter.toModel(response);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }

        return result;
    }

    @NonNull
    private String getKey() {
        return DependencyModuleApplication.getProperties(context).getProperty(MOVIEDB_API_KEY_PROPERTY);
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        movieListener.update(movies);
    }
}
