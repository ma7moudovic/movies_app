package com.shar2wy.moviesapp.models.reviewRepo

import com.google.gson.annotations.SerializedName

/**
 * Created by shar2wy on 4/8/17.
 */

class ReviewsResponse {
    @SerializedName("page")
    var page: Int = 0
    @SerializedName("results")
    var results: List<Review>? = null
    @SerializedName("total_results")
    var totalResults: Int = 0
    @SerializedName("total_pages")
    var totalPages: Int = 0
}
