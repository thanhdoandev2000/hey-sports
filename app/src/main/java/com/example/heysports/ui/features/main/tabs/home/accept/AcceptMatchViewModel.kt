package com.example.heysports.ui.features.main.tabs.home.accept

import com.example.heysports.cores.events.AppEvents
import com.example.heysports.data.models.dto.MatchApplicationInsertDto
import com.example.heysports.data.models.dto.MatchRequestDto
import com.example.heysports.data.models.dto.TeamOptionDto
import com.example.heysports.data.models.response.apiRequestOf
import com.example.heysports.domain.repositories.MatchesRepository
import com.example.heysports.ui.base.BaseViewModel
import com.example.heysports.ui.base.UiEffect
import com.example.heysports.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class AcceptMatchUiState(
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val matchRequest: MatchRequestDto? = null,
    val teams: List<TeamOptionDto> = emptyList(),
    val selectedTeamId: Long? = null,
    val isIndividual: Boolean = false,
    val phoneNumber: String = "",
    val message: String = ""
) : UiState {
    val selectedTeam: TeamOptionDto?
        get() = teams.firstOrNull { it.id == selectedTeamId }

    val canSubmit: Boolean
        get() = matchRequest?.status.equals("open", ignoreCase = true) &&
                (isIndividual || (
                        selectedTeam != null &&
                                selectedTeam?.id != matchRequest?.teamId
                        )) &&
                !isLoading &&
                !isSubmitting
}

sealed interface AcceptMatchEffect : UiEffect {
    data object Submitted : AcceptMatchEffect
}

@HiltViewModel
class AcceptMatchViewModel @Inject constructor(
    private val matchesRepository: MatchesRepository
) : BaseViewModel<AcceptMatchUiState, AcceptMatchEffect>(
    initialState = AcceptMatchUiState(),
    loadingReducer = { loading -> copy(isLoading = loading) }
) {
    private var loadedRequestId: Long? = null

    fun load(matchRequestId: Long) {
        if (loadedRequestId == matchRequestId) return
        loadedRequestId = matchRequestId

        callApis(
            requests = listOf(
                apiRequestOf(
                    request = { matchesRepository.getMatchRequest(matchRequestId) },
                    onSuccess = { request ->
                        updateState {
                            val eligibleTeam = teams.firstOrNull { it.id != request.teamId }
                            val currentSelectionIsEligible = selectedTeamId != request.teamId &&
                                    teams.any { it.id == selectedTeamId }
                            copy(
                                matchRequest = request,
                                selectedTeamId = selectedTeamId
                                    .takeIf { currentSelectionIsEligible }
                                    ?: eligibleTeam?.id,
                                isIndividual = isIndividual || eligibleTeam == null,
                                phoneNumber = phoneNumber.ifBlank {
                                    eligibleTeam?.phoneNumber.orEmpty()
                                }
                            )
                        }
                    }
                ),
                apiRequestOf(
                    request = matchesRepository::getMyTeams,
                    onSuccess = { teams ->
                        updateState {
                            val eligibleTeam = teams.firstOrNull {
                                it.id != matchRequest?.teamId
                            }
                            val currentSelectionIsEligible =
                                teams.any { it.id == selectedTeamId } &&
                                        selectedTeamId != matchRequest?.teamId
                            copy(
                                teams = teams,
                                selectedTeamId = selectedTeamId
                                    .takeIf { currentSelectionIsEligible }
                                    ?: eligibleTeam?.id,
                                isIndividual = isIndividual || eligibleTeam == null,
                                phoneNumber = phoneNumber.ifBlank {
                                    eligibleTeam?.phoneNumber.orEmpty()
                                }
                            )
                        }
                    }
                )
            ),
            onErrors = {
                loadedRequestId = null
            }
        )
    }

    fun selectTeam(teamId: Long) {
        updateState {
            val team = teams.firstOrNull { it.id == teamId } ?: return@updateState this
            if (team.id == matchRequest?.teamId) return@updateState this
            copy(
                selectedTeamId = team.id,
                isIndividual = false,
                phoneNumber = team.phoneNumber?.takeIf { it.isNotBlank() } ?: phoneNumber
            )
        }
    }

    fun selectIndividual() {
        updateState {
            copy(
                selectedTeamId = null,
                isIndividual = true
            )
        }
    }

    fun updatePhoneNumber(value: String) {
        val sanitized = value.filter { it.isDigit() || it == '+' }.take(15)
        updateState { copy(phoneNumber = sanitized) }
    }

    fun updateMessage(value: String) {
        updateState { copy(message = value.take(120)) }
    }

    fun submit() {
        val state = uiState.value
        val request = state.matchRequest
        val selectedTeam = state.selectedTeam
        val currentUserId = matchesRepository.currentUserId()

        val validationError = when {
            currentUserId.isNullOrBlank() -> "Bạn cần đăng nhập để nhận kèo"
            request == null -> "Không tìm thấy thông tin kèo"
            !request.status.equals("open", ignoreCase = true) -> "Kèo này không còn mở"
            request.userId == currentUserId -> "Bạn không thể nhận kèo do chính mình đăng"
            !state.isIndividual && selectedTeam == null -> "Vui lòng chọn đội hoặc nhận kèo với tư cách cá nhân"
            !state.isIndividual && request.teamId == selectedTeam?.id ->
                "Không thể dùng cùng một đội để nhận kèo"

            else -> null
        }

        if (validationError != null) {
            sendEffectGlobal(AppEvents.ShowGlobalError(validationError))
            return
        }

        val validRequest = request ?: return
        val validUserId = currentUserId ?: return

        updateState { copy(isSubmitting = true) }
        callApi(
            request = {
                matchesRepository.claimMatchRequest(
                    MatchApplicationInsertDto(
                        matchRequestId = validRequest.id,
                        applicantUserId = validUserId,
                        applicantTeamId = selectedTeam?.id.takeUnless {
                            state.isIndividual
                        },
                        message = state.message.trim().takeIf(String::isNotEmpty),
                        contactPhone = state.phoneNumber.trim().takeIf(String::isNotEmpty)
                    )
                )
            },
            isLoading = false,
            onSuccess = {
                updateState { copy(isSubmitting = false) }
                sendEffectGlobal(AppEvents.ShowToast("Nhận kèo thành công"))
                sendEffect(AcceptMatchEffect.Submitted)
            },
            onError = {
                updateState { copy(isSubmitting = false) }
            }
        )
    }
}
