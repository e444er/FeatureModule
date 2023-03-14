package com.e444er.movie.repository.movieDetail

import com.e444er.database.dao.MovieFavoriteDao
import com.e444er.movie.model.Movie
import com.e444er.movie.model.MovieDetail
import com.e444er.movie.model.mapToDomain
import com.e444er.movie.model.toDomainModel
import com.e444er.network.NetworkDataSource
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val movieFavoriteDao: MovieFavoriteDao,
    private val networkDataSource: NetworkDataSource,
) : MovieDetailRepository {

    override suspend fun getFavoriteMovie(id: Int): Movie? =
        movieFavoriteDao.getFavoriteMovie(id)?.toDomainModel()

    override suspend fun getDetails(moveID: Int): Result<MovieDetail> =
        networkDataSource.getDetails(moveID).map { it.mapToDomain() }
}