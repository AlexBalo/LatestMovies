package com.balocco.movies.data.model.responses

import com.balocco.movies.data.model.Movie
import com.google.gson.annotations.SerializedName

data class PopularResponse(@SerializedName("page") val page: Int = 1,
                           @SerializedName("results") val results: List<Movie> = emptyList(),
                           @SerializedName("total_results") val totalResults: Int = 0,
                           @SerializedName("total_pages") val totalPages: Int = 0
)