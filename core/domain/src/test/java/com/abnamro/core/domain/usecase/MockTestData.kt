package com.abnamro.core.domain.usecase

import com.abnamro.core.domain.model.RepoBO
import com.abnamro.core.domain.model.VisibilityState

object MockTestData {
    fun givenRepoList() = listOf(
        RepoBO(
            id = 1,
            name = "Repo1",
            fullName = "abnamrocoesd/Repo1",
            description = "Sample repo",
            ownerAvatarUrl = "http://avatar.url",
            visibility = VisibilityState.PUBLIC,
            isPrivate = false,
            htmlUrl = "http://html.url"
        )
    )
}