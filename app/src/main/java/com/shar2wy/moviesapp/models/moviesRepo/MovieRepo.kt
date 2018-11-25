package com.shar2wy.moviesapp.models.moviesRepo

import io.reactivex.Flowable

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
class MovieRepo private constructor() {
    private val remoteDataSource: MovieRemoteDataSource = MovieRemoteDataSource()
    private val localDataSource: MovieLocalDataSource = MovieLocalDataSource()

    val movies: Flowable<List<Movie>>
        get() = Flowable.concat(localDataSource.movies,
                remoteDataSource.movies)
                .doOnNext { movies -> localDataSource.saveMovies(movies) }

    companion object {

        private var INSTANCE: MovieRepo? = null

        @Synchronized
        fun getInstance(): MovieRepo {
            if (INSTANCE == null) {
                INSTANCE = MovieRepo()
            }
            return INSTANCE as MovieRepo
        }
    }
}
