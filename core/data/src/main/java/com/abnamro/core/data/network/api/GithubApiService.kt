package com.abnamro.core.data.network.api

import com.abnamro.core.data.model.RepoDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    @GET("/users/abnamrocoesd/repos")
    suspend fun getRepos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<RepoDTO>>
}