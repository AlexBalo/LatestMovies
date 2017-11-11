package com.balocco.movies.home.popular.usecase

import com.balocco.movies.data.model.Movie
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class SortPopularMoviesUseCase @Inject constructor(
) {

    fun execute(list: List<Movie>,
                comparator: Comparator<Movie>
    ): Single<List<Movie>> =
            Single.create { subscriber ->
                Collections.sort(list, comparator)
                subscriber.onSuccess(list)
            }
}