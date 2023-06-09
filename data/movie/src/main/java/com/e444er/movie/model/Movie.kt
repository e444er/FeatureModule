package com.e444er.movie.model

import com.e444er.database.model.MovieEntity
import com.e444er.database.model.MovieFavoriteEntity
import com.e444er.network.model.MovieApi

data class Movie(
    val id: Int,
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val releaseDate: String = "",
    val title: String = "",
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val isSelected: Boolean = false
)

internal fun MovieApi.toDomainModel(): Movie =
    Movie(
        id, originalTitle, overview, popularity, posterPath, releaseDate, title, voteAverage, voteCount
    )

internal fun MovieApi.toDatabaseModel(): MovieEntity =
    MovieEntity(
        id, originalTitle, overview, popularity, posterPath, releaseDate, title, voteAverage, voteCount,
        page
    )

internal fun MovieEntity.toDomainModel(): Movie =
    Movie(
        id, originalTitle, overview, popularity, posterPath, releaseDate, title, voteAverage, voteCount
    )

internal fun MovieFavoriteEntity.toDomainModel(): Movie =
    Movie(
        id, originalTitle, overview, 0.0, posterPath, releaseDate, title,
        0.0, 0, true
    )

internal fun Movie.toDatabaseModel(): MovieFavoriteEntity =
    MovieFavoriteEntity(
        id, originalTitle, overview, posterPath, releaseDate, title
    )
