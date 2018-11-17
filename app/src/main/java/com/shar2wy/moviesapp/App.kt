package com.shar2wy.moviesapp

import android.app.Application

import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by shar2wy
 * on 4/4/18.
 * Description: description goes here
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)
    }
}
