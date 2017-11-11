package com.balocco.movies.home.popular

import com.balocco.movies.data.model.Movie

interface OnMovieClickListener {

    fun onMovieClicked(movie: Movie)

}