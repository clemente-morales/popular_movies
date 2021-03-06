package lania.edu.mx.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;

import lania.edu.mx.popularmovies.PopularMoviesApplication;
import lania.edu.mx.popularmovies.R;
import lania.edu.mx.popularmovies.events.otto.MovieSelectionChangeEvent;
import lania.edu.mx.popularmovies.events.otto.SortOrderChangedEvent;
import lania.edu.mx.popularmovies.fragments.MovieDetailActivityFragment;
import lania.edu.mx.popularmovies.models.Movie;

public class MainActivity extends ActionBarActivity {

    public static final String DETAIL_FRAGMENT_TAG = "MovielDetailFragmentTag";
    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.movie_detail_container) != null) {
            twoPane = true;

            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction().replace(R.id.movie_detail_container,
                        new MovieDetailActivityFragment(), DETAIL_FRAGMENT_TAG).commit();
            }
        }
    }

    @Override
    protected void onResume() {
        PopularMoviesApplication.getEventBus().register(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        PopularMoviesApplication.getEventBus().unregister(this);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This event occurs when a movie has been selected.
     * @param movie Data of the movie.
     */
    @Subscribe
    public void onItemSelected(Movie movie) {
        if (twoPane) {
            PopularMoviesApplication.getEventBus().post(new MovieSelectionChangeEvent(movie));
        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivityFragment.MOVIE_DETAIL_KEY, movie);
            startActivity(intent);
        }
    }

    /**
     * Reset the fragment to show the detail of the movie.
     * @param event Event launched when the sort order whas changed.
     */
    @Subscribe
    public void onSortOrderChanged(SortOrderChangedEvent event) {
        if (findViewById(R.id.movie_detail_container) != null) {
            getFragmentManager().beginTransaction().replace(R.id.movie_detail_container,
                    new MovieDetailActivityFragment(), DETAIL_FRAGMENT_TAG).commit();
        }
    }
}
