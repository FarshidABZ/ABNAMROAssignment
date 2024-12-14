package com.abnamro.core.domain.model

enum class VisibilityState {
    PUBLIC,
    PRIVATE,
    INTERNAL
}

fun String.getVisibilityType(): VisibilityState {
    return when (this) {
        "public" -> VisibilityState.PUBLIC
        "private" -> VisibilityState.PRIVATE
        "internal" -> VisibilityState.INTERNAL
        else -> VisibilityState.PUBLIC
    }
}