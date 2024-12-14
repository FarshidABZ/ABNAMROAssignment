package com.abnamro.core.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abnamro.core.base.model.layermodel.DataModel

@Entity(tableName = "repos")
data class RepoEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val ownerAvatarUrl: String,
    val visibility: String,
    val isPrivate: Boolean,
    val htmlUrl: String
) : DataModel