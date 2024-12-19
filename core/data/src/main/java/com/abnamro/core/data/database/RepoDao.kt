package com.abnamro.core.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.abnamro.core.data.database.model.RepoEntity


@Dao
interface RepoDao {
    @Query("SELECT * FROM repos ORDER BY LOWER(name) ASC")
    suspend fun getAllRepos(): List<RepoEntity>?

    @Query("SELECT * FROM repos WHERE id = :id")
    suspend fun getRepoById(id: Long): RepoEntity

    @Upsert
    suspend fun upsertAll(repos: List<RepoEntity>)

    @Query("DELETE FROM repos")
    suspend fun clearRepos()
}