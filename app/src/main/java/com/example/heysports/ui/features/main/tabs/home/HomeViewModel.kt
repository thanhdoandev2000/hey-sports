package com.example.heysports.ui.features.main.tabs.home

import com.example.heysports.data.models.response.ApiRequest
import com.example.heysports.domain.models.PersonInfo
import com.example.heysports.domain.repositories.AuthRepository
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

data class UpcomingMatch(
    val id: String,
    val dateTime: String,
    val location: String,
    val homeTeam: Team,
    val awayTeam: Team
)

data class Matchmaking(
    val id: String,
    val name: String,
    val avatar: String? = null,
    val dateTime: String,
    val location: String,
    val description: String,
    val isFindMember: Boolean = false
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
    val user: PersonInfo,
    val time: String,
    val status: String,
    val content: String,
    val image: String? = null,
    val like: Int,
    val comment: Int
)

data class HomeUiState(
    val isLoading: Boolean = false,
    val personInfo: PersonInfo? = null,
    val upComingMatches: List<UpcomingMatch> = emptyList(),
    val matchesLive: List<MatchLive> = emptyList(),
    val newsFeeds: List<NewsFeed> = emptyList(),
    val matchmakingSections: List<Matchmaking> = emptyList()
) : UiState

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<HomeUiState, HomeUiEffect>(
    initialState = HomeUiState(),
    loadingReducer = { loading -> copy(isLoading = loading) }
) {
    private fun getDataFromServer() {
        callApis(
            requests = listOf(
                ApiRequest(
                    request = { authRepository.getPersonInfo() },
                    onSuccess = {
                        updateState { copy(personInfo = it) }
                    }
                )
            ),
            onDone = {}
        )
    }

    init {
        getDataFromServer()
    }
}