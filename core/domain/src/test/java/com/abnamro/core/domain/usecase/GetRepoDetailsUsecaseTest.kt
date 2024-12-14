package com.abnamro.core.domain.usecase

import com.abnamro.core.base.model.Result
import com.abnamro.core.domain.repository.RepoRepository
import com.abnamro.core.domain.usecase.MockTestData.givenRepoList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetRepoDetailsUsecaseTest {
    private val repository = mockk<RepoRepository>()
    private val getRepoDetailsUseCase = GetRepoDetailsUsecase(repository)

    @Test
    fun `when repository returns success, usecase returns success`() = runTest {
        // Given
        val repo = givenRepoList().first()

        coEvery { repository.getRepoDetail(any()) } returns Result.Success(repo)

        // When
        val result = getRepoDetailsUseCase(id = 1)

        // Then

        assertTrue(result is Result.Success)
        assertEquals(repo, (result as Result.Success).data)
    }

    @Test
    fun `when repository returns error, usecase returns error`() = runTest {
        // Given
        val exception = RuntimeException("Database error")
        coEvery { repository.getRepoDetail(any()) } returns Result.Error(exception, null)

        // When
        val result = getRepoDetailsUseCase(id = 1)

        // Then
        assertTrue(result is Result.Error)
    }
}