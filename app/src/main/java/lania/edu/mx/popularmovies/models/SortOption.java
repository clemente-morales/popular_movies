package lania.edu.mx.popularmovies.models;

import android.util.Log;
import android.util.SparseArray;

/**
 * Created by clemente on 7/23/15.
 */
public enum SortOption {
    POPULARITY(SortOption.POPULARITY_ID, "popularity.desc"),
    RAITING(SortOption.RAITING_ID, "popularity.desc");

    private static final String TAG = SortOption.class.getSimpleName();
    private static final int POPULARITY_ID = 1;
    private static final int RAITING_ID = 2;

    private final int id;
    private final String order;

    private static final SparseArray<SortOption> SORT_OPTIONS =
            new SparseArray<SortOption>();

    static {
        for (SortOption sortOption : values()) {
            SORT_OPTIONS.append(sortOption.getId(), sortOption);
        }
    }

    private SortOption(int id, String order) {
        this.id = id;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public String getOrder() {
        return order;
    }

    public static SortOption valueOf(int id) {
        if (SORT_OPTIONS.indexOfKey(id) < 0) {
            String mensaje = String.format("The order option with id %d doesn't exist.", id);
            Log.wtf(TAG, mensaje);
            throw new IllegalArgumentException(mensaje);
        }
        return SORT_OPTIONS.get(id);
    }
}
