package com.example.heysports.cores.utils

import android.util.Patterns
import com.example.heysports.R

object Validators {

    fun validateEmail(email: String?): Int? {
        return when {
            email.isNullOrBlank() -> R.string.authEmailEmpty
            ! Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.authEmailInValid
            else -> null

        }
    }

    fun validatePassword(value: String?, isRegister: Boolean = true): Int? {
        if (isRegister) {
            if (value.isNullOrBlank()) return R.string.authPasswordEmpty
            if (value.length < 8) return R.string.authPasswordLess8
            if (! value.any { it.isDigit() }) return R.string.authPasswordLessOneNUmber
        } else {
            if (value.isNullOrBlank()) return R.string.authPasswordEmpty
        }
        return null
    }

    fun validateConfirmPassword(value: String, password: String): Int? {
        if (value.isBlank()) return R.string.authPasswordEmpty
        if (value != password) return R.string.authConfirmPasswordNotMatch
        return null
    }

    fun validatePhoneNumber(phone: String): Int? {
        val vnPhoneRegex = Regex("^(0[3|5|7|8|9])+([0-9]{8})$")
        if (phone.length < 10) return R.string.authErrorPhoneMinLength
        if (phone.length > 11) return R.string.authErrorPhoneMaxLength
        if (! phone.all { it.isDigit() } || ! vnPhoneRegex.matches(phone)) return R.string.authErrorPhoneInvalidFormat
        return null
    }

    fun validateFieldState(value: String?): Int? {
        return if (value.isNullOrBlank()) R.string.authFieldError else null
    }
}