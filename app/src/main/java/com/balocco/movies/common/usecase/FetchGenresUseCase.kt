package com.balocco.movies.common.usecase

import com.balocco.movies.data.remote.RemoteDataSource
import com.balocco.movies.data.store.GenreStore
import io.reactivex.Completable
import javax.inject.Inject

class FetchGenresUseCase @Inject constructor(
        private val remoteDataSource: RemoteDataSource,
        private val genreStore: GenreStore
) {

    fun execute(): Completable =
            remoteDataSource.getGenres()
                    .flatMapCompletable {
                        Completable.fromAction {
                            genreStore.storeGenres(it.genres)
                        }
                    }

}