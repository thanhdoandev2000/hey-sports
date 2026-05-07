package com.example.heysports.ui.features.main.tabs.home

import com.example.heysports.data.models.dto.MatchRequestDto
import com.example.heysports.data.models.dto.UpcomingMatchDto
import com.example.heysports.data.models.response.apiRequestOf
import com.example.heysports.domain.models.UserInfo
import com.example.heysports.domain.repositories.AuthRepository
import com.example.heysports.domain.repositories.MatchesRepository
import com.example.heysports.ui.base.BaseViewModel
import com.example.heysports.ui.base.UiEffect
import com.example.heysports.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

interface HomeUiEffect : UiEffect

data class Team(
    val id: String,
    val name: String,
    val avatar: String? = null,
)

data class MatchLive(
    val id: String,
    val duration: Int,
    val location: String,
    val matchScore: String,
    val homeTeam: Team,
    val awayTeam: Team
)

data class NewsFeed(
    val id: String,
    val user: UserInfo,
    val time: String,
    val status: String,
    val content: String,
    val image: String? = null,
    val like: Int,
    val comment: Int
)

data class HomeUiState(
    val isLoading: Boolean = false,
    val isLoadingUpComing: Boolean = false,
    val isLoadingMatchRequests: Boolean = false,
    val personInfo: UserInfo? = null,
    val upComingMatches: List<UpcomingMatchDto> = emptyList(),
    val matchesLive: List<MatchLive> = emptyList(),
    val newsFeeds: List<NewsFeed> = emptyList(),
    val matchRequests: List<MatchRequestDto> = emptyList()
) : UiState

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val matchesRepository: MatchesRepository
) : BaseViewModel<HomeUiState, HomeUiEffect>(
    initialState = HomeUiState(),
    loadingReducer = { loading -> copy(isLoading = loading) }
) {
    internal fun getDataFromServer() {
        updateState {
            copy(
                isLoading = true,
                isLoadingUpComing = true,
                isLoadingMatchRequests = true
            )
        }
        callApis(
            requests = listOf(
                apiRequestOf(
                    request = { authRepository.getPersonInfo() },
                    onSuccess = {
                        updateState { copy(personInfo = it, isLoading = false) }
                    }
                ),
                apiRequestOf(
                    request = { matchesRepository.getUpcomingMatches() },
                    onSuccess = {
                        updateState { copy(upComingMatches = it, isLoadingUpComing = false) }
                    }
                ),
                apiRequestOf(
                    request = { matchesRepository.getMatchRequests() },
                    onSuccess = {
                        updateState { copy(matchRequests = it, isLoadingMatchRequests = false) }
                    }
                )
            ),
            onDone = {}
        )
    }
}