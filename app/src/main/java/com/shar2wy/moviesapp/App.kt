package com.shar2wy.moviesapp

import android.app.Application
import android.support.multidex.MultiDex

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
        MultiDex.install(this)

        Realm.init(this)
        val config = RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)
    }
}
