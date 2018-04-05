package com.shar2wy.moviesapp.models.reviewRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
class ReviewLocalDataSource {

    private Realm realm;

    public Flowable<List<Review>> getReview(int id) {
        realm = Realm.getDefaultInstance();
        RealmResults<Review> moviesList = realm.where(Review.class).equalTo("movieId",id).findAll();
        List<Review> list = new ArrayList<>();
        list.addAll(moviesList);

        return Flowable.fromArray(list);
    }

    public void saveMReviews(final List<Review> reviews) {
        Realm.getDefaultInstance().executeTransaction(realm -> realm.insertOrUpdate(reviews));
    }
}
