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
import lania.edu.mx.popularmovies.models.DialogData;
import lania.edu.mx.popularmovies.models.Movie;
import lania.edu.mx.popularmovies.models.SortOption;
import lania.edu.mx.popularmovies.utils.UserInterfaceHelper;

/**
 * Fragment to load the movies.
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

        new FetchMoviesTask(getActivity(), this).execute(getSortOrderFromPreferences());
    }

    @Override
    public void onStart() {
        super.onStart();
        new FetchMoviesTask(getActivity(), this).execute(getSortOrderFromPreferences());
    }

    @Override
    public void update(List<Movie> movies) {
        MovieListAdapter adapter = new MovieListAdapter(getActivity(), movies);
        if (getActivity() != null) {
            UserInterfaceHelper.deleteProgressDialog(getActivity(), "LoadingData");
            getMoviesListView().setAdapter(adapter);
        }
    }

    @Override
    public void onPreExecute() {
        UserInterfaceHelper.displayProgressDialog(getActivity(), buildDialogData(), "LoadingData");
    }

    /**
     * Get the sorter order to query the movies from the preferences manager. This value can be set in
     * the settings option from the menu.
     * @return Sorter order to query the movies.
     */
    private SortOption getSortOrderFromPreferences() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        String defaultValue = getString(R.string.preferences_order_default_value);
        String key = getString(R.string.preferences_order_key);

        int orderId = Integer.parseInt(preferences.getString(key, defaultValue));

        return SortOption.valueOf(orderId);
    }

    /**
     * Creates the Data to show in the indeterminate progress dialog.
     * @return Data to show in the indeterminate progress dialog.
     */
    private DialogData buildDialogData() {
        return new DialogData(R.string.app_name, R.string.message_progress_bar, false, android.R.drawable.ic_dialog_alert);
    }

    /**
     * Returns the ListView control to display the movies.
     * @return ListView control to display the movies.
     */
    private ListView getMoviesListView() {
        return (ListView) getActivity().findViewById(R.id.moviesListView);
    }
}
