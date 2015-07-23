package lania.edu.mx.popularmovies.tos;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
