package com.abnamro.core.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey val id: Int = 1,
    val nextPage: Int,
    val previousPage: Int,
    val isLastPage: Boolean = false
)