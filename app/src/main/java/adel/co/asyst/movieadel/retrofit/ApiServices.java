package adel.co.asyst.movieadel.retrofit;

import adel.co.asyst.movieadel.retrofit.response.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("discover/movie")
    Call<MovieResponse> getMovie(@Query("api_key") String apiKey, @Query("year") String year, @Query("page") int page, @Query("sort_by") String sort_by);

    @GET("search/movie")
    Call<MovieResponse> searchMovie(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);
}
