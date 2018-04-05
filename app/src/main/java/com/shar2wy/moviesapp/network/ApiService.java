package com.shar2wy.moviesapp.network;

import com.shar2wy.moviesapp.models.moviesRepo.MoviesResponse;
import com.shar2wy.moviesapp.models.reviewRepo.ReviewsResponse;
import com.shar2wy.moviesapp.models.trailerRepo.TrailersResponse;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shar2wy
 * on 4/2/18.
 * Description: description goes here
 */
public interface ApiService {

    @GET("movie/top_rated")
    Flowable<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Flowable<ReviewsResponse> getMovieReviews(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Flowable<TrailersResponse> getMovieTrailers(@Path("id") int id, @Query("api_key") String apiKey);

}
