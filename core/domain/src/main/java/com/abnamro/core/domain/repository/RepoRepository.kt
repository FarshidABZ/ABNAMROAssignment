package com.abnamro.core.domain.repository

import com.abnamro.core.base.model.Result
import com.abnamro.core.domain.model.RepoBO
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    fun getRepos(pageSize: Int = 10): Flow<Result<List<RepoBO>>>
    suspend fun getRepoDetail(id: Long): Result<RepoBO>
}