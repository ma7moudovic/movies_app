package com.shar2wy.moviesapp.models.reviewRepo

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
internal class ReviewRemoteDataSource(context: Context) {

    var apiService: ApiService = ApiClient.getInstance(context).getService(ApiService::class.java)

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
    }
}
