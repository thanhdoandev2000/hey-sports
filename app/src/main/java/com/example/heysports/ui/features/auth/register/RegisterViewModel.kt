package com.example.heysports.ui.features.auth.register

import com.example.heysports.cores.utils.Validators.validateConfirmPassword
import com.example.heysports.cores.utils.Validators.validateEmail
import com.example.heysports.cores.utils.Validators.validateFieldState
import com.example.heysports.cores.utils.Validators.validatePassword
import com.example.heysports.cores.utils.Validators.validatePhoneNumber
import com.example.heysports.cores.models.FieldState
import com.example.heysports.domain.repositories.AuthRepository
import com.example.heysports.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<RegisterUiState, RegisterUiEffect>(
    initialState = RegisterUiState(),
    loadingReducer = { loading -> copy(isLoading = loading) }) {

    fun updateField(field: RegisterUiEffect, isBure: Boolean = false) {
        updateState {
            val fieldState = FieldState(
                value = field.value,
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
                    it?.let { sendEffect(RegisterUiEffect.NavigateToHome(it)) }
                }
            )
        }
    }
}