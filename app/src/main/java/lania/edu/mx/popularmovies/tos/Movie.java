package lania.edu.mx.popularmovies.tos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * Created by clemente on 7/23/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
@JsonDeserialize()
public class Movie {
    @JsonProperty("adult")
    private boolean adultTarget;

    @JsonProperty("backdrop_path")
    private String backDropImage;

    @JsonProperty("original_title")
    private String originalTitle;

    private String title;

    private String overview;

    @JsonProperty("release_date")
    private Date releaseDate;

    @JsonProperty("poster_path")
    private String posterImage;

    @JsonProperty("vote_average")
    private float populariy;

    @JsonProperty("original_language")
    private String language;

    private int id;

    public boolean isAdultTarget() {
        return adultTarget;
    }

    public void setAdultTarget(boolean adultTarget) {
        this.adultTarget = adultTarget;
    }

    public String getBackDropImage() {
        return backDropImage;
    }

    public void setBackDropImage(String backDropImage) {
        this.backDropImage = backDropImage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    @JsonDeserialize(using = JsonDateDeserializer.class)
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public float getPopulariy() {
        return populariy;
    }

    public void setPopulariy(float populariy) {
        this.populariy = populariy;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
