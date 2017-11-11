package com.balocco.movies.home.di

import com.balocco.movies.common.di.ActivityScope
import com.balocco.movies.home.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@ActivityScope
abstract class MainActivityViewsBuildersModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun contributeMainActivityInjector(): MainActivity

}