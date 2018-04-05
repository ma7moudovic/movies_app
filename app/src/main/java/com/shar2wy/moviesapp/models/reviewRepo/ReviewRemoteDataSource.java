package com.shar2wy.moviesapp.models.reviewRepo;

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
class ReviewRemoteDataSource {

    ApiService apiService;
    String apiKey = "38db4ee0b3b9fe63c95b9c835b73021f";

    public ReviewRemoteDataSource(Context context) {
        apiService = ApiClient.getClient(context).create(ApiService.class);
    }

    public Flowable<List<Review>> getReviews(int id) {
        return apiService.getMovieReviews(id, apiKey)
                .flatMap(reviewsResponse -> Flowable.just(reviewsResponse.getResults()))
                .flatMapIterable(reviews -> reviews)
                .map(review -> {
                    review.setMovieId(id);
                    return review;
                })
                .toList()
                .toFlowable()
                .subscribeOn(AndroidSchedulers.mainThread());
    }
}
