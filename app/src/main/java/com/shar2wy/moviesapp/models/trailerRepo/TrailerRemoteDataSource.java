package com.shar2wy.moviesapp.models.trailerRepo;

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
class TrailerRemoteDataSource {

    ApiService apiService;
    String apiKey = "38db4ee0b3b9fe63c95b9c835b73021f";

    public TrailerRemoteDataSource(Context context) {
        apiService = ApiClient.getClient(context).create(ApiService.class);
    }

    public Flowable<List<Trailer>> getTrailers(int id) {
        return apiService.getMovieTrailers(id, apiKey)
                .flatMap(trailersResponse -> Flowable.just(trailersResponse.getResults()))
                .flatMapIterable(trailers -> trailers)
                .map(trailer -> {
                    trailer.setMovieId(id);
                    return trailer;
                })
                .toList()
                .toFlowable()
                .subscribeOn(AndroidSchedulers.mainThread());
    }
}
