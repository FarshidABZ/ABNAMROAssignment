package com.abnamro.core.domain.usecase

import com.abnamro.core.base.model.Result
import com.abnamro.core.domain.model.RepoBO
import com.abnamro.core.domain.repository.RepoRepository

class RefreshReposUsecase(private val repository: RepoRepository) {
    suspend operator fun invoke(perPage: Int = 10): Result<List<RepoBO>> {
        return repository.refreshRepos(perPage)
    }
}