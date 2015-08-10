package lania.edu.mx.popularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import lania.edu.mx.popularmovies.R;
import lania.edu.mx.popularmovies.models.Review;

/**
 * Provides the list view with the view corresponding to each element of a review for a video.
 * Created by clerks on 8/10/15.
 */
public class MovieReviewsAdapter extends BaseAdapter {
    /**
     * Context to inflate the views.
     */
    private final Context context;

    /**
     * Reviews to display.
     */
    private List<Review> reviews = Collections.emptyList();

    /**
     * Allows to create an instance of this class to provide the views for each element of a review in
     * request from a list view in each movie item.
     *
     * @param context Context of the application.
     * @param reviews  Review list to display.
     */
    public MovieReviewsAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_item_movie_review, null);
            holder = new ViewHolder();
            holder.authorTextView = (TextView) convertView.findViewById(R.id.authorTextView);
            holder.reviewTextView = (TextView) convertView.findViewById(R.id.reviewTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Review review = reviews.get(position);

        holder.authorTextView.setText(review.getAuthor());
        holder.reviewTextView.setText(review.getContent());


        return convertView;
    }

    /**
     * Encapsulates the view controls that represent a review for a movie.
     */
    private static class ViewHolder {
        /**
         * Author of the review.
         */
        private TextView authorTextView;

        /**
         * Content of the review.
         */
        public TextView reviewTextView;
    }
}