package com.shar2wy.moviesapp.models.trailerRepo

import android.content.Context

import com.shar2wy.moviesapp.network.ApiClient
import com.shar2wy.moviesapp.network.ApiService

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
internal class TrailerRemoteDataSource(context: Context) {

    var apiService: ApiService
    var apiKey = "38db4ee0b3b9fe63c95b9c835b73021f"

    init {
        apiService = ApiClient.getInstance(context).getService(ApiService::class.java)
    }

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
                .subscribeOn(AndroidSchedulers.mainThread())
    }
}