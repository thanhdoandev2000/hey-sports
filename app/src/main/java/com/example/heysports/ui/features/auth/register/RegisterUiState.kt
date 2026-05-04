package com.example.heysports.ui.features.auth.register

import com.example.heysports.cores.utils.Validators.validateConfirmPassword
import com.example.heysports.cores.utils.Validators.validateEmail
import com.example.heysports.cores.utils.Validators.validateFieldState
import com.example.heysports.cores.utils.Validators.validatePassword
import com.example.heysports.cores.utils.Validators.validatePhoneNumber
import com.example.heysports.cores.models.FieldState
import com.example.heysports.data.models.dto.PersonInfoDto
import com.example.heysports.ui.base.UiEffect
import com.example.heysports.ui.base.UiState

data class RegisterUiState(
    val isLoading: Boolean = false,
    val email: FieldState<String> = FieldState(""),
    val password: FieldState<String> = FieldState(""),
    val passwordConfirm: FieldState<String> = FieldState(""),
    val fullName: FieldState<String> = FieldState(""),
    val phoneNumber: FieldState<String> = FieldState(""),
) : UiState {
    val isFormValid: Boolean
        get() =
            email.isValid && password.isValid && passwordConfirm.isValid && fullName.isValid && phoneNumber.isValid

    fun toEntity(): PersonInfoDto = PersonInfoDto(
        name = fullName.value,
        email = email.value,
        password = password.value,
        phone = phoneNumber.value
    )
}

sealed class RegisterUiEffect : UiEffect {
    abstract val value: String
    abstract fun validate(state: RegisterUiState): Int?

    data class NavigateToHome(override val value: String) : RegisterUiEffect() {
        override fun validate(state: RegisterUiState) = validateEmail(value)
    }

    data class Email(override val value: String) : RegisterUiEffect() {
        override fun validate(state: RegisterUiState) = validateEmail(value)
    }

    data class Password(override val value: String) : RegisterUiEffect() {
        override fun validate(state: RegisterUiState) = validatePassword(value)
    }

    data class ConfirmPassword(override val value: String) : RegisterUiEffect() {
        override fun validate(state: RegisterUiState) =
            validateConfirmPassword(value, state.password.value)
    }

    data class UserName(override val value: String) : RegisterUiEffect() {
        override fun validate(state: RegisterUiState) = validateFieldState(value)
    }

    data class PhoneNumber(override val value: String) : RegisterUiEffect() {
        override fun validate(state: RegisterUiState) = validatePhoneNumber(value)
    }
}