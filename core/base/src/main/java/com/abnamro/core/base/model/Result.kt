package com.abnamro.core.base.model

sealed interface Result<out T> {
    data class Success<out T>(
        val data: T,
        val isLastPage: Boolean? = null
    ) : Result<T>

    data class Error(val exception: Exception?, val errorType: ErrorType?) : Result<Nothing>
}