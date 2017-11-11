package com.balocco.movies.home.popular.usecase

import com.balocco.movies.data.model.responses.PopularResponse
import com.balocco.movies.data.remote.RemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class FetchPopularMoviesUseCase @Inject constructor(
        private val remoteDataSource: RemoteDataSource
) {

    fun execute(page: Int): Single<PopularResponse> =
            remoteDataSource.getPopularMovies(page)

}