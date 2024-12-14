package com.abnamro.core.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abnamro.core.data.database.model.RepoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RepoDao {
    @Query("SELECT * FROM repos")
    fun getAllRepos(): Flow<List<RepoEntity>>

    @Query("SELECT * FROM repos WHERE id = :id")
    suspend fun getRepoById(id: Long): RepoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<RepoEntity>)

    @Query("DELETE FROM repos")
    suspend fun clearRepos()
}