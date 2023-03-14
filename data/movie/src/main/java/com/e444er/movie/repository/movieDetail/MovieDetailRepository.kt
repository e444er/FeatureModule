package com.e444er.movie.repository.movieDetail

import com.e444er.movie.model.Movie
import com.e444er.movie.model.MovieDetail

interface MovieDetailRepository {

    suspend fun getFavoriteMovie(id: Int): Movie?

    suspend fun getDetails(moveID: Int): Result<MovieDetail>

}