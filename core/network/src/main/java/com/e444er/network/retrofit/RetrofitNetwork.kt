package com.e444er.network.retrofit

import com.e444er.network.NetworkDataSource
import com.e444er.network.ext.parseResult
import com.e444er.network.model.MovieDetailApi
import com.e444er.network.model.PopularMovieResponse
import javax.inject.Inject

internal class RetrofitNetwork @Inject constructor(
    private val movieRetrofitApi: MovieRetrofitApi
): NetworkDataSource {

    override suspend fun getPopularMovies(page: Int): Result<PopularMovieResponse> =
        movieRetrofitApi.getPopulars(page).parseResult()

    override suspend fun getDetails(moveID: Int): Result<MovieDetailApi> =
        movieRetrofitApi.getDetails(moveID).parseResult()
}