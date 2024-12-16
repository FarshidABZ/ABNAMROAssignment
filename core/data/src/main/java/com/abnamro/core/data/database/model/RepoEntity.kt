package com.abnamro.core.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abnamro.core.base.model.layermodel.DataModel
import com.abnamro.core.data.model.OwnerDTO
import com.abnamro.core.data.model.RepoDTO
import com.abnamro.core.domain.model.RepoBO
import com.abnamro.core.domain.model.getVisibilityType

@Entity(tableName = "repos")
data class RepoEntity(
    @PrimaryKey val id: Long,
    val name: String?,
    val fullName: String?,
    val description: String?,
    val ownerAvatarUrl: String?,
    val visibility: String?,
    val isPrivate: Boolean,
    val htmlUrl: String?
) : DataModel

fun RepoEntity.toDTO() = RepoDTO(
    id = id,
    name = name,
    fullName = fullName,
    owner = OwnerDTO(ownerAvatarUrl),
    description = description,
    visibility = visibility,
    private = isPrivate,
    htmlUrl = htmlUrl
)

fun List<RepoEntity>.toDTO() = map { it.toDTO() }

fun RepoEntity.toDomain() = RepoBO(
    id = id,
    name = name,
    fullName = fullName,
    ownerAvatarUrl = ownerAvatarUrl,
    description = description,
    visibility = visibility.getVisibilityType(),
    isPrivate = isPrivate,
    htmlUrl = htmlUrl
)

fun List<RepoEntity>.toDomain() = map { it.toDomain() }