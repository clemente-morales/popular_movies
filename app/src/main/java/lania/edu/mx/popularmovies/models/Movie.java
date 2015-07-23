package lania.edu.mx.popularmovies.models;

import java.util.Date;

/**
 * This class encapsulates the information of a movie.
 * Created by clemente on 7/22/15.
 */
public class Movie {
    /**
     * Original title.
     */
    private final String title;

    /**
     * Movie poster image name.
     */
    private final String imageName;

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
     * Allows to construct an instance of this class to encapsulate the movie with its data.
     * @param title Title of the movie.
     * @param imageName Image name of the movie.
     * @param synopsis Synopsis of the movie.
     * @param popularity Rainting of the movie.
     * @param releaseDate Release date of the movie.
     */
    public Movie(String title, String imageName, String synopsis, float popularity, Date releaseDate) {
        this.title = title;
        this.imageName = imageName;
        this.synopsis = synopsis;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
    }

    /**
     * Allows to get the Original title.
     * @return Original title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Allows to get the Movie poster image name.
     * @return Movie poster image name.
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Allows to get the Plot synopsis.
     * @return Plot synopsis.
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Allows to get the User raiting.
     * @return User raiting.
     */
    public float getPopularity() {
        return popularity;
    }

    /**
     * Allows to get the Release date.
     * @return Release date.
     */
    public Date getReleaseDate() {
        return releaseDate;
    }
}
