package com.shar2wy.moviesapp.models.reviewRepo;

import android.content.Context;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
public class ReviewRepo {

    private static ReviewRepo INSTANCE;
    private ReviewRemoteDataSource remoteDataSource;
    private ReviewLocalDataSource localDataSource;

    private ReviewRepo(Context context) {
        this.remoteDataSource = new ReviewRemoteDataSource(context);
        this.localDataSource = new ReviewLocalDataSource();
    }

    public static synchronized ReviewRepo getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ReviewRepo(context);
        }
        return INSTANCE;
    }

    public Flowable<List<Review>> getReviews(int id) {
        return Flowable.concat(localDataSource.getReview(id),
                remoteDataSource.getReviews(id))
                .doOnNext(new Consumer<List<Review>>() {
                    @Override
                    public void accept(List<Review> reviews) throws Exception {
                        localDataSource.saveMReviews(reviews);
                    }
                });
    }
}
