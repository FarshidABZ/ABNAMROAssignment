package com.abnamro.feature.repolist.model

import com.abnamro.core.domain.model.RepoBO
import com.abnamro.core.domain.model.VisibilityState

class RepoUiModel(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String,
    val ownerAvatarUrl: String,
    val ownerName: String,
    val visibility: VisibilityState,
    val isPrivate: Boolean,
    val htmlUrl: String?
)

internal fun RepoBO.toUIModel(): RepoUiModel {
    return RepoUiModel(
        id = id ?: 0L,
        name = name.orEmpty(),
        fullName = fullName.orEmpty(),
        description = description.orEmpty(),
        ownerAvatarUrl = ownerAvatarUrl.orEmpty(),
        ownerName = ownerName.orEmpty(),
        visibility = visibility ?: VisibilityState.PUBLIC,
        isPrivate = isPrivate ?: false,
        htmlUrl = htmlUrl
    )
}