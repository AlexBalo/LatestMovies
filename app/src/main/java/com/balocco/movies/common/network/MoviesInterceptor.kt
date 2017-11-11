package com.balocco.movies.common.network

import okhttp3.Interceptor
import okhttp3.Response

private const val KEY_APY_KEY = "api_key"
private const val VALUE_APY_KEY = "410bc004d3f349e1e2a687516fa6b866"

private const val KEY_LANGUAGE = "language"
private const val VALUE_LANGUAGE = "en-US"

class MoviesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url()
        val newUrl = originalUrl.newBuilder()
                .addQueryParameter(KEY_APY_KEY, VALUE_APY_KEY)
                .addQueryParameter(KEY_LANGUAGE, VALUE_LANGUAGE)
                .build()

        val requestBuilder = original.newBuilder().url(newUrl)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}
