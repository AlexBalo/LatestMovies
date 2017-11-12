package com.balocco.movies.data.store

import com.balocco.movies.data.model.Genre

class GenreStore {

    private val genres: MutableList<Genre> = ArrayList()

    fun hasData(): Boolean = genres.isNotEmpty()

    fun storeGenres(newGenres: List<Genre>) {
        genres.clear()
        genres.addAll(newGenres)
    }

    fun genresFromIds(ids: List<Int>): List<Genre> {
        val genresToReturn = arrayListOf<Genre>()
        (0 until ids.size)
                .map { ids[it] }
                .forEach { id ->
                    (0 until genres.size)
                            .map { genres[it] }
                            .filterTo(genresToReturn) { id.toString() == it.id }
                }
        return genresToReturn
    }
}