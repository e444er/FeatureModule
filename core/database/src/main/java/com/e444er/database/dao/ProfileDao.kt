package com.e444er.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.e444er.database.model.ProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profileEntity: ProfileEntity?)

    @Query("Select * From profile")
    suspend fun getProfile(): ProfileEntity?

    @Query("Select * From profile")
    fun profile(): Flow<ProfileEntity?>
}