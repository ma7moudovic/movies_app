package com.shar2wy.moviesapp.models.reviewRepo

import android.content.Context
import io.reactivex.Flowable

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
class ReviewRepo private constructor(context: Context) {
    private val remoteDataSource: ReviewRemoteDataSource
    private val localDataSource: ReviewLocalDataSource

    init {
        this.remoteDataSource = ReviewRemoteDataSource(context)
        this.localDataSource = ReviewLocalDataSource()
    }

    fun getReviews(id: Int): Flowable<List<Review>> {
        return Flowable.concat(localDataSource.getReview(id),
                remoteDataSource.getReviews(id))
                .doOnNext { reviews -> localDataSource.saveMReviews(reviews) }
    }

    companion object {

        private var INSTANCE: ReviewRepo? = null

        @Synchronized
        fun getInstance(context: Context): ReviewRepo {
            if (INSTANCE == null) {
                INSTANCE = ReviewRepo(context)
            }
            return INSTANCE as ReviewRepo
        }
    }
}
