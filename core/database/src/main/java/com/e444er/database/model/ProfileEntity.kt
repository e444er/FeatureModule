package com.e444er.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val firstName: String = "",
    val lastName: String = ""
)
