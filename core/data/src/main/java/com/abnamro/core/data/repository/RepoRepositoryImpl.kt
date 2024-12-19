package com.abnamro.core.data.repository

import androidx.room.withTransaction
import com.abnamro.core.base.model.Result
import com.abnamro.core.data.database.AppDatabase
import com.abnamro.core.data.database.model.RemoteKeyEntity
import com.abnamro.core.data.database.model.toDomain
import com.abnamro.core.data.model.toEntity
import com.abnamro.core.data.network.BaseRemoteDataSource
import com.abnamro.core.data.network.api.GithubApiService
import com.abnamro.core.domain.model.RepoBO
import com.abnamro.core.domain.repository.RepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val remoteDataSource: GithubApiService,
    private val localDataSource: AppDatabase,
) : RepoRepository, BaseRemoteDataSource() {
    override fun getRepos(pageSize: Int): Flow<Result<List<RepoBO>>> = flow {
        emit(Result.Success(loadFromDb()))

        val remoteKey = localDataSource.remoteKeyDao.getLastKey()
        var isLastPage = remoteKey?.isLastPage ?: false
        val page = remoteKey?.nextPage ?: 1

        if (isLastPage) {
            emit(Result.Success(emptyList(), true))
            return@flow
        }

        when (val networkResult = safeApiCall { remoteDataSource.getRepos(page) }) {
            is Result.Success -> {
                val data = networkResult.data
                localDataSource.withTransaction {
                    if (data.isEmpty()) {
                        localDataSource.remoteKeyDao.upsert(
                            RemoteKeyEntity(
                                isLastPage = true,
                                nextPage = page,
                                previousPage = page - 1
                            )
                        )
                    } else {
                        localDataSource.repoDao.upsertAll(data.toEntity())
                        isLastPage = data.size < pageSize
                        localDataSource.remoteKeyDao.upsert(
                            RemoteKeyEntity(
                                isLastPage = isLastPage,
                                nextPage = if (!isLastPage) page + 1 else page,
                                previousPage = page
                            )
                        )
                    }
                }
                emit(Result.Success(loadFromDb(), isLastPage))
            }

            is Result.Error -> {
                val cachedData = loadFromDb()
                if (cachedData.isNotEmpty()) {
                    emit(Result.Success(cachedData))
                }
                emit(Result.Error(networkResult.exception, networkResult.errorType))
            }
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Loads the current data from the database and maps it to a domain [Result].
     * If there's no data, returns Success with an empty list.
     */
    private suspend fun loadFromDb() =
        localDataSource.repoDao.getAllRepos()?.map { it.toDomain() }.orEmpty()

    override suspend fun getRepoDetail(id: Int): Result<RepoBO> {
        TODO("Not yet implemented")
    }
}