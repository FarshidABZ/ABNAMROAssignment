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


class RefreshReposUsecaseTest {
    private val repository: RepoRepository = mockk()
    private val refreshReposUseCase = RefreshReposUsecase(repository)

    @Test
    fun `when refresh successful, returns success`() = runTest {
        val repos = givenRepoList()

        coEvery { repository.refreshRepos(10) } returns Result.Success(repos)

        val result = refreshReposUseCase(10)

        assertTrue(result is Result.Success)
        assertEquals(repos, (result as Result.Success).data)
    }

    @Test
    fun `when refresh fails, returns error`() = runTest {
        val exception = RuntimeException("Error during refresh")

        coEvery { repository.refreshRepos(10) } returns Result.Error(exception, null)

        val result = refreshReposUseCase(10)

        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
    }
}