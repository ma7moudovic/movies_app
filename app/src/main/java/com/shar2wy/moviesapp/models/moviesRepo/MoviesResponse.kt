package com.shar2wy.moviesapp.models.moviesRepo

import com.google.gson.annotations.SerializedName

/**
 * Created by shar2wy on 4/7/17.
 */

class MoviesResponse {

    @SerializedName("page")
    var page: Int = 0
    @SerializedName("results")
    lateinit var results: List<Movie>
    @SerializedName("total_results")
    var totalResults: Int = 0
    @SerializedName("total_pages")
    var totalPages: Int = 0
}
