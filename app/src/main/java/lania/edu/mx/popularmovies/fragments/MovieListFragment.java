package lania.edu.mx.popularmovies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lania.edu.mx.popularmovies.R;

/**
 * Created by clemente on 7/22/15.
 */
public class MovieListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }
}
