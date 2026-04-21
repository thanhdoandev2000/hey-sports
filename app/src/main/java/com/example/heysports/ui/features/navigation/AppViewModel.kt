package com.example.heysports.ui.features.navigation

import com.example.heysports.data.model.UiEffect
import com.example.heysports.data.model.UiState
import com.example.heysports.data.repositories.authRepository.AuthRepository
import com.example.heysports.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class AppUiState(
    val isLoading: Boolean = false
) : UiState

@HiltViewModel
class AppViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<UiState, UiEffect>(AppUiState()) {

}