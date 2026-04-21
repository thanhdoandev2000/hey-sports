package com.example.heysports.ui.features.getting

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heysports.R
import com.example.heysports.data.model.UiEffect
import com.example.heysports.data.model.UiState
import com.example.heysports.data.repositories.authRepository.AuthRepository
import com.example.heysports.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GettingSlide(
    val title: Int,
    val description: Int,
    val icon: ImageVector
)

data class GettingUiState(val slides: List<GettingSlide> = emptyList()) : UiState

@HiltViewModel
class GettingStartedViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<GettingUiState, UiEffect>(initialState = GettingUiState()) {

    init {
        updateState {
            copy(
                slides = listOf(
                    GettingSlide(
                        title = R.string.gettingTitle,
                        description = R.string.gettingFindMatches,
                        icon = Icons.Outlined.SportsSoccer
                    ),
                    GettingSlide(
                        title = R.string.gettingFindFootballFieldTitle,
                        description = R.string.gettingFootballField,
                        icon = Icons.Outlined.LocationOn
                    ),
                    GettingSlide(
                        title = R.string.gettingManageTeamTitle,
                        description = R.string.gettingManageTeam,
                        icon = Icons.Outlined.PeopleOutline
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