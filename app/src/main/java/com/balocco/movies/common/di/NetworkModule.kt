package com.balocco.movies.common.di

import com.balocco.movies.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/* Module that contains network dependencies. */
@Module
class NetworkModule {

    @Provides @ApplicationScope
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides @ApplicationScope
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides @ApplicationScope
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides @ApplicationScope
    fun provideOkHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    @Provides @ApplicationScope
    fun provideRetrofit(
            converterFactory: Converter.Factory,
            callAdapterFactory: CallAdapter.Factory,
            okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .client(okHttpClient)
            .build()

    @Provides @ApplicationScope
    fun provideRemoteDataSource(
            retrofit: Retrofit
    ): RemoteDataSource = retrofit.create(RemoteDataSource::class.java)

}