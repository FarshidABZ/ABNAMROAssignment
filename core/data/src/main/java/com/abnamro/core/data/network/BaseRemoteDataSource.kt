package com.abnamro.core.data.network

import com.abnamro.core.base.model.Result
import com.abnamro.core.data.network.NetworkUtils.getErrorMessage
import com.abnamro.core.data.network.NetworkUtils.getNetworkErrorMessage
import retrofit2.Response

/**
 * Abstract class that provides a base implementation for remote data sources.
 * It includes methods to safely execute API calls and handle responses.
 */
abstract class BaseRemoteDataSource {

    /**
     * Executes a given API call in a safe manner, catching exceptions and returning a [Result] object.
     *
     * @param T the type of the response body.
     * @param call the suspend function that makes the API call and returns a [Response] object.
     * @return a [Result] object containing either the successful response body or an error.
     */
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>) =
        try {
            safeApiResult(call.invoke())
        } catch (e: Exception) {
            Result.Error(exception = e, errorType = getNetworkErrorMessage(e))
        }

    /**
     * Processes the API response and returns a [Result] object.
     *
     * @param T the type of the response body.
     * @param response the [Response] object returned by the API call.
     * @return a [Result] object containing either the successful response body or an error.
     */
    private fun <T> safeApiResult(response: Response<T>): Result<T> =
        if (response.isSuccessful && response.body() != null) Result.Success(response.body()!!)
        else Result.Error(null, errorType = getErrorMessage(response.code()))
}