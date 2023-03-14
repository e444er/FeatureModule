package com.e444er.movie.model

import com.e444er.network.model.GenreApi

data class GenreMovie(
    val id: Int,
    val name: String
)

internal fun GenreApi.toGenreMovie() = GenreMovie(id, name)
