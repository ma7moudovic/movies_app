package com.shar2wy.moviesapp.models.moviesRepo;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
public class MovieRepo {

    private static MovieRepo INSTANCE;
    private MovieRemoteDataSource remoteDataSource;
    private MovieLocalDataSource localDataSource;
    private Map<String, List<Movie>> cacheSpeakers = new HashMap<>();

    private MovieRepo(Context context) {
        this.remoteDataSource = new MovieRemoteDataSource(context);
        this.localDataSource = new MovieLocalDataSource();
    }

    public static synchronized MovieRepo getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MovieRepo(context);
        }
        return INSTANCE;
    }

    public Flowable<List<Movie>> getMovies() {
        return Flowable.concat(localDataSource.getMovies(),
                remoteDataSource.getMovies())
                .doOnNext(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        localDataSource.saveMovies(movies);
                    }
                });
    }
}
