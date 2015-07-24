package lania.edu.mx.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * This class encapsulates the information of a movie.
 * Created by clemente on 7/22/15.
 */
public class Movie implements Parcelable {
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
     * Language of the movie.
     */
    private final String language;

    /**
     * Allows to construct an instance of this class to encapsulate the movie with its data.
     *
     * @param title             Title of the movie.
     * @param posterImageName   Image name of the movie.
     * @param synopsis          Synopsis of the movie.
     * @param popularity        Rainting of the movie.
     * @param releaseDate       Release date of the movie.
     * @param backDropImageName Movie backdrop image name.
     * @param language          Original language of the movie.
     */
    public Movie(String title, String posterImageName, String synopsis, float popularity, Date releaseDate, String backDropImageName, String language) {
        this.title = title;
        this.posterImageName = posterImageName;
        this.synopsis = synopsis;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
        this.backDropImageName = backDropImageName;
        this.language = language;
    }

    /**
     * Allows to create an instance of this class extracting the data from the Parcel.
     *
     * @param source Parcle with the movie data.
     */
    private Movie(Parcel source) {
        this.title = source.readString();
        this.posterImageName = source.readString();
        this.synopsis = source.readString();
        this.popularity = source.readFloat();
        this.releaseDate = (Date) source.readSerializable();
        this.backDropImageName = source.readString();
        this.language = source.readString();
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
        return (popularity / 10) * 5;
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

    /**
     * Allows to get the Language of the movie.
     *
     * @return Language of the movie.
     */
    public String getLanguage() {
        return language;
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
        dest.writeString(title);
        dest.writeString(posterImageName);
        dest.writeString(synopsis);
        dest.writeFloat(popularity);
        dest.writeSerializable(releaseDate);
        dest.writeString(backDropImageName);
        dest.writeString(language);
    }
}
