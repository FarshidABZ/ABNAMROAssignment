package com.abnamro.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abnamro.core.data.database.model.RemoteKeyEntity
import com.abnamro.core.data.database.model.RepoEntity

@Database(entities = [RepoEntity::class, RemoteKeyEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val repoDao: RepoDao
    abstract val remoteKeyDao: RemoteKeyDao
}