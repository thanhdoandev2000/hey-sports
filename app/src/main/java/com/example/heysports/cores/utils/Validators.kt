package com.example.heysports.cores.utils

import android.util.Patterns
import com.example.heysports.R

object Validators {

    fun emailError(email: String): Int? {
        return when {
            email.isBlank() -> R.string.authEmailEmpty
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.authEmailInValid
            else -> null

        }
    }
}