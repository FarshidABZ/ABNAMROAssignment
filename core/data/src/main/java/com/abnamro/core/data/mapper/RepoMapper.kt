package com.abnamro.core.data.mapper

import com.abnamro.core.base.model.DataMapper
import com.abnamro.core.data.model.RepoDTO
import com.abnamro.core.domain.model.RepoBO
import com.abnamro.core.domain.model.VisibilityState
import javax.inject.Inject

class RepoMapper @Inject constructor() : DataMapper<RepoDTO, RepoBO> {
    override fun map(input: RepoDTO): RepoBO {
        return RepoBO(
            id = input.id,
            name = input.name,
            fullName = input.fullName,
            ownerAvatarUrl = input.owner.avatarUrl,
            description = input.description,
            visibility = when (input.visibility) {
                "public" -> VisibilityState.PUBLIC
                "private" -> VisibilityState.PRIVATE
                else -> VisibilityState.INTERNAL
            },
            isPrivate = input.private,
            htmlUrl = input.htmlUrl
        )
    }
}