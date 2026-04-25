package com.example.heysports.ui.features.auth.register

import com.example.heysports.cores.utils.Validators.validateConfirmPassword
import com.example.heysports.cores.utils.Validators.validateEmail
import com.example.heysports.cores.utils.Validators.validateFieldState
import com.example.heysports.cores.utils.Validators.validatePassword
import com.example.heysports.cores.utils.Validators.validatePhoneNumber
import com.example.heysports.data.models.UiEffect
import com.example.heysports.data.models.UiState
import com.example.heysports.data.models.app.FieldState
import com.example.heysports.data.models.entities.PersonEntity.Companion.toEntity
import com.example.heysports.data.repositories.authRepository.AuthRepository
import com.example.heysports.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


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

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<RegisterUiState, RegisterUiEffect>(
    initialState = RegisterUiState(),
    loadingReducer = { loading -> copy(isLoading = loading) }) {

    fun updateField(field: RegisterUiEffect, isBure: Boolean = false) {
        updateState {
            val fieldState = FieldState(
                value = field.value.trim(),
                error = if (isBure) field.validate(this) else null,
                isTouched = isBure
            )
            when (field) {
                is RegisterUiEffect.Email -> copy(email = fieldState)
                is RegisterUiEffect.Password -> copy(password = fieldState)
                is RegisterUiEffect.ConfirmPassword -> copy(passwordConfirm = fieldState)
                is RegisterUiEffect.UserName -> copy(fullName = fieldState)
                is RegisterUiEffect.PhoneNumber -> copy(phoneNumber = fieldState)
                else -> copy()
            }
        }
    }

    fun registerAccount() {
        updateState {
            copy(
                email = email.copy(isTouched = true, error = validateEmail(email.value)),
                password = password.copy(
                    isTouched = true,
                    error = validatePassword(password.value)
                ),
                passwordConfirm = passwordConfirm.copy(
                    isTouched = true,
                    error = validateConfirmPassword(passwordConfirm.value, password.value)
                ),
                fullName = fullName.copy(
                    isTouched = true,
                    error = validateFieldState(fullName.value)
                ),
                phoneNumber = phoneNumber.copy(
                    isTouched = true,
                    error = validatePhoneNumber(phoneNumber.value)
                )
            )
        }
        if (uiState.value.isFormValid) {
            callApi(
                request = { authRepository.createAccount(uiState.value.toEntity()) },
                onSuccess = {
                    sendEffect(RegisterUiEffect.NavigateToHome(it.uid))
                }
            )
        }
    }
}