package lania.edu.mx.popularmovies.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
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
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.apache.commons.lang3.StringUtils;

import lania.edu.mx.popularmovies.PopularMoviesApplication;
import lania.edu.mx.popularmovies.R;
import lania.edu.mx.popularmovies.adapters.MovieListCursorAdapter;
import lania.edu.mx.popularmovies.asynctasks.FetchMoviesTask;
import lania.edu.mx.popularmovies.data.PopularMoviesContract;
import lania.edu.mx.popularmovies.events.otto.FinishingFetchingMoviesEvent;
import lania.edu.mx.popularmovies.models.DialogData;
import lania.edu.mx.popularmovies.models.SortOption;
import lania.edu.mx.popularmovies.utils.UserInterfaceHelper;

import static lania.edu.mx.popularmovies.data.PopularMoviesContract.MovieEntry;
import static lania.edu.mx.popularmovies.models.SortOption.valueOf;

/**
 * Fragment to load the movies.
 * Created by Clemente on 7/22/15.
 */
public class MovieListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
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
            MovieEntry.COLUMN_POSTER_IMAGE
    };

    public static int COLUMN_ID_INDEX = 0;
    public static int COLUMN_POSTER_IMAGE_INDEX = 1;

    private MovieListCursorAdapter movieListCursorAdapter;

    /**
     * Selected sort option.
     */
    private SortOption sortOption;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list_grid, container, false);
        movieListCursorAdapter = new MovieListCursorAdapter(getActivity(),null, 0);
        GridView gridview = (GridView) getView().findViewById(R.id.gridview);
        gridview.setAdapter(movieListCursorAdapter);
        new FetchMoviesTask(getActivity()).execute(getSortOrderFromPreferences());

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);

                int rowId = cursor.getInt(COLUMN_ID_INDEX);

                PopularMoviesApplication.getPopularMoviesApplication(getActivity().getApplicationContext())
                        .getEventBus().post(PopularMoviesContract.MovieEntry.buildMovieUri(rowId));
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MOVIES_LOADER, null, this);
        restoreMovieState(savedInstanceState);
    }

    /**
     * Allows to restore the previous state of the fragment.
     *
     * @param savedInstanceState Previous state of the fragment..
     */
    private void restoreMovieState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            sortOption = getSortOrderFromPreferences();
        } else {
            int selectedSortOption = savedInstanceState.getInt(SELECTED_SORT_OPTION_KEY);
            sortOption = valueOf(selectedSortOption);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // if the user is returning from the settings,  we check if the selection order was change to launch the query again.
        if (sortOption != getSortOrderFromPreferences()) {
            sortOption = getSortOrderFromPreferences();
            new FetchMoviesTask(getActivity()).execute(sortOption);
            getLoaderManager().restartLoader(MOVIES_LOADER, null, this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_SORT_OPTION_KEY, sortOption.getId());
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String sortOrder = StringUtils.EMPTY;
        UserInterfaceHelper.displayProgressDialog(getActivity(), buildDialogData(), PROGRESS_DIALOG_TAG);

        switch (sortOption) {
            case POPULARITY:
                sortOrder = PopularMoviesContract.MovieEntry.COLUMN_POPULARITY + " desc";
                break;
            case RAITING:
                sortOrder = PopularMoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE + " desc";
                break;
            default:
                throw new UnsupportedOperationException("Sor ordern not define");
        }

        Uri orderMovieListUri = PopularMoviesContract.MovieEntry.CONTENT_URI;

        return new CursorLoader(getActivity(), orderMovieListUri, MOVIE_COLUMNS, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movieListCursorAdapter.swapCursor(data);
        UserInterfaceHelper.deleteProgressDialog(getActivity(), PROGRESS_DIALOG_TAG);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        movieListCursorAdapter.swapCursor(null);
    }

    @Subscribe
    public void onFinishingFetchingMovies(FinishingFetchingMoviesEvent event) {
        if (event.getResult().isException()) {
            Toast.makeText(getActivity(), R.string.error_connection_message, Toast.LENGTH_SHORT).show();
        }
    }
}
