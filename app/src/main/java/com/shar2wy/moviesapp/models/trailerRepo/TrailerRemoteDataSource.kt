package com.shar2wy.moviesapp.models.trailerRepo

import android.content.Context
import com.shar2wy.moviesapp.network.ApiClient
import com.shar2wy.moviesapp.network.ApiService
import com.shar2wy.moviesapp.util.Constants.apiKey
import io.reactivex.Flowable

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
internal class TrailerRemoteDataSource(context: Context) {

    var apiService: ApiService = ApiClient.getInstance(context).getService(ApiService::class.java)

    fun getTrailers(id: Int): Flowable<List<Trailer>> {
        return apiService.getMovieTrailers(id, apiKey)
                .flatMap { trailersResponse -> Flowable.just(trailersResponse.results) }
                .flatMapIterable { trailers -> trailers }
                .map { trailer ->
                    trailer.movieId = id
                    trailer
                }
                .toList()
                .toFlowable()
    }
}
