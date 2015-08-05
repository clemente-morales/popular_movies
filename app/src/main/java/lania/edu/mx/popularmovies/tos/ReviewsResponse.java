package lania.edu.mx.popularmovies.tos;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

/**
 * Created by clemente on 8/5/15.
 */
public class ReviewsResponse {
    @JsonProperty("results")
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
