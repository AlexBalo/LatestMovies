package com.balocco.movies.common.di

import android.content.Context
import com.balocco.movies.common.network.MoviesInterceptor
import com.balocco.movies.data.remote.RemoteDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/"
private const val DATE_FORMAT = "yyyy-MM-dd"

/* Module that contains network dependencies. */
@Module
class NetworkModule {

    @Provides @ApplicationScope
    fun providePicasso(context: Context): Picasso = Picasso.with(context)

    @Provides @ApplicationScope
    fun provideGson(): Gson = GsonBuilder().setDateFormat(DATE_FORMAT).create()

    @Provides @ApplicationScope
    fun provideConverterFactory(gson: Gson): Converter.Factory = GsonConverterFactory.create(gson)

    @Provides @ApplicationScope
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides @ApplicationScope
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides @ApplicationScope
    fun provideMoviesInterceptor(): MoviesInterceptor = MoviesInterceptor()

    @Provides @ApplicationScope
    fun provideOkHttpClient(
            moviesInterceptor: MoviesInterceptor,
            httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(moviesInterceptor)
            .build()

    @Provides @ApplicationScope
    fun provideRetrofit(
            converterFactory: Converter.Factory,
            callAdapterFactory: CallAdapter.Factory,
            okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .client(okHttpClient)
            .build()

    @Provides @ApplicationScope
    fun provideRemoteDataSource(
            retrofit: Retrofit
    ): RemoteDataSource = retrofit.create(RemoteDataSource::class.java)

}