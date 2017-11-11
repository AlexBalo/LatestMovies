package com.balocco.movies.common.di

import android.app.Application
import android.content.Context
import com.balocco.movies.common.scheduler.RealSchedulerProvider
import com.balocco.movies.common.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides

/* Module that contains generic android dependencies. */
@Module
class AppModule {

    @Provides @ApplicationScope
    fun provideContext(
            application: Application
    ): Context = application.applicationContext

    @Provides @ApplicationScope
    fun provideSchedulerProvider(
    ): SchedulerProvider = RealSchedulerProvider()
}