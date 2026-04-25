package com.example.heysports.ui.features.onboarding

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.SportsSoccer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewModelScope
import com.example.heysports.R
import com.example.heysports.data.models.UiEffect
import com.example.heysports.data.models.UiState
import com.example.heysports.data.repositories.authRepository.AuthRepository
import com.example.heysports.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingPage(
    val icon: ImageVector,
    val title: Int,
    val description: Int,
    val backgroundColor: Color = Color(0xFFE8F5E9)
)

data class GettingUiState(val slides: List<OnboardingPage> = emptyList()) : UiState

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<GettingUiState, UiEffect>(initialState = GettingUiState()) {

    init {
        updateState {
            copy(
                slides = listOf(
                    OnboardingPage(
                        title = R.string.gettingTitle,
                        description = R.string.gettingFindMatches,
                        icon = Icons.Rounded.SportsSoccer
                    ),
                    OnboardingPage(
                        title = R.string.gettingFindFootballFieldTitle,
                        description = R.string.gettingFootballField,
                        icon = Icons.Rounded.LocationOn
                    ),
                    OnboardingPage(
                        title = R.string.gettingManageTeamTitle,
                        description = R.string.gettingManageTeam,
                        icon = Icons.Rounded.Groups
                    )
                )
            )
        }
    }

    fun updatePreview() {
        viewModelScope.launch {
            authRepository.updateGettingStarted()
        }
    }
}