package com.abnamro.core.data.model

import com.abnamro.core.base.model.layermodel.DataModel
import com.abnamro.core.data.database.model.RepoEntity
import com.abnamro.core.domain.model.RepoBO
import com.abnamro.core.domain.model.getVisibilityType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDTO(
    val id: Long?,
    val name: String?,
    @SerialName("full_name")
    val fullName: String?,
    val description: String?,
    val owner: OwnerDTO?,
    val visibility: String?,
    val private: Boolean,
    @SerialName("html_url")
    val htmlUrl: String?
) : DataModel

fun RepoDTO.toDomain() = RepoBO(
    id = id,
    name = name,
    fullName = fullName,
    ownerAvatarUrl = owner?.avatarUrl,
    description = description,
    visibility = visibility?.getVisibilityType(),
    isPrivate = private,
    htmlUrl = htmlUrl
)

fun List<RepoDTO>.toDomain() = map { it.toDomain() }

fun RepoDTO.toEntity() = RepoEntity(
    id = id ?: 1,
    name = name.orEmpty(),
    fullName = fullName.orEmpty(),
    ownerAvatarUrl = owner?.avatarUrl.orEmpty(),
    description = description.orEmpty(),
    visibility = visibility.orEmpty(),
    isPrivate = private,
    htmlUrl = htmlUrl.orEmpty()
)

fun List<RepoDTO>.toEntity() = map { it.toEntity() }
