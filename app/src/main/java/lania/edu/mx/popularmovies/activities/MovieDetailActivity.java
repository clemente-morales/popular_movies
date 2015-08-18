package lania.edu.mx.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import lania.edu.mx.popularmovies.R;

public class MovieDetailActivity extends ActionBarActivity {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

    }
}
