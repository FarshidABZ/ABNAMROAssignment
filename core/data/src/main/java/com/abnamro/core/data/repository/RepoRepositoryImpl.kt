package com.abnamro.core.data.repository

import com.abnamro.core.base.model.Result
import com.abnamro.core.data.database.RepoDao
import com.abnamro.core.data.database.model.toDomain
import com.abnamro.core.data.model.toEntity
import com.abnamro.core.data.network.BaseRemoteDataSource
import com.abnamro.core.data.network.api.GithubApiService
import com.abnamro.core.domain.model.RepoBO
import com.abnamro.core.domain.repository.RepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val remoteDataSource: GithubApiService,
    private val localDataSource: RepoDao,
) : RepoRepository, BaseRemoteDataSource() {
    override fun getRepos(page: Int, perPage: Int): Flow<Result<List<RepoBO>>> = flow {
        emit(loadFromDb())
        when (val networkResult = safeApiCall { remoteDataSource.getRepos(page, perPage) }) {
            is Result.Success -> {
                localDataSource.insertRepos(networkResult.data.toEntity())
                emit(loadFromDb())
            }
            is Result.Error -> {
                emit(Result.Error(networkResult.exception, networkResult.errorType))
            }
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Loads the current data from the database and maps it to a domain [Result].
     * If there's no data, returns Success with an empty list.
     */
    private suspend fun loadFromDb(): Result<List<RepoBO>> {
        return localDataSource.getAllRepos()
            .map { entities ->
                Result.Success(entities.toDomain())
            }
            .firstOrNull() ?: Result.Success(emptyList())
    }

    override suspend fun getRepoDetail(id: Int): Result<RepoBO> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshRepos(perPage: Int): Result<List<RepoBO>> {
        TODO("Not yet implemented")
    }
}