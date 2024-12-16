package com.abnamro.core.data.model

import com.abnamro.core.base.model.layermodel.DataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerDTO(
    @SerialName("avatar_url")
    val avatarUrl: String?,
    @SerialName("login")
    val ownerName: String?
) : DataModel
