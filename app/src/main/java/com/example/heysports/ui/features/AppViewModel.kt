package com.example.heysports.ui.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heysports.data.repositories.authRepository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class AppViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _destination = MutableStateFlow<SplashDestination?>(null)
    val destination: StateFlow<SplashDestination?> = _destination

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            combine(
                authRepository.isLoggedIn(),
                authRepository.isGettingStarted()
            ) { isLoggedIn, isGettingStarted ->
                when {
                    !isGettingStarted -> SplashDestination.Onboarding
                    isLoggedIn -> SplashDestination.Home
                    else -> SplashDestination.Login
                }
            }
                .filterNotNull()
                .first()
                .let { _destination.value = it }
        }
    }
}

sealed class SplashDestination {
    object Onboarding : SplashDestination()
    object Login : SplashDestination()
    object Home : SplashDestination()
}