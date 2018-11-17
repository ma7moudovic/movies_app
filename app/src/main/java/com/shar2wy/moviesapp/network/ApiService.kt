package com.shar2wy.moviesapp.network

import com.shar2wy.moviesapp.models.moviesRepo.MoviesResponse
import com.shar2wy.moviesapp.models.reviewRepo.ReviewsResponse
import com.shar2wy.moviesapp.models.trailerRepo.TrailersResponse

import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by shar2wy
 * on 4/2/18.
 * Description: description goes here
 */
interface ApiService {

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String): Flowable<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String, @Query("page") page: Int): Call<MoviesResponse>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Int, @Query("api_key") apiKey: String): Call<MoviesResponse>

    @GET("movie/{id}/reviews")
    fun getMovieReviews(@Path("id") id: Int, @Query("api_key") apiKey: String): Flowable<ReviewsResponse>

    @GET("movie/{id}/videos")
    fun getMovieTrailers(@Path("id") id: Int, @Query("api_key") apiKey: String): Flowable<TrailersResponse>

}
