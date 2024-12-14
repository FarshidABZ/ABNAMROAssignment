package com.abnamro.core.data

import com.abnamro.core.data.model.OwnerDTO
import com.abnamro.core.data.model.RepoDTO
import kotlin.random.Random

object MockTestData {
    fun givenRepoList() = listOf(givenPublicRepo(), givenPrivateRepo())

    fun givenRepo() = RepoDTO(
        id = Random.nextLong(),
        name = "repo",
        fullName = "fullRepo",
        description = "description",
        owner = OwnerDTO(avatarUrl = "avatarUrl"),
        visibility = "public",
        private = false,
        htmlUrl = "htmlUrl"
    )

    fun givenPublicRepo() =
        givenRepo().copy(id = Random.nextLong(), visibility = "public", private = false)

    fun givenPrivateRepo() =
        givenRepo().copy(id = Random.nextLong(), visibility = "private", private = true)

    fun givenInternalRepo() =
        givenRepo().copy(id = Random.nextLong(), visibility = "internal", private = true)
}