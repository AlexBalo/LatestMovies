package com.balocco.movies.data.model.responses

import com.balocco.movies.data.model.Genre
import com.google.gson.annotations.SerializedName

data class GenreResponse(@SerializedName("genres") val genres: List<Genre> = emptyList())