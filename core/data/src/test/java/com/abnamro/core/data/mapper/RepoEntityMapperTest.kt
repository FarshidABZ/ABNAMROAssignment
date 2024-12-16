package com.abnamro.core.data.mapper

import com.abnamro.core.data.MockTestData.givenInternalRepoEntity
import com.abnamro.core.data.MockTestData.givenPrivateRepoEntity
import com.abnamro.core.data.MockTestData.givenPublicRepoEntity
import com.abnamro.core.data.database.model.toDTO
import com.abnamro.core.data.model.OwnerDTO
import com.abnamro.core.data.model.RepoDTO
import com.abnamro.core.domain.model.VisibilityState
import org.junit.Assert.assertEquals
import org.junit.Test

class RepoEntityMapperTest {
    @Test
    fun `given public repo, map correctly to RepoBO`() {
        // Given
        val repoEntity = givenPublicRepoEntity()

        // When
        val repoDTO = repoEntity.toDTO()

        // Then
        val expectedRepoDTO = RepoDTO(
            id = repoDTO.id,
            name = "CachedRepo",
            fullName = "fullRepo",
            description = "description",
            owner = OwnerDTO("avatarUrl"),
            visibility = VisibilityState.PUBLIC.name.lowercase(),
            private = false,
            htmlUrl = "htmlUrl"
        )

        assertEquals(expectedRepoDTO, repoDTO)
    }

    @Test
    fun `given private repo, map correctly to RepoBO`() {
        // Given
        val repoEntity = givenPrivateRepoEntity()

        // When
        val repoDTO = repoEntity.toDTO()

        // Then
        val expectedRepoDTO = RepoDTO(
            id = repoDTO.id,
            name = "CachedRepo",
            fullName = "fullRepo",
            description = "description",
            owner = OwnerDTO("avatarUrl"),
            visibility = VisibilityState.PRIVATE.name.lowercase(),
            private = true,
            htmlUrl = "htmlUrl"
        )


        assertEquals(expectedRepoDTO, repoDTO)
    }

    @Test
    fun `given internal repo, map correctly to RepoBO`() {
        // Given
        val repoEntity = givenInternalRepoEntity()

        // When
        val repoDTO = repoEntity.toDTO()

        // Then
        val expectedRepoDTO = RepoDTO(
            id = repoDTO.id,
            name = "CachedRepo",
            fullName = "fullRepo",
            description = "description",
            owner = OwnerDTO("avatarUrl"),
            visibility = VisibilityState.INTERNAL.name.lowercase(),
            private = true,
            htmlUrl = "htmlUrl"
        )


        assertEquals(expectedRepoDTO, repoDTO)
    }
}