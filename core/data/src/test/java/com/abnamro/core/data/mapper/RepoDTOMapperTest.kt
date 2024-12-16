package com.abnamro.core.data.mapper

import com.abnamro.core.data.MockTestData.givenInternalRepo
import com.abnamro.core.data.MockTestData.givenPrivateRepo
import com.abnamro.core.data.MockTestData.givenPublicRepo
import com.abnamro.core.data.model.toDomain
import com.abnamro.core.domain.model.RepoBO
import com.abnamro.core.domain.model.VisibilityState
import org.junit.Assert.assertEquals
import org.junit.Test


class RepoDTOMapperTest {
    @Test
    fun `given public repo, map correctly to RepoBO`() {
        // Given
        val repoDTO = givenPublicRepo()

        // When
        val repoBO = repoDTO.toDomain()

        // Then
        val expectedRepoBO = RepoBO(
            id = repoBO.id,
            name = "NewRepo",
            fullName = "fullRepo",
            description = "description",
            ownerAvatarUrl = "avatarUrl",
            ownerName = "ownerName",
            visibility = VisibilityState.PUBLIC,
            isPrivate = false,
            htmlUrl = "htmlUrl"
        )

        assertEquals(expectedRepoBO, repoBO)
    }

    @Test
    fun `given private repo, map correctly to RepoBO`() {
        // Given
        val repoDTO = givenPrivateRepo()

        // When
        val repoBO = repoDTO.toDomain()

        // Then
        val expectedRepoBO = RepoBO(
            id = repoBO.id,
            name = "NewRepo",
            fullName = "fullRepo",
            description = "description",
            ownerName = "ownerName",
            ownerAvatarUrl = "avatarUrl",
            visibility = VisibilityState.PRIVATE,
            isPrivate = true,
            htmlUrl = "htmlUrl"
        )

        assertEquals(expectedRepoBO, repoBO)
    }

    @Test
    fun `given internal repo, map correctly to RepoBO`() {
        // Given
        val repoDTO = givenInternalRepo()

        // When
        val repoBO = repoDTO.toDomain()

        // Then
        val expectedRepoBO = RepoBO(
            id = repoBO.id,
            name = "NewRepo",
            fullName = "fullRepo",
            description = "description",
            ownerName = "ownerName",
            ownerAvatarUrl = "avatarUrl",
            visibility = VisibilityState.INTERNAL,
            isPrivate = true,
            htmlUrl = "htmlUrl"
        )

        assertEquals(expectedRepoBO, repoBO)
    }
}