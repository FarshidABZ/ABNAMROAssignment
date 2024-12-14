package com.abnamro.core.domain.model

import com.abnamro.core.base.model.DomainModel

data class RepoBO(
    val id: Int,
    val name: String,
    val fullName: String,
    val description: String?,
    val ownerAvatarUrl: String,
    val visibility: VisibilityState,
    val isPrivate: Boolean,
    val htmlUrl: String
) : DomainModel