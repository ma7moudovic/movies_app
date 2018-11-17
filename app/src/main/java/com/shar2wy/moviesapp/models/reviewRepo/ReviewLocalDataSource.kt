package com.shar2wy.moviesapp.models.reviewRepo

import io.reactivex.Flowable
import io.realm.Realm
import java.util.*

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
internal class ReviewLocalDataSource {

    private var realm: Realm? = null

    fun getReview(id: Int): Flowable<List<Review>> {
        realm = Realm.getDefaultInstance()
        val moviesList = realm!!.where(Review::class.java).equalTo("movieId", id).findAll()
        val list = ArrayList<Review>()
        list.addAll(moviesList)

        return Flowable.fromArray(list)
    }

    fun saveMReviews(reviews: List<Review>) {
        Realm.getDefaultInstance().executeTransaction { realm -> realm.insertOrUpdate(reviews) }
    }
}
