package com.sarwoedhi.albumapps.data.response

import com.google.gson.annotations.SerializedName
import com.sarwoedhi.albumapps.data.models.Dates
import com.sarwoedhi.albumapps.data.models.Movie

data class MovieResponse(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: List<Movie>? = arrayListOf(),
    @SerializedName("dates") var dates: Dates? = null,
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
)