package com.abnamro.core.data

import com.abnamro.core.data.database.model.RepoEntity
import com.abnamro.core.data.model.OwnerDTO
import com.abnamro.core.data.model.RepoDTO

internal object MockTestData {
    fun givenRepoList() = listOf(givenPublicRepo(), givenPrivateRepo())
    fun givenRepoEntityList() = listOf(givenPublicRepoEntity(), givenPrivateRepoEntity())
    fun givenUpdatedRepoEntity() = listOf(updatedRepoEntity())

    fun updatedRepoEntity() =
        RepoEntity(
            id = 1,
            name = "UpdatedRepo",
            fullName = "UpdatedFullRepo",
            description = "description",
            ownerAvatarUrl = "avatarUrl",
            ownerName = "ownerName",
            visibility = "public",
            isPrivate = false,
            htmlUrl = "htmlUrl"
        )

    fun givenRepo() = RepoDTO(
        id = 10,
        name = "NewRepo",
        fullName = "fullRepo",
        description = "description",
        owner = OwnerDTO(avatarUrl = "avatarUrl", ownerName = "ownerName"),
        visibility = "public",
        private = false,
        htmlUrl = "htmlUrl"
    )

    fun givenRepoEntity() = RepoEntity(
        id = 10,
        name = "CachedRepo",
        fullName = "fullRepo",
        description = "description",
        ownerAvatarUrl = "avatarUrl",
        ownerName = "ownerName",
        visibility = "public",
        isPrivate = false,
        htmlUrl = "htmlUrl"
    )

    fun givenPublicRepo() =
        givenRepo().copy(id = 1, visibility = "public", private = false)

    fun givenPrivateRepo() =
        givenRepo().copy(id = 2, visibility = "private", private = true)

    fun givenInternalRepo() =
        givenRepo().copy(id = 3, visibility = "internal", private = true)

    fun givenPublicRepoEntity() =
        givenRepoEntity().copy(id = 1, visibility = "public", isPrivate = false)

    fun givenPrivateRepoEntity() =
        givenRepoEntity().copy(id = 2, visibility = "private", isPrivate = true)

    fun givenInternalRepoEntity() =
        givenRepoEntity().copy(id = 3, visibility = "internal", isPrivate = true)
}