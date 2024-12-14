package com.abnamro.core.data.mapper

import com.abnamro.core.data.MockTestData.givenInternalRepoEntity
import com.abnamro.core.data.MockTestData.givenPrivateRepoEntity
import com.abnamro.core.data.MockTestData.givenPublicRepoEntity
import com.abnamro.core.data.model.OwnerDTO
import com.abnamro.core.data.model.RepoDTO
import com.abnamro.core.domain.model.VisibilityState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RepoEntityMapperTest {

    private lateinit var repoEntityMapper: RepoEntityMapper

    @Before
    fun setUp() {
        repoEntityMapper = RepoEntityMapper()
    }

    @Test
    fun `given public repo, map correctly to RepoBO`() {
        // Given
        val repoEntity = givenPublicRepoEntity()

        // When
        val repoBO = repoEntityMapper.map(repoEntity)

        // Then
        val expectedRepoBO = RepoDTO(
            id = repoBO.id,
            name = "repo",
            fullName = "fullRepo",
            description = "description",
            owner = OwnerDTO("avatarUrl"),
            visibility = VisibilityState.PUBLIC.name.lowercase(),
            private = false,
            htmlUrl = "htmlUrl"
        )

        assertEquals(expectedRepoBO, repoBO)
    }

    @Test
    fun `given private repo, map correctly to RepoBO`() {
        // Given
        val repoEntity = givenPrivateRepoEntity()

        // When
        val repoBO = repoEntityMapper.map(repoEntity)

        // Then
        val expectedRepoBO = RepoDTO(
            id = repoBO.id,
            name = "repo",
            fullName = "fullRepo",
            description = "description",
            owner = OwnerDTO("avatarUrl"),
            visibility = VisibilityState.PRIVATE.name.lowercase(),
            private = true,
            htmlUrl = "htmlUrl"
        )


        assertEquals(expectedRepoBO, repoBO)
    }

    @Test
    fun `given internal repo, map correctly to RepoBO`() {
        // Given
        val repoEntity = givenInternalRepoEntity()

        // When
        val repoBO = repoEntityMapper.map(repoEntity)

        // Then
        val expectedRepoBO = RepoDTO(
            id = repoBO.id,
            name = "repo",
            fullName = "fullRepo",
            description = "description",
            owner = OwnerDTO("avatarUrl"),
            visibility = VisibilityState.INTERNAL.name.lowercase(),
            private = true,
            htmlUrl = "htmlUrl"
        )


        assertEquals(expectedRepoBO, repoBO)
    }
}