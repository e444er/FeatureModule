package com.e444er.movie.di

import com.e444er.movie.repository.movie.MovieRepository
import com.e444er.movie.repository.movie.MovieRepositoryImpl
import com.e444er.movie.repository.movieDetail.MovieDetailRepository
import com.e444er.movie.repository.movieDetail.MovieDetailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataMovieModule {

    @Binds
    fun movieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    fun movieDetailRepository(
        movieDetailRepositoryImpl: MovieDetailRepositoryImpl
    ): MovieDetailRepository
}