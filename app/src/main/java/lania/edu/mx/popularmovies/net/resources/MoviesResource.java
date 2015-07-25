package lania.edu.mx.popularmovies.net.resources;

import lania.edu.mx.popularmovies.tos.MovieResponse;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by clemente on 7/25/15.
 */
public interface MoviesResource {
    @GET("/movie")
    MovieResponse getMovies(@Query("sort_by") String sortOrder, @Query("api_key") String key);
}
