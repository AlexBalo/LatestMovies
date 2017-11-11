package com.balocco.movies.home.popular.data

import com.balocco.movies.data.model.Movie

class MovieReleaseDateAscendingComparator : Comparator<Movie> {

    override fun compare(movie1: Movie, movie2: Movie): Int {
        val date1 = movie1.releaseDate
        val date2 = movie2.releaseDate
        return date1.compareTo(date2);
    }
}