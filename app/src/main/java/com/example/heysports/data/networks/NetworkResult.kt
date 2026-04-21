package com.example.heysports.data.networks

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Exception, val message: String) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
}