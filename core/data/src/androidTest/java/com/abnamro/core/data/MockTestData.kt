package com.abnamro.core.data

import com.abnamro.core.data.database.model.RepoEntity

internal object MockTestData {
    fun givenRepoEntityList() = listOf(givenPublicRepoEntity(), givenPrivateRepoEntity())

    fun givenRepoEntity() = RepoEntity(
        id = 10,
        name = "repo",
        fullName = "fullRepo",
        description = "description",
        ownerAvatarUrl = "avatarUrl",
        visibility = "public",
        isPrivate = false,
        htmlUrl = "htmlUrl"
    )

    fun givenPublicRepoEntity() =
        givenRepoEntity().copy(id = 1, visibility = "public", isPrivate = false)

    fun givenPrivateRepoEntity() =
        givenRepoEntity().copy(id = 2, visibility = "private", isPrivate = true)

    fun givenInternalRepoEntity() =
        givenRepoEntity().copy(id = 3, visibility = "internal", isPrivate = true)
}