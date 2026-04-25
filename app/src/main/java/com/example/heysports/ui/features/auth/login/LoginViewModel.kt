package com.example.heysports.ui.features.auth.login

import android.content.Context
import androidx.activity.result.ActivityResultRegistryOwner
import com.example.heysports.cores.utils.Validators.validateEmail
import com.example.heysports.cores.utils.Validators.validatePassword
import com.example.heysports.data.models.UiEffect
import com.example.heysports.data.models.UiState
import com.example.heysports.data.models.app.FieldState
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

    fun updateEmail(email: String, isBure: Boolean = false) {
        updateState {
            copy(
                email = FieldState(
                    value = email.trim(),
                    error = if (isBure) validateEmail(email) else null,
                    isTouched = isBure
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

    fun loginFacebook(activity: ActivityResultRegistryOwner) {
        callApi(
            request = { authRepository.loginWithFacebook(activity) },
            onSuccess = {
                sendEffect(LoginUiEffect.NavigateToHome)
            }
        )
    }

    fun loginGoogle(context: Context) {
        callApi(
            request = { authRepository.loginWithGoogle(context) },
            onSuccess = {
                sendEffect(LoginUiEffect.NavigateToHome)
            }
        )
    }
}