package com.e444er.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.e444er.database.dao.MovieFavoriteDao
import com.e444er.database.dao.MovieRemoteKeysDao
import com.e444er.database.dao.MoviesDao
import com.e444er.database.dao.ProfileDao
import com.e444er.database.model.MovieEntity
import com.e444er.database.model.MovieFavoriteEntity
import com.e444er.database.model.MovieRemoteKeys
import com.e444er.database.model.ProfileEntity

@Database(
    entities = [
        MovieEntity::class, MovieRemoteKeys::class,
        MovieFavoriteEntity::class, ProfileEntity::class],
    version = 1
)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeysDao
    abstract fun movieFavoriteDao(): MovieFavoriteDao
    abstract fun profileDao(): ProfileDao
}