package com.example.heysports.data.models.response

sealed class NetworkResult<out T> {
    data class Success<out T>(val value: T) : NetworkResult<T>()
    data class Error(val exception: Exception, val message: String) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
}

interface ApiRequest<T> {
    val request: suspend () -> NetworkResult<T>
    val onSuccess: (T) -> Unit
}

class AnyApiRequest<T>(
    override val request: suspend () -> NetworkResult<T>,
    override val onSuccess: (T) -> Unit
) : ApiRequest<T>

fun <T> apiRequestOf(
    request: suspend () -> NetworkResult<T>,
    onSuccess: (T) -> Unit
): AnyApiRequest<*> = AnyApiRequest(request, onSuccess)