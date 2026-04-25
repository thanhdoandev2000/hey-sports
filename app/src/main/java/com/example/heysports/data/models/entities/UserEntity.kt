package com.example.heysports.data.models.entities

import com.example.heysports.ui.features.auth.register.RegisterUiState

data class PersonEntity(
    val id: String? = null,
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String? = null,
    val avatar: String? = null
) {
    companion object {
        fun RegisterUiState.toEntity() = PersonEntity(
            name = fullName.value,
            email = email.value,
            password = password.value,
            phoneNumber = phoneNumber.value
        )
    }
}