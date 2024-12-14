package com.abnamro.core.domain.usecase

import com.abnamro.core.base.model.Result
import com.abnamro.core.domain.model.RepoBO
import com.abnamro.core.domain.repository.RepoRepository

class GetRepoDetailsUsecase(private val repository: RepoRepository) {
    suspend operator fun invoke(id: Int): Result<RepoBO> {
        return repository.getRepoDetail(id)
    }
}