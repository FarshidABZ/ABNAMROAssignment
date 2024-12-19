package com.abnamro.core.data.di

import com.abnamro.core.data.database.AppDatabase
import com.abnamro.core.data.network.api.GithubApiService
import com.abnamro.core.data.repository.RepoRepositoryImpl
import com.abnamro.core.domain.repository.RepoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: GithubApiService,
        localDataSource: AppDatabase,
    ): RepoRepository =
        RepoRepositoryImpl(remoteDataSource, localDataSource)
}