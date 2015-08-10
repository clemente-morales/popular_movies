package lania.edu.mx.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class encapsulates the information of a movie.
 * Created by clemente on 7/22/15.
 */
public class Movie implements Parcelable {
    /**
     * Max popularity given to a movie.
     */
    public static final int MAX_POPULARITY = 10;

    /**
     * Max number of starts for the popularity of a movie.
     */
    public static final int MAX_STARTS = 5;

    public static final String IMAGE_PATH = "movieImages";

    /**
     * Original title.
     */
    private final String title;

    /**
     * Movie poster image name.
     */
    private final String posterImageName;

    /**
     * Plot synopsis.
     */
    private final String synopsis;

    /**
     * User raiting.
     */
    private final float popularity;

    /**
     * Release date.
     */
    private final Date releaseDate;

    /**
     * Movie backdrop image name.
     */
    private final String backDropImageName;

    /**
     * Identifier of the movie.
     */
    private final int id;

    /**
     * vote average for the movie.
     */
    private float voteAverage;

    /**
     * If the movie is marked as favorite.
     */
    private boolean markedAsFavorite;

    // TODO Include in parcel
    private ArrayList<Review> reviews;

    // TODO Include in parcel
    private ArrayList<Video> videos;

    /**
     * Allows to construct an instance of this class to encapsulate the movie with its data.
     *
     * @param id                Id of the movie.
     * @param title             Title of the movie.
     * @param posterImageName   Image name of the movie.
     * @param synopsis          Synopsis of the movie.
     * @param popularity        Rainting of the movie.
     * @param releaseDate       Release date of the movie.
     * @param backDropImageName Movie backdrop image name.
     * @param voteAverage       Vote average for the movie.
     */
    public Movie(int id, String title, String posterImageName, String synopsis, float popularity,
                 Date releaseDate, String backDropImageName, float voteAverage) {
        this.id = id;
        this.title = title;
        this.posterImageName = posterImageName;
        this.synopsis = synopsis;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
        this.backDropImageName = backDropImageName;
        this.voteAverage = voteAverage;
    }

    /**
     * Allows to create an instance of this class extracting the data from the Parcel.
     *
     * @param source Parcle with the movie data.
     */
    private Movie(Parcel source) {
        this.id = source.readInt();
        this.title = source.readString();
        this.posterImageName = source.readString();
        this.synopsis = source.readString();
        this.popularity = source.readFloat();
        this.releaseDate = (Date) source.readSerializable();
        this.backDropImageName = source.readString();
        this.voteAverage = source.readFloat();
        this.markedAsFavorite = source.readInt() == 1 ? true : false;
    }

    /**
     * Instance of {@code Parcelable.Creator} required to implement Parcelable. This is used to create
     * the items of Movie previously serialized in the Parcel.
     */
    public static final Parcelable.Creator<Movie> CREATOR =
            new Parcelable.Creator<Movie>() {
                @Override
                public Movie createFromParcel(Parcel source) {
                    return new Movie(source);
                }

                @Override
                public Movie[] newArray(int size) {
                    return new Movie[size];
                }

            };

    /**
     * Allows to get the Original title.
     *
     * @return Original title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Allows to get the Movie poster image name.
     *
     * @return Movie poster image name.
     */
    public String getPosterImageName() {
        return posterImageName;
    }

    /**
     * Allows to get the Plot synopsis.
     *
     * @return Plot synopsis.
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Allows to get the User raiting.
     *
     * @return User raiting.
     */
    public float getPopularity() {
        return (voteAverage / MAX_POPULARITY) * MAX_STARTS;
    }

    /**
     * Allows to get the Release date.
     *
     * @return Release date.
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Allows to get the Movie backdrop image name.
     *
     * @return Movie backdrop image name.
     */
    public String getBackDropImageName() {
        return backDropImageName;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(posterImageName);
        dest.writeString(synopsis);
        dest.writeFloat(popularity);
        dest.writeSerializable(releaseDate);
        dest.writeString(backDropImageName);
        dest.writeFloat(voteAverage);
        dest.writeInt(markedAsFavorite ? 1 : 0);
    }

    /**
     * Allows to get the movie id.
     *
     * @return Id of the movie.
     */
    public int getId() {
        return id;
    }

    public String getFormatReleaseDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return null == releaseDate ? StringUtils.EMPTY : simpleDateFormat.format(releaseDate);
    }

    /**
     * Allows to get the vote average for the movie.
     * @return vote average for the movie.
     */
    public float getVoteAverage() {
        return voteAverage;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public boolean isMarkedAsFavorite() {
        return markedAsFavorite;
    }

    public void setMarkedAsFavorite(boolean markedAsFavorite) {
        this.markedAsFavorite = markedAsFavorite;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }
}
