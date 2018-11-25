package com.shar2wy.moviesapp.models.trailerRepo

import io.reactivex.Flowable

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
class TrailerRepo private constructor() {
    private val remoteDataSource: TrailerRemoteDataSource = TrailerRemoteDataSource()
    private val localDataSource: TrailerLocalDataSource = TrailerLocalDataSource()

    fun getTrailers(id: Int): Flowable<List<Trailer>> {
        return Flowable.concat(localDataSource.getTrailers(id),
                remoteDataSource.getTrailers(id))
                .doOnNext { trailers -> localDataSource.saveTrailers(trailers) }
    }

    companion object {

        private var INSTANCE: TrailerRepo? = null

        @Synchronized
        fun getInstance(): TrailerRepo {
            if (INSTANCE == null) {
                INSTANCE = TrailerRepo()
            }
            return INSTANCE as TrailerRepo
        }
    }
}
