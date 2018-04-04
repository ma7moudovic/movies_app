package com.shar2wy.moviesapp.models;

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
class MovieLocalDataSource {

    private Realm realm;

    public Flowable<List<Movie>> getMovies(int perPage) {
        realm = Realm.getDefaultInstance();
        RealmResults<Movie> moviesList = realm.where(Movie.class).findAll();
        List<Movie> list = new ArrayList<>();
        list.addAll(moviesList);

        return Flowable.fromArray(list);
    }

    public void saveMovies(final List<Movie> movies) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.insertOrUpdate(movies);
                    }
                });
    }
}
