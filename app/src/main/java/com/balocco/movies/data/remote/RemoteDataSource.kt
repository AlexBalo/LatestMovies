package com.balocco.movies.data.remote

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

private const val V3 = "/3/"

interface RemoteDataSource {

    @GET(V3 + "movie/popular")
    fun getPopular(@Query("page") page: Int): Observable<ResponseBody>

}
