package com.shar2wy.moviesapp.models.moviesRepo

import io.reactivex.Flowable
import io.realm.Realm
import java.util.*

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
internal class MovieLocalDataSource {

    private var realm: Realm = Realm.getDefaultInstance()

    val movies: Flowable<List<Movie>>
        get() {
            val moviesList = realm.where(Movie::class.java).findAll()
            val list = ArrayList<Movie>()
            list.addAll(moviesList)
            return Flowable.fromArray(list)
        }

    fun saveMovies(movies: List<Movie>) {
        Realm.getDefaultInstance().executeTransaction { realm -> realm.insertOrUpdate(movies) }
    }
}
