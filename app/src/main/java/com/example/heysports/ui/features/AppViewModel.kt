package com.example.heysports.ui.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heysports.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class AppViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _destination = MutableStateFlow<SplashDestination?>(null)
    val destination: StateFlow<SplashDestination?> = _destination

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                authRepository.isLoggedIn(),
                authRepository.isGettingStarted()
            ) { isLoggedIn, isGettingStarted ->
                when {
                    ! isGettingStarted -> SplashDestination.Onboarding
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