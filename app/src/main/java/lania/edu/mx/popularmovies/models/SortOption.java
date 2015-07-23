package lania.edu.mx.popularmovies.models;

/**
 * Created by clemente on 7/23/15.
 */
public enum SortOption {
    POPULARITY (1, "popularity.desc"),
    RAITING (2, "popularity.desc");

    private final int id;
    private final String order;

    private SortOption(int id, String order) {
        this.id = id;
        this.order=order;
    }

    public int getId() {
        return id;
    }

    public String getOrder() {
        return order;
    }
}
