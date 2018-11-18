package com.shar2wy.moviesapp.models.moviesRepo

import android.content.Context
import com.shar2wy.moviesapp.network.ApiClient
import com.shar2wy.moviesapp.network.ApiService
import io.reactivex.Flowable

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
internal class MovieRemoteDataSource(context: Context) {

    var mMovieLocalDataSource: MovieLocalDataSource = MovieLocalDataSource()

    var apiService: ApiService = ApiClient.getInstance(context).getService(ApiService::class.java)
    var apiKey = "38db4ee0b3b9fe63c95b9c835b73021f"

    val movies: Flowable<List<Movie>>
        get() = apiService.getTopRatedMovies(apiKey)
                .map { moviesResponse -> moviesResponse.results }
                .doOnNext { movies -> }
}
