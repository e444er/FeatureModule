package com.e444er.movie.model

import com.e444er.network.model.PopularMovieResponse

data class PopularMovies(
    val page: Int = 0,
    val movies: List<Movie> = emptyList(),
    val totalPage: Int = 0,
    val totalResults: Int = 0
)

internal fun PopularMovieResponse.mapToPopularMovies() =
    PopularMovies(page, movies.map { it.toDomainModel() }, totalPage, totalResults)