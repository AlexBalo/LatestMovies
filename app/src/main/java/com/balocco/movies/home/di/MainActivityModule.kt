package com.balocco.movies.home.di

import android.app.Activity
import com.balocco.movies.common.di.ActivityScope
import com.balocco.movies.home.ui.MainActivity
import dagger.Module
import dagger.Provides

/* Module that contains specific dependencies for the Main Activity. */
@Module
@ActivityScope
class MainActivityModule {

    @Provides @ActivityScope fun providesActivity(
            activity: MainActivity
    ): Activity = activity

}
