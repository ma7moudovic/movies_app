package com.shar2wy.moviesapp.models.moviesRepo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.io.Serializable

/**
 * Created by shar2wy on 4/7/17.
 */

open class Movie(posterPath: String, adult: Boolean, overview: String, releaseDate: String, genreIds: List<Int>, id: Int, originalTitle: String, originalLanguage: String, title: String, backdropPath: String, @SerializedName("popularity") var popularity: Double?, voteCount: Int?, @SerializedName("video") var video: Boolean?, @SerializedName("vote_average") var voteAverage: Double?) : RealmObject(), Serializable {

    @SerializedName("poster_path")
    var posterPath: String = posterPath
    @SerializedName("adult")
    var isAdult: Boolean = adult
    @SerializedName("overview")
    var overview: String? = overview
    @SerializedName("release_date")
    var releaseDate: String? = releaseDate
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("original_title")
    var originalTitle: String? = originalTitle
    @SerializedName("original_language")
    var originalLanguage: String? = originalLanguage
    @SerializedName("title")
    var title: String? = title
    @SerializedName("backdrop_path")
    var backdropPath: String? = backdropPath
    @SerializedName("vote_count")
    var voteCount: Int = 0

    init {
        this.id = id
    }
}