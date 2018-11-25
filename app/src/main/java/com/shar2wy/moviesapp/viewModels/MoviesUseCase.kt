package com.shar2wy.moviesapp.viewModels

import com.shar2wy.moviesapp.models.moviesRepo.Movie
import com.shar2wy.moviesapp.models.moviesRepo.MovieRemoteDataSource
import com.shar2wy.moviesapp.models.moviesRepo.MovieRepo
import com.shar2wy.moviesapp.models.reviewRepo.Review
import com.shar2wy.moviesapp.models.reviewRepo.ReviewRepo
import com.shar2wy.moviesapp.models.trailerRepo.Trailer
import com.shar2wy.moviesapp.models.trailerRepo.TrailerRepo
import io.reactivex.Flowable
import io.reactivex.Scheduler

/**
 * Created by shar2wy
 * on 11/18/18.
 * Description: description goes here
 */

class MoviesUseCase(executorThreadParam: Scheduler, uiThreadParam: Scheduler) : UseCase(executorThreadParam, uiThreadParam) {

    var movieRepo: MovieRepo = MovieRepo.getInstance()
    var reviewsRepo: ReviewRepo = ReviewRepo.getInstance()
    var trailerRepo: TrailerRepo = TrailerRepo.getInstance()
    private var movieRemoteDataSource: MovieRemoteDataSource = MovieRemoteDataSource()

    fun getMovies(): Flowable<List<Movie>> {
        return movieRepo
                .movies
                .subscribeOn(executorThread)
                .observeOn(uiThread)
    }

    fun getReviews(id: Int): Flowable<List<Review>> {
        return reviewsRepo
                .getReviews(id)
                .subscribeOn(executorThread)
                .observeOn(uiThread)
    }

    fun getTrailers(id: Int): Flowable<List<Trailer>> {
        return trailerRepo.getTrailers(id)
                .subscribeOn(executorThread)
                .observeOn(uiThread)
    }
}