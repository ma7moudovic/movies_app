package com.shar2wy.moviesapp.models.trailerRepo;

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
class TrailerLocalDataSource {

    private Realm realm;

    public Flowable<List<Trailer>> getTrailers(int id) {
        realm = Realm.getDefaultInstance();
        RealmResults<Trailer> trailersList = realm.where(Trailer.class).equalTo("mMovieId", id).findAll();
        List<Trailer> list = new ArrayList<>();
        list.addAll(trailersList);

        return Flowable.just(list);
    }

    public void saveTrailers(final List<Trailer> trailers) {
        Realm.getDefaultInstance().executeTransaction(realm -> realm.insertOrUpdate(trailers));
    }
}
