package com.balocco.movies.home.popular.usecase

import com.balocco.movies.data.model.responses.PopularResponse
import com.balocco.movies.data.remote.RemoteDataSource
import io.reactivex.Observable
import javax.inject.Inject

class FetchPopularMoviesUseCase @Inject constructor(
        private val remoteDataSource: RemoteDataSource
) {

    fun execute(): Observable<PopularResponse> =
            remoteDataSource.getPopularMovies(1)

}