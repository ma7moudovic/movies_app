package com.shar2wy.moviesapp.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shar2wy.moviesapp.models.moviesRepo.Movie
import com.shar2wy.moviesapp.models.reviewRepo.Review
import com.shar2wy.moviesapp.models.trailerRepo.Trailer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by shar2wy
 * on 11/18/18.
 * Description: description goes here
 */

class MoviesViewModel : ViewModel() {

    private val moviesUseCase = MoviesUseCase(Schedulers.io(), AndroidSchedulers.mainThread())

    private val moviesListLiveData = MutableLiveData<List<Movie>>()
    private val reviewsListLiveData = MutableLiveData<List<Review>>()
    private val trailersListLiveData = MutableLiveData<List<Trailer>>()

    val movies: LiveData<List<Movie>>
        get() = moviesListLiveData

    val reviews: LiveData<List<Review>>
        get() = reviewsListLiveData

    val trailers: LiveData<List<Trailer>>
        get() = trailersListLiveData

    fun getMovies() {
        moviesUseCase.addObserver(moviesUseCase
                .getMovies()
                .doOnNext { t -> moviesListLiveData.value = t }
                .doOnError { t -> t.printStackTrace() }
                .subscribe()
        )
    }

    fun getReviews(id: Int) {
        moviesUseCase.addObserver(moviesUseCase
                .getReviews(id)
                .doOnNext { it -> reviewsListLiveData.value = it }
                .doOnError { it -> it.printStackTrace() }
                .subscribe())
    }

    fun getTrailers(id: Int) {
        moviesUseCase.addObserver(moviesUseCase
                .getTrailers(id)
                .doOnNext { it -> trailersListLiveData.value = it }
                .doOnError { it -> it.printStackTrace() }
                .subscribe())
    }

    override fun onCleared() {
        super.onCleared()
        moviesUseCase.dispose()
    }
}