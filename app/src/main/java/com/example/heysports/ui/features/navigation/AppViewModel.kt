package com.example.heysports.ui.features.navigation

import androidx.lifecycle.viewModelScope
import com.example.heysports.data.model.UiEffect
import com.example.heysports.data.model.UiState
import com.example.heysports.data.repositories.authRepository.AuthRepository
import com.example.heysports.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class AppUiState(
    val isLoading: Boolean = false
) : UiState

@HiltViewModel
class AppViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<UiState, UiEffect>(AppUiState()) {

    val isLoggedIn = authRepository.isLoggedIn()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val isGettingStarted = authRepository.isGettingStarted()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}