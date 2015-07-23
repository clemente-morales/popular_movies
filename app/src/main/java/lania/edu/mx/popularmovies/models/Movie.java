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
    private String title;

    /**
     * Movie poster image name.
     */
    private String imageName;

    /**
     * Plot synopsis.
     */
    private String synopsis;

    /**
     * User raiting.
     */
    private int raiting;

    /**
     * Release date.
     */
    private Date releaseDate;

    /**
     * Allows to get the Original title.
     * @return Original title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Allows to set the Original title.
     * @param title Original title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Allows to get the Movie poster image name.
     * @return Movie poster image name.
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Allows to set the Movie poster image name.
     * @param imageName Movie poster image name.
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Allows to get the Plot synopsis.
     * @return Plot synopsis.
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Allows to set the Plot synopsis.
     * @param synopsis Plot synopsis.
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * Allows to get the User raiting.
     * @return User raiting.
     */
    public int getRaiting() {
        return raiting;
    }

    /**
     * Allows to set the User raiting.
     * @param raiting
     */
    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    /**
     * Allows to get the Release date.
     * @return Release date.
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Allows to set the Release date.
     * @param releaseDate Release date.
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
