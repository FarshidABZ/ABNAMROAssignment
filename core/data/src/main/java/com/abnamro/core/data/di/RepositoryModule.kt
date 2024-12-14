package com.abnamro.core.data.di

import com.abnamro.core.data.mapper.RepoMapper
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
        repoMapper: RepoMapper,
    ): RepoRepository =
        RepoRepositoryImpl(remoteDataSource, repoMapper)
}