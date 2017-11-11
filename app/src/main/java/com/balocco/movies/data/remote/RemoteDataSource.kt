package com.balocco.movies.data.remote

import com.balocco.movies.data.model.responses.PopularResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

private const val V3 = "/3/"

interface RemoteDataSource {

    @GET(V3 + "movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Observable<PopularResponse>

}
