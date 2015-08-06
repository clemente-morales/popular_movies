package lania.edu.mx.popularmovies.fragments;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import lania.edu.mx.popularmovies.R;
import lania.edu.mx.popularmovies.adapters.MovieVideosAdapter;
import lania.edu.mx.popularmovies.asynctasks.FetchMovieDetailTask;
import lania.edu.mx.popularmovies.models.DataResult;
import lania.edu.mx.popularmovies.models.DialogData;
import lania.edu.mx.popularmovies.models.Movie;
import lania.edu.mx.popularmovies.models.Video;
import lania.edu.mx.popularmovies.services.MoviesService;
import lania.edu.mx.popularmovies.utils.UserInterfaceHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment implements FetchMovieDetailTask.MovieDetailListener {

    private static final String TAG = MovieDetailActivityFragment.class.getSimpleName();

    public static final String MOVIE_DETAIL_KEY = "MovieDetailKey";

    private static final String DETAIL_FRAGMENT_TAG = "MovielDetailFragmentTag";

    /**
     * Tag for the progress dialog.
     */
    public static final String PROGRESS_DIALOG_TAG = "LoadingData";

    private Movie movie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restoreState(savedInstanceState);
        extractMovieDetailDataFromIntent();
        displayData();

        ListView videosListView = (ListView) getView().findViewById(R.id.videosListView);

        videosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Video video = (Video) parent.getItemAtPosition(position);
                watchYoutubeVideo(video.getKey());
            }
        });
    }

    public void watchYoutubeVideo(String id){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            getActivity().startActivity(intent);
        }catch (ActivityNotFoundException ex){
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+id));
            startActivity(intent);
        }
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }

        this.movie = savedInstanceState.getParcelable("MovieSerializationId");
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("MovieSerializationId", movie);
    }

    /**
     * Allows to dipslay the detail of a movie.
     */
    private void displayData() {
        ImageView thumbailImage = (ImageView) getActivity().findViewById(R.id.movieThumbailImageView);
        displayImage(movie.getPosterImageName(), thumbailImage);

        TextView title = (TextView) getActivity().findViewById(R.id.movieTitleTextView);
        title.setText(movie.getTitle());

        TextView synopsis = (TextView) getActivity().findViewById(R.id.movieSynopsisTextView);
        synopsis.setText(movie.getSynopsis());

        TextView release = (TextView) getActivity().findViewById(R.id.movieReleaseDateTextView);
        release.setText(formatDate(movie.getReleaseDate()));

        RatingBar raitingBar = (RatingBar) getActivity().findViewById(R.id.movieRatingBar);
        raitingBar.setRating(movie.getPopularity());

        Button button = (Button) getActivity().findViewById(R.id.markAsFavoriteButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoviesService.class);
                intent.putExtra(MoviesService.MOVIE_DATA_KEY, movie);
                getActivity().startService(intent);
            }
        });

        new FetchMovieDetailTask(getActivity(), this).execute(movie);
    }

    /**
     * Format {@link Date} to dd/MM/yyyy.
     *
     * @param releaseDate Release date of a movie.
     * @return Release date in the format dd/MM/yyyy.
     */
    private String formatDate(Date releaseDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(releaseDate);
    }

    /**
     * Allows to display the movie thumbail image
     *
     * @param imageName Name of the image to display.
     * @param imageView Control to display the thumbail image for the movie
     */
    private void displayImage(String imageName, ImageView imageView) {
        Picasso.with(getActivity()).load(String.format("http://image.tmdb.org/t/p/w185/%s", imageName)).into(imageView);
    }

    /**
     * It extract the data from the Intent.
     */
    private void extractMovieDetailDataFromIntent() {
        Intent intent = getActivity().getIntent();
        movie = intent.getParcelableExtra(MOVIE_DETAIL_KEY);
    }

    @Override
    public void onPreExecute() {
        UserInterfaceHelper.displayProgressDialog(getActivity(), buildDialogData(), PROGRESS_DIALOG_TAG);
    }

    @Override
    public void update(DataResult<Movie, Exception> data) {
        UserInterfaceHelper.deleteProgressDialog(getActivity(), PROGRESS_DIALOG_TAG);
        if (!data.isException()) {
            this.movie = data.getData();
            displayExtraData();
        } else {
            Toast.makeText(getActivity(), R.string.error_connection_message, Toast.LENGTH_SHORT).show();
        }
    }

    private void displayExtraData() {
        if (getActivity() != null) {
            Log.d(TAG, "Videos "+movie.getVideos());
            MovieVideosAdapter videosAdapter = new MovieVideosAdapter(getActivity(), movie.getVideos());
            ListView listView = (ListView) getView().findViewById(R.id.videosListView);
            View header = getActivity().getLayoutInflater().inflate(R.layout.movie_videos_header, null);
            listView.setAdapter(videosAdapter);
            //listView.addHeaderView(header, null, false);
        }
    }

    /**
     * Creates the Data to show in the indeterminate progress dialog.
     *
     * @return Data to show in the indeterminate progress dialog.
     */
    private DialogData buildDialogData() {
        return new DialogData(R.string.app_name, R.string.message_progress_bar, false, android.R.drawable.ic_dialog_alert);
    }
}