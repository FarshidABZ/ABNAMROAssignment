package com.abnamro.core.base.model

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception?, val errorType: ErrorType?) : Result<Nothing>()
}