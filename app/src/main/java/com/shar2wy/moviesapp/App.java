package com.shar2wy.moviesapp;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
