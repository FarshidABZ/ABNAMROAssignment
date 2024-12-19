package com.abnamro.core.data.repository

import androidx.room.withTransaction
import app.cash.turbine.test
import com.abnamro.core.base.model.Result
import com.abnamro.core.data.MockTestData.givenRepoEntityList
import com.abnamro.core.data.MockTestData.givenRepoList
import com.abnamro.core.data.database.AppDatabase
import com.abnamro.core.data.database.model.RemoteKeyEntity
import com.abnamro.core.data.network.api.GithubApiService
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    private val localDataSource: AppDatabase = mockk()
    private lateinit var repository: RepoRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(
            "androidx.room.RoomDatabaseKt"
        )

        val transactionLambda = slot<suspend () -> Any>()
        coEvery { localDataSource.withTransaction(capture(transactionLambda)) } coAnswers {
            transactionLambda.captured.invoke()
        }

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
            mockLocalDataSource()

            coEvery { remoteDataSource.getRepos(any(), any()) } returns
                    Response.success(givenRepoList())

            val flow = repository.getRepos()

            flow.test {
                val firstEmission = awaitItem()
                assertTrue(firstEmission is Result.Success)
                assertEquals("CachedRepo", (firstEmission as Result.Success).data.first().name)

                // Second emission: updated data after network call
                val secondEmission = awaitItem()
                assertTrue(secondEmission is Result.Success)
                assertEquals("CachedRepo", (secondEmission as Result.Success).data.first().name)

                awaitComplete()
            }
        }

    @Test
    fun `given cached data and network error, emits cached data then error`() = runTest {
        mockLocalDataSource()

        coEvery { remoteDataSource.getRepos(any(), any()) } throws
                RuntimeException("Network error")

        val flow = repository.getRepos()

        flow.test {
            // First emission should be the cached data
            val firstEmission = awaitItem()
            assertTrue(firstEmission is Result.Success)
            val firstData = (firstEmission as Result.Success).data
            assertEquals(2, firstData.size)
            assertEquals("CachedRepo", firstData.first().name)

            // Second emission should be the cached data again (from the error block)
            val secondEmission = awaitItem()
            assertTrue(secondEmission is Result.Success)
            val secondData = (secondEmission as Result.Success).data
            assertEquals(2, secondData.size)
            assertEquals("CachedRepo", secondData.first().name)

            // Third emission should be the error
            val thirdEmission = awaitItem()
            assertTrue(thirdEmission is Result.Error)
            val errorResult = thirdEmission as Result.Error
            assertEquals("Network error", errorResult.exception?.message)

            // Ensure no more emissions
            awaitComplete()
        }
    }

    @Test
    fun `given no cached data and successful network call, emits empty then updated data`() =
        runTest {
            mockLocalDataSource(true)

            coEvery { remoteDataSource.getRepos(any(), any()) } returns
                    Response.success(givenRepoList())

            val flow = repository.getRepos()

            flow.test {
                val firstEmission = awaitItem()
                assertTrue(firstEmission is Result.Success)
                assertTrue((firstEmission as Result.Success).data.isEmpty())

                cancelAndIgnoreRemainingEvents()
            }
        }

    private fun mockLocalDataSource(noCached: Boolean = false) {
        if (noCached) {
            coEvery { localDataSource.repoDao.getAllRepos() } returns emptyList()
        } else {
            coEvery { localDataSource.repoDao.getAllRepos() } returnsMany listOf(
                givenRepoEntityList()
            )
        }

        coEvery { localDataSource.remoteKeyDao.getLastKey() } returns RemoteKeyEntity(
            previousPage = 1,
            nextPage = 2
        )
        coEvery { localDataSource.remoteKeyDao.clearRepos() } just Runs
        coEvery { localDataSource.remoteKeyDao.upsert(any()) } just Runs

        coEvery { localDataSource.repoDao.upsertAll(any()) } just Runs
    }
}