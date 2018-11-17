package com.shar2wy.moviesapp.models.reviewRepo

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
internal class ReviewRemoteDataSource(context: Context) {

    var apiService: ApiService
    var apiKey = "38db4ee0b3b9fe63c95b9c835b73021f"

    init {
        apiService = ApiClient.getInstance(context).getService(ApiService::class.java)
    }

    fun getReviews(id: Int): Flowable<List<Review>> {
        return apiService.getMovieReviews(id, apiKey)
                .flatMap { reviewsResponse -> Flowable.just(reviewsResponse.results) }
                .flatMapIterable { reviews -> reviews }
                .map { review ->
                    review.movieId = id
                    review
                }
                .toList()
                .toFlowable()
                .subscribeOn(AndroidSchedulers.mainThread())
    }
}