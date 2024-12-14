package com.abnamro.core.data.repository

import com.abnamro.core.base.model.Result
import com.abnamro.core.data.mapper.RepoMapper
import com.abnamro.core.data.network.BaseRemoteDataSource
import com.abnamro.core.data.network.api.GithubApiService
import com.abnamro.core.domain.model.RepoBO
import com.abnamro.core.domain.repository.RepoRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val remoteDataSource: GithubApiService,
    private val repoMapper: RepoMapper
) : RepoRepository, BaseRemoteDataSource() {
    override fun getRepos(page: Int, perPage: Int) = flow {
        val networkResult = safeApiCall { remoteDataSource.getRepos(page, perPage) }
        when (networkResult) {
            is Result.Success -> {
                val repos = networkResult.data.map { repoMapper.map(it) }
                emit(Result.Success(repos))
            }

            is Result.Error -> emit(Result.Error(networkResult.exception, networkResult.errorType))
        }
    }

    override suspend fun getRepoDetail(id: Int): Result<RepoBO> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshRepos(perPage: Int): Result<List<RepoBO>> {
        TODO("Not yet implemented")
    }
}