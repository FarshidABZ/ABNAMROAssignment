package com.abnamro.core.domain.model

import com.abnamro.core.base.model.layermodel.DomainModel

data class RepoBO(
    val id: Long?,
    val name: String?,
    val fullName: String?,
    val description: String?,
    val ownerAvatarUrl: String?,
    val ownerName: String?,
    val visibility: VisibilityState?,
    val isPrivate: Boolean?,
    val htmlUrl: String?
) : DomainModel