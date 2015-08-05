package lania.edu.mx.popularmovies.models;

/**
 * Created by clemente on 8/5/15.
 */
public class Video {
    private final String id;
    private final String key;
    private final String name;
    private final int size;
    private final String type;
    private final String site;
    private final String encode;

    public Video(String id, String key, String name, int size, String type, String site, String encode) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.size = size;
        this.type = type;
        this.site = site;
        this.encode = encode;
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
}
