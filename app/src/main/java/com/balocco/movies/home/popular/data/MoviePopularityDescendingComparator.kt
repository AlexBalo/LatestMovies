package com.balocco.movies.home.popular.data

import com.balocco.movies.data.model.Movie

class MoviePopularityDescendingComparator : Comparator<Movie> {

    override fun compare(movie1: Movie, movie2: Movie): Int {
        val popularity1 = movie1.popularity
        val popularity2 = movie2.popularity
        return popularity2.compareTo(popularity1);
    }
}