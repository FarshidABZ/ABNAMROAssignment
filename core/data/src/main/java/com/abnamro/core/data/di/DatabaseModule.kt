package com.abnamro.core.data.di

import android.content.Context
import androidx.room.Room
import com.abnamro.core.data.database.AppDatabase
import com.abnamro.core.data.database.RepoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    fun provideDao(database: AppDatabase): RepoDao = database.dao()

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "AppDatabase",
        ).build()
}