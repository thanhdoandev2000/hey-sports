package com.example.heysports.data.model.app

import androidx.annotation.StringRes

data class FieldState<T>(
    val value: T,
    @param:StringRes val error: Int? = null,
    val isTouched: Boolean = false
) {
    val isValid: Boolean get() = error == null
    val showError: Boolean get() = isTouched && error != null
}