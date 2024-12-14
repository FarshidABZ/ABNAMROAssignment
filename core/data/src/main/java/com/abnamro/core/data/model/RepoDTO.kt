package com.abnamro.core.data.model

import com.abnamro.core.base.model.layermodel.DataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDTO(
    val id: Long?,
    val name: String?,
    @SerialName("full_name")
    val fullName: String?,
    val description: String?,
    val owner: OwnerDTO,
    val visibility: String,
    val private: Boolean,
    @SerialName("html_url")
    val htmlUrl: String
) : DataModel
