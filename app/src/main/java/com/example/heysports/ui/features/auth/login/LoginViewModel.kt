package com.example.heysports.ui.features.auth.login

import com.example.heysports.cores.utils.Validators.validateEmail
import com.example.heysports.cores.utils.Validators.validatePassword
import com.example.heysports.data.model.UiEffect
import com.example.heysports.data.model.UiState
import com.example.heysports.data.model.app.FieldState
import com.example.heysports.data.repositories.authRepository.AuthRepository
import com.example.heysports.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

data class LoginUiState(
    val email: FieldState<String> = FieldState(""),
    val password: FieldState<String> = FieldState(""),
    val isLoading: Boolean = false,
    val checked: Boolean = false
) : UiState {
    val isFormValid: Boolean get() = email.isValid && password.isValid
}

sealed class LoginUiEffect : UiEffect {
    object NavigateToHome : LoginUiEffect()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<LoginUiState, LoginUiEffect>(
    initialState = LoginUiState(),
    loadingReducer = { loading -> copy(isLoading = loading) }
) {

    fun updateEmail(email: String) {
        updateState {
            copy(
                email = FieldState(
                    value = email.trim(),
                    error = validateEmail(email),
                    isTouched = true
                )
            )
        }
    }

    fun updatePassword(password: String) {
        updateState {
            copy(password = FieldState(value = password, isTouched = true))
        }
    }

    fun updateChecked(checked: Boolean) {
        updateState { copy(checked = checked) }
    }

    fun login() {
        updateState {
            copy(
                email = email.copy(error = validateEmail(email.value), isTouched = true),
                password = password.copy(
                    error = validatePassword(password.value, isRegister = false),
                    isTouched = true
                )
            )
        }
        if (uiState.value.isFormValid) {
            callApi(
                request = {
                    authRepository.login(
                        email = uiState.value.email.value,
                        password = uiState.value.password.value
                    )
                },
                onSuccess = {
                    sendEffect(LoginUiEffect.NavigateToHome)
                }
            )
        }
    }

    fun loginFacebook() {

    }

    fun loginGoogle() {

    }
}