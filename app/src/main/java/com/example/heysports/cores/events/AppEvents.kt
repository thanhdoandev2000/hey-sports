package com.example.heysports.cores.events

sealed class AppEvents {
    data class ShowGlobalError(val message: String) : AppEvents()
    data class ShowToast(val message: String) : AppEvents()
    data class ShowSnackBar(val message: String) : AppEvents()
}