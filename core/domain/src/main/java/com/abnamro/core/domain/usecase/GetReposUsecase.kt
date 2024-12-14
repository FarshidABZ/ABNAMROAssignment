package com.abnamro.core.domain.usecase

import com.abnamro.core.base.model.Result
import com.abnamro.core.domain.model.RepoBO
import com.abnamro.core.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReposUsecase @Inject constructor(private val repository: RepoRepository) {
    operator fun invoke(page: Int, perPage: Int): Flow<Result<List<RepoBO>>> {
        return repository.getRepos(page, perPage)
    }
}