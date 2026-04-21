package com.example.heysports.cores.utils

import android.util.Patterns
import com.example.heysports.R

object Validators {

    fun validateEmail(email: String?): Int? {
        return when {
            email.isNullOrBlank() -> R.string.authEmailEmpty
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.authEmailInValid
            else -> null

        }
    }

    fun validatePassword(value: String?, isRegister: Boolean = true): Int? {
        if (isRegister) {
            if (value.isNullOrBlank()) return R.string.authPasswordEmpty
            if (value.length < 8) return R.string.authPasswordLess8
            if (!value.any { it.isDigit() }) return R.string.authPasswordLessOneNUmber
        } else {
            if (value.isNullOrBlank()) return R.string.authPasswordEmpty
        }
        return null
    }
}