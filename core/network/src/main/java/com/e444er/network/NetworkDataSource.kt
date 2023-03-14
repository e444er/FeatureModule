package com.e444er.network

import com.e444er.network.model.MovieDetailApi
import com.e444er.network.model.PopularMovieResponse

interface NetworkDataSource {

    suspend fun getPopularMovies(page: Int = 0): Result<PopularMovieResponse>

    suspend fun getDetails(moveID: Int): Result<MovieDetailApi>
}