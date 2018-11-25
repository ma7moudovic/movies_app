package com.shar2wy.moviesapp.models.trailerRepo

import io.reactivex.Flowable
import io.realm.Realm
import java.util.*

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
internal class TrailerLocalDataSource {

    private var realm: Realm = Realm.getDefaultInstance()

    fun getTrailers(id: Int): Flowable<List<Trailer>> {
        val trailersList = realm.where(Trailer::class.java).equalTo("movieId", id).findAll()
        val list = ArrayList<Trailer>()
        list.addAll(trailersList)
        return Flowable.just(list)
    }

    fun saveTrailers(trailers: List<Trailer>) {
        Realm.getDefaultInstance().executeTransaction { realm -> realm.insertOrUpdate(trailers) }
    }
}
