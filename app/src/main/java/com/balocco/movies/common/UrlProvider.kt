package com.balocco.movies.common

import javax.inject.Inject

private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

class UrlProvider @Inject constructor() {

    fun provideUrlForPoster(suffix: String): String = BASE_IMAGE_URL + suffix

    fun provideUrlForBackdrop(suffix: String): String = BASE_IMAGE_URL + suffix

}