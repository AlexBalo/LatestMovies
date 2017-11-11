package com.balocco.movies.common.di

import com.balocco.movies.data.store.GenreStore
import dagger.Module
import dagger.Provides

/* Module that contains dependencies to access local data. */
@Module
class DataModule {

    @Provides @ApplicationScope
    fun provideGenreStore(): GenreStore = GenreStore()
}