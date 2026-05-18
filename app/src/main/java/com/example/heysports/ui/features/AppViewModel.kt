package com.example.heysports.ui.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heysports.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
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
            val isGettingStarted = authRepository.isGettingStarted().first()

            _destination.value = if (! isGettingStarted) {
                SplashDestination.Onboarding
            } else if (authRepository.isLoggedIn().first()) {
                SplashDestination.Home
            } else {
                SplashDestination.Login
            }
        }
    }
}

sealed class SplashDestination {
    object Onboarding : SplashDestination()
    object Login : SplashDestination()
    object Home : SplashDestination()
}
