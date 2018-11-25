package com.shar2wy.moviesapp.models.moviesRepo

import com.shar2wy.moviesapp.network.ApiClient
import com.shar2wy.moviesapp.network.ApiService
import com.shar2wy.moviesapp.util.Constants.apiKey
import io.reactivex.Flowable

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
internal class MovieRemoteDataSource() {

    var mMovieLocalDataSource: MovieLocalDataSource = MovieLocalDataSource()

    var apiService: ApiService = ApiClient.getInstance().getService(ApiService::class.java)

    val movies: Flowable<List<Movie>>
        get() = apiService.getTopRatedMovies(apiKey)
                .map { moviesResponse -> moviesResponse.results }
                .doOnNext { movies -> }
}
