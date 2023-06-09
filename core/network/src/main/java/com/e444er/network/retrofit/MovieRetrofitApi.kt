package com.e444er.network.retrofit

import com.e444er.network.model.MovieDetailApi
import com.e444er.network.model.PopularMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MovieRetrofitApi {

    @GET("3/movie/popular?language=en_US")
    suspend fun getPopulars(@Query("page") page: Int): PopularMovieResponse

    @GET("3/movie/{movie_id}?language=en_US")
    suspend fun getDetails(@Path("movie_id") moveID: Int): MovieDetailApi
}