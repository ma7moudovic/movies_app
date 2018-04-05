package com.shar2wy.moviesapp.models.moviesRepo;

import android.content.Context;

import com.shar2wy.moviesapp.network.ApiClient;
import com.shar2wy.moviesapp.network.ApiService;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
class MovieRemoteDataSource {

    MovieLocalDataSource mMovieLocalDataSource;

    ApiService apiService;
    String apiKey = "38db4ee0b3b9fe63c95b9c835b73021f";

    public MovieRemoteDataSource(Context context) {
        apiService = ApiClient.getClient(context).create(ApiService.class);
        mMovieLocalDataSource = new MovieLocalDataSource();
    }

    public Flowable<List<Movie>> getMovies() {
        return apiService.getTopRatedMovies(apiKey)
                .map(moviesResponse -> moviesResponse.getResults())
                .doOnNext(movies -> {
                    //saveToRealm
//                        mMovieLocalDataSource.saveMovies(movies);
                }).subscribeOn(AndroidSchedulers.mainThread());
    }
}
