package lania.edu.mx.popularmovies.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import lania.edu.mx.popularmovies.PopularMoviesApplication;
import lania.edu.mx.popularmovies.R;
import lania.edu.mx.popularmovies.activities.MovieDetailActivity;
import lania.edu.mx.popularmovies.adapters.MovieListGridAdapter;
import lania.edu.mx.popularmovies.asynctasks.FetchMoviesTask;
import lania.edu.mx.popularmovies.data.PopularMoviesContract;
import lania.edu.mx.popularmovies.data.PopularMoviesProvider;
import lania.edu.mx.popularmovies.models.DataResult;
import lania.edu.mx.popularmovies.models.DialogData;
import lania.edu.mx.popularmovies.models.Movie;
import lania.edu.mx.popularmovies.models.SortOption;
import lania.edu.mx.popularmovies.utils.UserInterfaceHelper;

import static lania.edu.mx.popularmovies.models.SortOption.*;
import static lania.edu.mx.popularmovies.data.PopularMoviesContract.MovieEntry;

/**
 * Fragment to load the movies.
 * Created by Clemente on 7/22/15.
 */
public class MovieListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    /**
     * Data to pass to de detail of the movie.
     */
    public static final String MOVIE_DATA_EXTRA = "MovieData";

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

    private static final int MOVIES_LOADER = 23;

    private static final String[] MOVIE_COLUMNS = {
            MovieEntry.ID,
            MovieEntry.COLUMN_BACKDROP_IMAGE,
            MovieEntry.COLUMN_POPULARITY,
            MovieEntry.COLUMN_POSTER_IMAGE,
            MovieEntry.COLUMN_RELEASE_DATE,
            MovieEntry.COLUMN_SYNOPSIS,
            MovieEntry.COLUMN_TITLE,
            MovieEntry.COLUMN_VOTE_AVERAGE
    };

    public static int COLUMN_ID_INDEX = 0;
    public static int COLUMN_BACKDROG_IMAGE_INDEX = 1;
    public static int COLUMN_POPULARITY_INDEX = 2;
    public static int COLUMN_POSTER_IMAGE_INDEX = 3;
    public static int COLUMN_RELEASE_DATE_INDEX = 4;
    public static int COLUMN_SYNOPSIS_INDEX = 5;
    public static int COLUMN_TITLE_INDEX = 6;
    public static int COLUMN_VOTE_AVERAGE_INDEX = 7;

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
        View view = inflater.inflate(R.layout.fragment_movie_list_grid, container, false);
        displayMovies();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridView gridview = (GridView) getView().findViewById(R.id.gridview);
        getLoaderManager().initLoader(MOVIES_LOADER, null, this);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieListGridAdapter adapter = (MovieListGridAdapter) parent.getAdapter();
                Movie movie = (Movie) adapter.getItem(position);

                ((PopularMoviesApplication) getActivity().getApplicationContext()).getEventBus().post(movie);
            }
        });

        restoreMovieState(savedInstanceState);
    }

    /**
     * Allows to restore the previous state of the fragment.
     *
     * @param savedInstanceState Previous state of the fragment..
     */
    private void restoreMovieState(Bundle savedInstanceState) {
        if (savedInstanceState == null || !savedInstanceState.containsKey(LIST_OF_MOVIES_KEY)) {
            sortOption = getSortOrderFromPreferences();
            // new FetchMoviesTask(getActivity(), this).execute(sortOption);
        } else {
            int selectedSortOption = savedInstanceState.getInt(SELECTED_SORT_OPTION_KEY);
            sortOption = valueOf(selectedSortOption);
            movies = savedInstanceState.getParcelableArrayList(LIST_OF_MOVIES_KEY);
            if (movies!=null) {
                displayMovies();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // if the user is returning from the settings,  we check if the selection order was change to launch the query again.
        if (sortOption != getSortOrderFromPreferences()) {
            sortOption = getSortOrderFromPreferences();
            // new FetchMoviesTask(getActivity(), this).execute(sortOption);
            getLoaderManager().restartLoader(MOVIES_LOADER, null, this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_OF_MOVIES_KEY, movies);
        outState.putInt(SELECTED_SORT_OPTION_KEY, sortOption.getId());
    }


    public void update(DataResult<ArrayList<Movie>, Exception> moviesResult) {
        UserInterfaceHelper.deleteProgressDialog(getActivity(), PROGRESS_DIALOG_TAG);
        if (!moviesResult.isException()) {
            this.movies = moviesResult.getData();
            displayMovies();
        } else {
            Toast.makeText(getActivity(),R.string.error_connection_message, Toast.LENGTH_SHORT).show();
        }
    }

    private void displayMovies() {

        if (getActivity() != null) {
            MovieListGridAdapter adapter = new MovieListGridAdapter(getActivity(), movies);
            GridView gridview = (GridView) getView().findViewById(R.id.gridview);
            gridview.setAdapter(adapter);
        }
    }

    public void onPreExecute() {
        UserInterfaceHelper.displayProgressDialog(getActivity(), buildDialogData(), PROGRESS_DIALOG_TAG);
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

        return valueOf(orderId);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String sortOrder = StringUtils.EMPTY;

        switch (sortOption) {
            case POPULARITY:
                sortOrder = PopularMoviesContract.MovieEntry.COLUMN_POPULARITY+" desc";
                break;
            case RAITING:
                sortOrder = PopularMoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE+" desc";
                break;
            default:
                throw new UnsupportedOperationException("Sor ordern not define");
        }

        Uri orderMovieListUri = PopularMoviesContract.MovieEntry.CONTENT_URI;

        return new CursorLoader(getActivity(), orderMovieListUri, MOVIE_COLUMNS, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
