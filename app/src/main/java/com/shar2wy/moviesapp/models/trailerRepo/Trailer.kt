package com.shar2wy.moviesapp.models.trailerRepo

import com.google.gson.annotations.SerializedName

import io.realm.RealmObject

/**
 * Created by shar2wy on 4/8/17.
 */

open class Trailer : RealmObject() {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("key")
    var key: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("site")
    var site: String? = null
    @SerializedName("type")
    var type: String? = null
    var movieId: Int = 0
}
