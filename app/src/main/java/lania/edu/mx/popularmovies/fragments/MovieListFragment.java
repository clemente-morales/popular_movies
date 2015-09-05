package lania.edu.mx.popularmovies.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lania.edu.mx.popularmovies.PopularMoviesApplication;
import lania.edu.mx.popularmovies.R;
import lania.edu.mx.popularmovies.adapters.MovieListGridAdapter;
import lania.edu.mx.popularmovies.asynctasks.FetchMoviesTask;
import lania.edu.mx.popularmovies.events.otto.SortOrderChangedEvent;
import lania.edu.mx.popularmovies.models.DataResult;
import lania.edu.mx.popularmovies.models.DialogData;
import lania.edu.mx.popularmovies.models.Movie;
import lania.edu.mx.popularmovies.models.SortOption;
import lania.edu.mx.popularmovies.utils.UserInterfaceHelper;

/**
 * Fragment to load the movies.
 * Created by clemente on 7/22/15.
 */
public class MovieListFragment extends Fragment implements FetchMoviesTask.MovieListener {
    private static final String TAG = MovieListFragment.class.getSimpleName();

    /**
     * Key to restore the movies.
     */
    public static final String LIST_OF_MOVIES_KEY = "ListOfMovies";

    /**
     * Key to restore de selected sort option.
     */
    public static final String SELECTED_SORT_OPTION_KEY = "SelectedSortOption";

    /**
     * Tag for the progress dialog.
     */
    public static final String PROGRESS_DIALOG_TAG = "LoadingData";

    /**
     * List of movies, currently displayed.
     */
    private ArrayList<Movie> movies;

    /**
     * Selected sort option.
     */
    private SortOption sortOption;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie_list_grid, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridView gridview = (GridView) getView().findViewById(R.id.gridview);
        gridview.setEmptyView(getView().findViewById(R.id.noDataTextView));


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieListGridAdapter adapter = (MovieListGridAdapter) parent.getAdapter();
                Movie movie = (Movie) adapter.getItem(position);
                PopularMoviesApplication.getEventBus().post(movie);
            }
        });

        restoreMovieState(savedInstanceState);
    }

    @Override
    public void onResume() {
        PopularMoviesApplication.getEventBus().register(this);
        super.onResume();

        // if the user is returning from the settings,  we check if the selection order was change
        // to launch the query again.
        if (sortOption != getSortOrderFromPreferences()) {
            PopularMoviesApplication.getEventBus().post(new SortOrderChangedEvent());
            sortOption = getSortOrderFromPreferences();
            new FetchMoviesTask(getActivity(), this).execute(sortOption);
        }
    }

    @Override
    public void onPause() {
        PopularMoviesApplication.getEventBus().unregister(this);
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_OF_MOVIES_KEY, movies);
        outState.putInt(SELECTED_SORT_OPTION_KEY, sortOption.getId());
    }

    @Override
    public void update(DataResult<ArrayList<Movie>, Exception> moviesResult) {
        UserInterfaceHelper.deleteProgressDialog(getActivity(), PROGRESS_DIALOG_TAG);
        if (!moviesResult.isException()) {
            this.movies = moviesResult.getData();
        } else {
            this.movies = new ArrayList<>();
            if (!UserInterfaceHelper.isNetworkAvailable(getActivity())) {
                TextView noDataTextView = (TextView) getView().findViewById(R.id.noDataTextView);
                noDataTextView.setText(R.string.movieList_noInternetConnection);
            } else
                Toast.makeText(getActivity(), R.string.error_connection_message, Toast.LENGTH_SHORT).show();
        }
        displayMovies();
    }

    private void displayMovies() {
        if (getActivity() != null) {
            MovieListGridAdapter adapter = new MovieListGridAdapter(getActivity(), movies);
            GridView gridview = (GridView) getView().findViewById(R.id.gridview);
            gridview.setAdapter(adapter);
        }
    }

    @Override
    public void onPreExecute() {
        UserInterfaceHelper.displayProgressDialog(getActivity(), buildDialogData(), PROGRESS_DIALOG_TAG);
    }

    /**
     * Allows to restore the previous state of the fragment.
     *
     * @param savedInstanceState Previous state of the fragment..
     */
    private void restoreMovieState(Bundle savedInstanceState) {
        if (savedInstanceState == null || !savedInstanceState.containsKey(LIST_OF_MOVIES_KEY)) {
            sortOption = getSortOrderFromPreferences();
            new FetchMoviesTask(getActivity(), this).execute(sortOption);
        } else {
            int selectedSortOption = savedInstanceState.getInt(SELECTED_SORT_OPTION_KEY);
            sortOption = SortOption.valueOf(selectedSortOption);
            movies = savedInstanceState.getParcelableArrayList(LIST_OF_MOVIES_KEY);
            if (movies!=null) {
                displayMovies();
            }
        }
    }

    /**
     * Get the sorter order to query the movies from the preferences manager. This value can be set in
     * the settings option from the menu.
     *
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
     *
     * @return Data to show in the indeterminate progress dialog.
     */
    private DialogData buildDialogData() {
        return new DialogData(R.string.app_name, R.string.message_progress_bar, false, android.R.drawable.ic_dialog_alert);
    }

    /**
     * Returns the ListView control to display the movies.
     *
     * @return ListView control to display the movies.
     */
    private ListView getMoviesListView() {
        return (ListView) getActivity().findViewById(R.id.moviesListView);
    }
}