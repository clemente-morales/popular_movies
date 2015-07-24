package lania.edu.mx.popularmovies.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import lania.edu.mx.popularmovies.R;
import lania.edu.mx.popularmovies.activities.MovieDetailActivity;
import lania.edu.mx.popularmovies.adapters.MovieListAdapter;
import lania.edu.mx.popularmovies.asynctasks.FetchMoviesTask;
import lania.edu.mx.popularmovies.models.Movie;
import lania.edu.mx.popularmovies.models.SortOption;

/**
 * Created by clemente on 7/22/15.
 */
public class MovieListFragment extends Fragment implements FetchMoviesTask.MovieListener {
    public static final String MOVIE_DATA = "MovieData";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getMoviesListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieListAdapter adapter = (MovieListAdapter) parent.getAdapter();
                Movie movie = (Movie) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(MOVIE_DATA, movie);
                getActivity().startActivity(intent);
            }
        });

        new FetchMoviesTask(getActivity(), this).execute(getSortOrder());
    }

    @Override
    public void onStart() {
        super.onStart();
        new FetchMoviesTask(getActivity(), this).execute(getSortOrder());
    }

    private SortOption getSortOrder() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        String defaultValue = getString(R.string.preferences_order_default_value);
        String key = getString(R.string.preferences_order_key);

        int orderId = Integer.parseInt(preferences.getString(key, defaultValue));

        return SortOption.valueOf(orderId);
    }

    @Override
    public void update(List<Movie> movies) {
        MovieListAdapter adapter = new MovieListAdapter(getActivity(), movies);
        if (getActivity() != null) {
            getMoviesListView().setAdapter(adapter);
        }
    }

    private ListView getMoviesListView() {
        return (ListView) getActivity().findViewById(R.id.moviesListView);
    }
}
