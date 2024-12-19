package com.abnamro.core.domain.usecase

import app.cash.turbine.test
import com.abnamro.core.base.model.NetworkErrorType
import com.abnamro.core.base.model.Result
import com.abnamro.core.domain.repository.RepoRepository
import com.abnamro.core.domain.usecase.MockTestData.givenRepoList
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetReposUsecaseTest {
    private val repository = mockk<RepoRepository>()
    private val getReposUsecase = GetReposUsecase(repository)

    @Test
    fun `when repository returns success, usecase emits success`() = runTest {
        // Given
        val repoList = givenRepoList()

        coEvery { repository.getRepos(any()) } returns
                flowOf(Result.Success(repoList))

        // When
        val flow = getReposUsecase(1)

        // Then
        verify(exactly = 1) {
            repository.getRepos(any())
        }

        flow.test {
            val emission = awaitItem()
            assertTrue(emission is Result.Success)
            assertEquals(repoList, (emission as Result.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `when repository returns error, usecase emits error`() = runTest {
        // Given
        val exception = RuntimeException("Network error")
        coEvery { repository.getRepos(any()) } returns flowOf(
            Result.Error(
                exception,
                NetworkErrorType.NETWORK_ERROR
            )
        )

        // When
        val flow = getReposUsecase(10)

        // Then
        flow.test {
            val emission = awaitItem()
            assertTrue(emission is Result.Error)
            assertEquals(exception, (emission as Result.Error).exception)
            awaitComplete()
        }
    }
}