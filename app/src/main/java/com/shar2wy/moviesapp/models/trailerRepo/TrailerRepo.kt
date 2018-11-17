package com.shar2wy.moviesapp.models.trailerRepo

import android.content.Context
import io.reactivex.Flowable

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
class TrailerRepo private constructor(context: Context) {
    private val remoteDataSource: TrailerRemoteDataSource
    private val localDataSource: TrailerLocalDataSource

    init {
        this.remoteDataSource = TrailerRemoteDataSource(context)
        this.localDataSource = TrailerLocalDataSource()
    }

    fun getTrailers(id: Int): Flowable<List<Trailer>> {
        return Flowable.concat(localDataSource.getTrailers(id),
                remoteDataSource.getTrailers(id))
                .doOnNext { trailers -> localDataSource.saveTrailers(trailers) }
    }

    companion object {

        private var INSTANCE: TrailerRepo? = null

        @Synchronized
        fun getInstance(context: Context): TrailerRepo {
            if (INSTANCE == null) {
                INSTANCE = TrailerRepo(context)
            }
            return INSTANCE as TrailerRepo
        }
    }
}
