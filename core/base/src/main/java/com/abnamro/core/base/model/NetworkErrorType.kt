package com.abnamro.core.base.model

enum class NetworkErrorType : ErrorType {
    UNAUTHORIZED_ERROR,
    TIME_OUT_ERROR,
    SERVER_ERROR,
    NETWORK_ERROR,
    UNKNOWN_HOST_ERROR,
    GENERAL_ERROR
}