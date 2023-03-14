package com.e444er.movie.repository.movie

import androidx.paging.*
import com.e444er.common.di.IoDispatcher
import com.e444er.database.dao.MovieFavoriteDao
import com.e444er.database.dao.MovieRemoteKeysDao
import com.e444er.database.dao.MoviesDao
import com.e444er.database.utils.TransactionProvider
import com.e444er.movie.PAGE_SIZE
import com.e444er.movie.model.*
import com.e444er.network.NetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val moviesDao: MoviesDao,
    private val movieRemoteKeysDao: MovieRemoteKeysDao,
    private val movieFavoriteDao: MovieFavoriteDao,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) : MovieRepository {

    @Inject
    lateinit var transactionProvider: TransactionProvider

    override suspend fun getPopulars(page: Int): Result<PopularMovies> =
        networkDataSource.getPopularMovies(page).map { it.mapToPopularMovies() }

    override suspend fun getDetails(moveID: Int): Result<MovieDetail> =
        networkDataSource.getDetails(moveID).map { it.mapToDomain() }

    override fun favoriteMovies(): Flow<List<Movie>> =
        movieFavoriteDao.favoriteMovies().map { movies -> movies.map { it.toDomainModel() } }

    override suspend fun saveToFavorite(movie: Movie) {
        movieFavoriteDao.insert(movie.toDatabaseModel())
    }

    override suspend fun deleteFromFavorite(id: Int) {
        movieFavoriteDao.deleteMovie(id)
    }

    override suspend fun getFavoriteMovie(id: Int): Movie? =
        movieFavoriteDao.getFavoriteMovie(id)?.toDomainModel()


    @OptIn(ExperimentalPagingApi::class)
    override fun getPopularMovies(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = PAGE_SIZE,
            ),
            pagingSourceFactory = {
                moviesDao.getMoviesPagingSource()
            },
            remoteMediator = MoviesRemoteMediator(
                networkDataSource,
                moviesDao,
                movieRemoteKeysDao,
                transactionProvider
            )
        ).flow
            .map { pagingData ->
                pagingData.map(dispatcherIO.asExecutor()) { it.toDomainModel() }
            }
            .flowOn(dispatcherIO)
}