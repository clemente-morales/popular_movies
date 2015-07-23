package lania.edu.mx.popularmovies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import lania.edu.mx.popularmovies.R;
import lania.edu.mx.popularmovies.adapters.MovieListAdapter;
import lania.edu.mx.popularmovies.asynctasks.FetchMoviesTask;
import lania.edu.mx.popularmovies.models.Movie;
import lania.edu.mx.popularmovies.models.SortOption;

/**
 * Created by clemente on 7/22/15.
 */
public class MovieListFragment extends Fragment implements FetchMoviesTask.MovieListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new FetchMoviesTask(this).execute(SortOption.POPULARITY);
    }

    @Override
    public void update(List<Movie> movies) {
        ListView moviesListView = (ListView) getActivity().findViewById(R.id.moviesListView);
        MovieListAdapter adapter = new MovieListAdapter(getActivity(), movies);
        moviesListView.setAdapter(adapter);
    }
}
