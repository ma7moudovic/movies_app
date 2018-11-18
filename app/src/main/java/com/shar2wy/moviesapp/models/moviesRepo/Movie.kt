package com.shar2wy.moviesapp.models.moviesRepo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.io.Serializable

/**
 * Created by shar2wy on 4/7/17.
 */

open class Movie() : RealmObject(), Serializable {
    @SerializedName("poster_path")
    var posterPath: String? = null
    @SerializedName("adult")
    var isAdult: Boolean = false
    @SerializedName("overview")
    var overview: String? = null
    @SerializedName("release_date")
    var releaseDate: String? = null
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("original_title")
    var originalTitle: String? = null
    @SerializedName("original_language")
    var originalLanguage: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
    @SerializedName("vote_count")
    var voteCount: Int = 0
    @SerializedName("popularity")
    var popularity: Double? = null
    @SerializedName("video")
    var video: Boolean? = null
    @SerializedName("vote_average")
    var voteAverage: Double? = null
}