package com.example.heysports.data.models.response

sealed class NetworkResult<out T> {
    data class Success<out T>(val value: T) : NetworkResult<T>()
    data class Error(val exception: Exception, val message: String) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
}

data class ApiRequest<T>(
    val request: suspend () -> NetworkResult<T>,
    val onSuccess: suspend (T) -> Unit
)