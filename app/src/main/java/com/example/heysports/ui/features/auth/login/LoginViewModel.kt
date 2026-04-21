package com.example.heysports.ui.features.auth.login

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.heysports.cores.utils.Validators.emailError
import com.example.heysports.data.model.UiEffect
import com.example.heysports.data.model.UiState
import com.example.heysports.data.networks.NetworkResult
import com.example.heysports.data.repositories.authRepository.AuthRepository
import com.example.heysports.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlin.math.log

data class LoginUiState(
    val email: String? = "thanhdoandev@gmail.com",
    val password: String? = "Pass!234",
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isLoading: Boolean = false,
    val checked: Boolean = false
) : UiState

sealed class LoginUiEffect : UiEffect {
    object NavigateToHome : LoginUiEffect()
    object NavigateToRegister : LoginUiEffect()
    object NavigateToForgotPassword : LoginUiEffect()
    object Error : LoginUiEffect()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<LoginUiState, LoginUiEffect>(
    initialState = LoginUiState(),
    loadingReducer = { loading -> copy(isLoading = loading) }
) {

    fun updateEmail(email: String) {
        updateState { copy(email = email, emailError = emailError(email)) }
    }

    fun updatePassword(password: String) {
        updateState { copy(password = password) }
    }

    fun updateChecked(checked: Boolean) {
        updateState { copy(checked = checked) }
    }

    fun login() {
        val loginState = uiState.value
        val isEmailValid = loginState.email.isNullOrBlank().not() && loginState.emailError != null
        val isPasswordValid = loginState.password.isNullOrBlank().not()
        callApi(
            request = {
                authRepository.login(
                    email = loginState.email.orEmpty(),
                    password = loginState.password.orEmpty()
                )
            },
            onSuccess = {}
        )
    }

    fun loginFacebook() {

    }

    fun loginGoogle() {

    }
}