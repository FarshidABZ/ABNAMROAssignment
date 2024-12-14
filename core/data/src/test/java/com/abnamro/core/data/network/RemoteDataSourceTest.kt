package com.abnamro.core.data.network

import com.abnamro.core.data.MockTestData
import com.abnamro.core.data.network.api.GithubApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

class RemoteDataSourceTest : BaseNetworkTest() {
    private lateinit var githubApiService: GithubApiService

    @Before
    override fun setup() {
        super.setup()
        githubApiService = getApiService(GithubApiService::class.java)
    }

    @Test
    fun `getRepos, return list of RepoDTO`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(TestUtils.getJson("json/validResponse.json"))

        mockWebServer.enqueue(mockResponse)

        val response = githubApiService.getRepos(page = 1, perPage = 10)

        val expectedRepos = listOf(MockTestData.givenPublicRepo().copy(id = 1))

        assertTrue(response.isSuccessful)
        assertEquals(expectedRepos, response.body())
    }

    @Test
    fun `give page id getItems returns success empty list`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("[]")

        mockWebServer.enqueue(mockResponse)

        val response = githubApiService.getRepos(page = 1, perPage = 10)

        assertTrue(response.isSuccessful)
        assertEquals(0, response.body()?.size)
    }

    @Test
    fun `getItems returns unauthorized error`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)

        mockWebServer.enqueue(mockResponse)

        val response = githubApiService.getRepos(page = 1, perPage = 10)

        assertTrue(response.isSuccessful.not())
    }
}