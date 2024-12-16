package com.abnamro.core.data.repository

import app.cash.turbine.test
import com.abnamro.core.base.model.Result
import com.abnamro.core.data.MockTestData.givenRepoEntityList
import com.abnamro.core.data.MockTestData.givenRepoList
import com.abnamro.core.data.MockTestData.givenUpdatedRepoEntity
import com.abnamro.core.data.database.RepoDao
import com.abnamro.core.data.network.api.GithubApiService
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response


@OptIn(ExperimentalCoroutinesApi::class)
class RepoRepositoryImplTest {
    private val remoteDataSource: GithubApiService = mockk()
    private val localDataSource: RepoDao = mockk()
    private lateinit var repository: RepoRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = RepoRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given cached data and successful network call, repository emits cached then updated data`() =
        runTest {
            coEvery { localDataSource.getAllRepos() } returnsMany listOf(
                flowOf(givenRepoEntityList()),
                flowOf(givenUpdatedRepoEntity())
            )

            coEvery { localDataSource.insertRepos(any()) } just Runs

            coEvery { remoteDataSource.getRepos(page = 1, perPage = 10) } returns
                    Response.success(givenRepoList())

            val flow = repository.getRepos(page = 1, perPage = 10)

            flow.test {
                val firstEmission = awaitItem()
                assertTrue(firstEmission is Result.Success)
                assertEquals("CachedRepo", (firstEmission as Result.Success).data.first().name)

                // Second emission: updated data after network call
                val secondEmission = awaitItem()
                assertTrue(secondEmission is Result.Success)
                assertEquals("UpdatedRepo", (secondEmission as Result.Success).data.first().name)

                awaitComplete()
            }
        }


    @Test
    fun `given cached data and network error, emits cached data then error`() = runTest {
        coEvery { localDataSource.getAllRepos() } returns flowOf(givenRepoEntityList())

        coEvery { remoteDataSource.getRepos(page = 1, perPage = 10) } throws
                RuntimeException("Network error")

        val flow = repository.getRepos(page = 1, perPage = 10)

        flow.test {
            val firstEmission = awaitItem()
            assertTrue(firstEmission is Result.Success)
            assertEquals("CachedRepo", (firstEmission as Result.Success).data.first().name)

            val secondEmission = awaitItem()
            assertTrue(secondEmission is Result.Error)
            val errorResult = secondEmission as Result.Error
            assertEquals("Network error", errorResult.exception?.message)

            awaitComplete()
        }
    }

    @Test
    fun `given no cached data and successful network call, emits empty then updated data`() =
        runTest {
            coEvery { localDataSource.getAllRepos() } returnsMany listOf(
                flowOf(emptyList()),
                flowOf(givenUpdatedRepoEntity())
            )

            coEvery { localDataSource.insertRepos(any()) } just Runs

            coEvery { remoteDataSource.getRepos(page = 1, perPage = 10) } returns
                    Response.success(givenRepoList())

            val flow = repository.getRepos(page = 1, perPage = 10)

            flow.test {
                val firstEmission = awaitItem()
                assertTrue(firstEmission is Result.Success)
                assertTrue((firstEmission as Result.Success).data.isEmpty())

                val secondEmission = awaitItem()
                assertTrue(secondEmission is Result.Success)
                assertEquals("UpdatedRepo", (secondEmission as Result.Success).data.first().name)

                awaitComplete()
            }
        }
}