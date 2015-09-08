package lania.edu.mx.popularmovies.net.resources;

import lania.edu.mx.popularmovies.tos.MovieResponse;
import lania.edu.mx.popularmovies.tos.ReviewsResponse;
import lania.edu.mx.popularmovies.tos.VideosResponse;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by clemente on 7/25/15.
 */
public interface MoviesResource {
    @GET("/discover/movie")
    MovieResponse getMovies(@Query("sort_by") String sortOrder, @Query("api_key") String key);

    @GET("/movie/{id}/reviews")
    ReviewsResponse getReviews(@Path("id") int id, @Query("api_key") String key);

    @GET("/movie/{id}/videos")
    VideosResponse getVideos(@Path("id") int id, @Query("apia_key") String key);
}
