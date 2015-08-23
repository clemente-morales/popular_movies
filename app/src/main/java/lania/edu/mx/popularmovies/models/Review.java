package lania.edu.mx.popularmovies.models;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by clemente on 8/5/15.
 */
public class Review implements Serializable {
    private final String id;
    private final String author;
    private final String content;
    private final String url;
    private final String movieId;

    public Review(String id, String movieId, String author, String content, String url) {
        this.id = id;
        this.movieId = movieId;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public String getMovieId() {
        return movieId;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
