package com.abnamro.core.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.abnamro.core.data.database.model.RemoteKeyEntity


@Dao
interface RemoteKeyDao {
    @Query("SELECT * FROM remote_keys")
    suspend fun getLastKey(): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRepos()

    @Upsert
    suspend fun upsert(remoteKey: RemoteKeyEntity)
}