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
import lania.edu.mx.popularmovies.models.Video;

/**
 * Provides the list view with the view corresponding to each element of a video.
 * Created by clemente on 8/8/15.
 */
public class MovieVideosAdapter extends BaseAdapter {
    /**
     * Context to inflate the views.
     */
    private final Context context;

    /**
     * Video list to display.
     */
    private List<Video> videos = Collections.emptyList();

    /**
     * Allows to create an instance of this class to provide the views for each element of a video in
     * request from a list view in each movie item.
     *
     * @param context Context of the application.
     * @param videos  Video list to display.
     */
    public MovieVideosAdapter(Context context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
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
            convertView = vi.inflate(R.layout.list_item_movie_video, null);
            holder = new ViewHolder();
            holder.videoTitleTextView = (TextView) convertView.findViewById(R.id.videoTitleTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Video video = videos.get(position);
        holder.videoTitleTextView.setText(video.getName());


        return convertView;
    }

    /**
     * Encapsulates the view controls that represent a video. This is an element of the ViewHolder pattern
     * to limit the number of calls to findViewByID
     */
    private static class ViewHolder {
        /**
         * Original title of the video.
         */
        private TextView videoTitleTextView;
    }
}
