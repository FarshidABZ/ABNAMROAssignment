package com.abnamro.core.data.network

import com.abnamro.core.base.model.NetworkErrorType
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal object NetworkUtils {
    fun getNetworkErrorMessage(e: Exception) = when (e) {
        is SocketTimeoutException -> NetworkErrorType.TIME_OUT_ERROR
        is UnknownHostException -> NetworkErrorType.UNKNOWN_HOST_ERROR
        is IOException -> NetworkErrorType.NETWORK_ERROR
        else -> NetworkErrorType.GENERAL_ERROR
    }

    fun getErrorMessage(code: Int) = when (code) {
        401 -> NetworkErrorType.UNAUTHORIZED_ERROR
        // Time out error
        408 -> NetworkErrorType.TIME_OUT_ERROR
        // Internal server error
        500 -> NetworkErrorType.SERVER_ERROR
        // Any other error executing the API
        else -> NetworkErrorType.GENERAL_ERROR
    }
}