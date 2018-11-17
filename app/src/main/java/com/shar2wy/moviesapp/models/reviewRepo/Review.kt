package com.shar2wy.moviesapp.models.reviewRepo

import com.google.gson.annotations.SerializedName

import io.realm.RealmObject

/**
 * Created by shar2wy on 4/8/17.
 */

open class Review : RealmObject() {
    var movieId: Int = 0
    @SerializedName("id")
    var id: String? = null
    @SerializedName("author")
    var author: String? = null
    @SerializedName("content")
    var content: String? = null
}
