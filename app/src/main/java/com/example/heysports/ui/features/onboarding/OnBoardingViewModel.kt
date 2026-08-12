package com.example.heysports.ui.features.onboarding

import androidx.lifecycle.viewModelScope
import com.example.heysports.R
import com.example.heysports.ui.base.UiEffect
import com.example.heysports.ui.base.UiState
import com.example.heysports.domain.repositories.AuthRepository
import com.example.heysports.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingPage(
    val illustration: Int,
    val title: Int,
    val description: Int
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
                        illustration = R.drawable.onboarding_find_match
                    ),
                    OnboardingPage(
                        title = R.string.gettingFindFootballFieldTitle,
                        description = R.string.gettingFootballField,
                        illustration = R.drawable.onboarding_find_pitch
                    ),
                    OnboardingPage(
                        title = R.string.gettingManageTeamTitle,
                        description = R.string.gettingManageTeam,
                        illustration = R.drawable.onboarding_manage_team
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