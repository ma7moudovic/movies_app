package com.shar2wy.moviesapp.models.trailerRepo;

import android.content.Context;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
public class TrailerRepo {

    private static TrailerRepo INSTANCE;
    private TrailerRemoteDataSource remoteDataSource;
    private TrailerLocalDataSource localDataSource;

    private TrailerRepo(Context context) {
        this.remoteDataSource = new TrailerRemoteDataSource(context);
        this.localDataSource = new TrailerLocalDataSource();
    }

    public static synchronized TrailerRepo getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TrailerRepo(context);
        }
        return INSTANCE;
    }

    public Flowable<List<Trailer>> getTrailers(int id) {
        return Flowable.concat(localDataSource.getTrailers(id),
                remoteDataSource.getTrailers(id))
                .doOnNext(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailers) throws Exception {
                        localDataSource.saveTrailers(trailers);
                    }
                });
    }
}
