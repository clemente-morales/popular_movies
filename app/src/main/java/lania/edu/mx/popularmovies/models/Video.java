package lania.edu.mx.popularmovies.models;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by clemente on 8/5/15.
 */
public class Video implements Serializable {
    private final String id;
    private final String key;
    private final String name;
    private final int size;
    private final String type;
    private final String site;
    private final String encode;
    private String movieId;

    public Video(String id, String movieId, String key, String name, int size, String type, String site, String encode) {
        this.movieId = movieId;
        this.id = id;
        this.key = key;
        this.name = name;
        this.size = size;
        this.type = type;
        this.site = site;
        this.encode = encode;
    }

    public Video(String id, String movieId, String name, String key) {
        this.id = id;
        this.movieId = movieId;
        this.key = key;
        this.name = name;
        this.size = 0;
        this.type = null;
        this.site = null;
        this.encode = null;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getSite() {
        return site;
    }

    public String getEncode() {
        return encode;
    }

    public String getMovieId() {
        return movieId;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
