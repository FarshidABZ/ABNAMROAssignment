package com.abnamro.core.data.mapper

import com.abnamro.core.base.model.DataMapper
import com.abnamro.core.data.database.model.RepoEntity
import com.abnamro.core.data.model.OwnerDTO
import com.abnamro.core.data.model.RepoDTO
import com.abnamro.core.domain.model.RepoBO
import com.abnamro.core.domain.model.getVisibilityType
import javax.inject.Inject

class RepoMapper @Inject constructor() : DataMapper<RepoDTO, RepoBO> {
    override fun map(input: RepoDTO): RepoBO {
        return RepoBO(
            id = input.id,
            name = input.name,
            fullName = input.fullName,
            ownerAvatarUrl = input.owner.avatarUrl,
            description = input.description,
            visibility = input.visibility.getVisibilityType(),
            isPrivate = input.private,
            htmlUrl = input.htmlUrl
        )
    }
}

class RepoEntityMapper @Inject constructor() : DataMapper<RepoEntity, RepoDTO> {
    override fun map(input: RepoEntity): RepoDTO {
        return RepoDTO(
            id = input.id,
            name = input.name,
            fullName = input.fullName,
            owner = OwnerDTO(input.ownerAvatarUrl),
            description = input.description,
            visibility = input.visibility,
            private = input.isPrivate,
            htmlUrl = input.htmlUrl
        )
    }
}