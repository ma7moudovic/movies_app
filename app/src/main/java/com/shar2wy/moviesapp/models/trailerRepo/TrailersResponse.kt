package com.shar2wy.moviesapp.models.trailerRepo

import com.google.gson.annotations.SerializedName

/**
 * Created by shar2wy on 4/8/17.
 */

class TrailersResponse {
    @SerializedName("results")
    var results: List<Trailer>? = null
}
