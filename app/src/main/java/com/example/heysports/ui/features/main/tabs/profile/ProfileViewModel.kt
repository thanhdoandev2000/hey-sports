package com.example.heysports.ui.features.main.tabs.profile

import com.example.heysports.data.models.dto.TeamOptionDto
import com.example.heysports.data.models.enums.EMatchLevel
import com.example.heysports.data.models.response.apiRequestOf
import com.example.heysports.domain.models.UserInfo
import com.example.heysports.domain.repositories.AuthRepository
import com.example.heysports.domain.repositories.MatchesRepository
import com.example.heysports.ui.base.BaseViewModel
import com.example.heysports.ui.base.UiEffect
import com.example.heysports.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

data class PlayerStats(
    val matches: Int? = null,
    val wins: Int? = null
)

data class PlayerRatingSummary(
    val score: Double? = null
)

data class PlayerProfileSummary(
    val position: String? = null,
    val skillLevel: String? = null,
    val area: String? = null,
    val memberSinceYear: String? = null
)

data class ProfileTeamSummary(
    val id: Long,
    val name: String,
    val avatar: String? = null,
    val description: String? = null
)

data class ProfileUiState(
    val isLoading: Boolean = false,
    val isVerified: Boolean = false,
    val personInfo: UserInfo? = null,
    val playerStats: PlayerStats = PlayerStats(),
    val ratingSummary: PlayerRatingSummary = PlayerRatingSummary(),
    val playerProfile: PlayerProfileSummary = PlayerProfileSummary(),
    val primaryTeam: ProfileTeamSummary? = null
) : UiState

sealed class ProfileUiEffect : UiEffect {
    data object NavigateToLogin : ProfileUiEffect()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val matchesRepository: MatchesRepository
) : BaseViewModel<ProfileUiState, ProfileUiEffect>(
    initialState = ProfileUiState(),
    loadingReducer = { isLoading -> copy(isLoading = isLoading) }
) {

    fun loadProfile() {
        callApis(
            requests = listOf(
                apiRequestOf(
                    request = authRepository::getPersonInfo,
                    onSuccess = { personInfo ->
                        updateState {
                            copy(
                                personInfo = personInfo,
                                playerStats = playerStats.copy(
                                    matches = personInfo?.matchesPlayed
                                ),
                                ratingSummary = ratingSummary.copy(
                                    score = personInfo?.rating
                                ),
                                playerProfile = playerProfile.copy(
                                    skillLevel = toSkillLevelLabel(personInfo?.skillLevel),
                                    memberSinceYear = personInfo?.createdAt?.take(4)
                                        ?.takeIf { year -> year.all(Char::isDigit) }
                                )
                            )
                        }
                    }
                ),
                apiRequestOf(
                    request = matchesRepository::getMyTeams,
                    onSuccess = { teams ->
                        updateState {
                            copy(primaryTeam = teams.firstOrNull()?.toProfileTeamSummary())
                        }
                    }
                )
            )
        )
    }

    fun signOut() {
        callApi(
            request = { authRepository.singOut() },
            onSuccess = {
                sendEffect(ProfileUiEffect.NavigateToLogin)
            }
        )
    }

    private fun toSkillLevelLabel(rawValue: String?): String? {
        if (rawValue.isNullOrBlank()) return null
        return EMatchLevel.entries
            .firstOrNull { it.name.equals(rawValue, ignoreCase = true) }
            ?.label
            ?: rawValue
    }

    private fun TeamOptionDto.toProfileTeamSummary() = ProfileTeamSummary(
        id = id,
        name = teamName,
        avatar = avatar,
        description = level
    )
}
