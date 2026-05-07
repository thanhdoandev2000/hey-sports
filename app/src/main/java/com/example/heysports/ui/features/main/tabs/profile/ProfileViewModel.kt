package com.example.heysports.ui.features.main.tabs.profile

import com.example.heysports.domain.repositories.AuthRepository
import com.example.heysports.ui.base.BaseViewModel
import com.example.heysports.ui.base.UiEffect
import com.example.heysports.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

data class ProfileUiState(
    val isLoading: Boolean = false
) : UiState

sealed class ProfileUiEffect : UiEffect {
    object NavigateToLogin : ProfileUiEffect()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<ProfileUiState, UiEffect>(initialState = ProfileUiState()) {

    fun signOut() {
        callApi(
            request = { authRepository.singOut() },
            onSuccess = {
                sendEffect(ProfileUiEffect.NavigateToLogin)
            }
        )
    }
}