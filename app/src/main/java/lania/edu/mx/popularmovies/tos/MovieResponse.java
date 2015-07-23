package lania.edu.mx.popularmovies.tos;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.util.List;

/**
 * Response from a query to the themoviedb API.
 * Created by clemente on 7/23/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
@JsonDeserialize()
public class MovieResponse {
    @JsonProperty("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
