package com.example.heysports.ui.features.main.tabs.home.posts

import android.net.Uri
import com.example.heysports.cores.utils.DateTimeUtils
import com.example.heysports.cores.events.AppEvents
import com.example.heysports.data.models.dto.MatchRequestInsertDto
import com.example.heysports.domain.models.PitchSelectionModel
import com.example.heysports.domain.repositories.MatchesRepository
import com.example.heysports.domain.repositories.PitchRepository
import com.example.heysports.domain.repositories.UploadRepository
import com.example.heysports.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MatchRequestViewModel @Inject constructor(
    val uploadRepository: UploadRepository,
    val pitchRepository: PitchRepository,
    val matchesRepository: MatchesRepository
) : BaseViewModel<MatchRequestUiState, MatchRequestEffect>(
    initialState = MatchRequestUiState(false),
    loadingReducer = { loading -> copy(isLoading = loading) }
) {
    private val _pitches: MutableStateFlow<SelectionModel<PitchSelectionModel>> =
        MutableStateFlow(SelectionModel(false, listOf()))
    val pitches: StateFlow<SelectionModel<PitchSelectionModel>> = _pitches.asStateFlow()

    private fun uploadPhoto(ui: Uri) {
        callApi(
            request = { uploadRepository.uploadPhoto(ui) },
            onSuccess = {
                updateState { copy(photos = photos.plus(it)) }
            }
        )
    }

    fun updateUiState(effect: MatchRequestEffect) {
        when (effect) {
            is MatchRequestEffect.OnPhotoAdded -> uploadPhoto(effect.uri)
            is MatchRequestEffect.OnMatchTypeChange -> updateState { copy(matchType = effect.type) }
            is MatchRequestEffect.OnDateChange -> updateState { copy(startTime = effect.date) }
            is MatchRequestEffect.OnLocationChange -> updateState { copy(pitch = effect.pitch) }
            is MatchRequestEffect.OnDescriptionChange -> updateState { copy(description = effect.desc) }
            is MatchRequestEffect.OnUpdatePhoneNumber -> updateState { copy(phoneNumber = effect.phoneNumber) }
            is MatchRequestEffect.OnUpdateMoreInfo -> updateState { copy(moreInfo = effect.info) }
        }
    }

    fun getPitches(search: String) {
        _pitches.update { it.copy(isLoading = true) }
        callApi(
            request = { pitchRepository.getPitches(search) },
            onSuccess = { items ->
                _pitches.update { it.copy(isLoading = false, items = items) }
            },
            onError = {
                _pitches.update { it.copy(isLoading = false) }
            },
            isLoading = false
        )
    }

    fun createMatchRequest() {
        val request = uiState.value.toInsertDto(
            userId = matchesRepository.currentUserId()
        ) ?: return

        callApi(
            request = { matchesRepository.createMatchRequest(request) },
            onSuccess = {
                updateState { MatchRequestUiState(isLoading = false) }
                sendEffectGlobal(AppEvents.ShowToast("Đăng bài tìm đối thủ thành công"))
            }
        )
    }

    private fun MatchRequestUiState.toInsertDto(userId: String?): MatchRequestInsertDto? {
        val selectedPitch = pitch
        val selectedMatchTime = startTime

        when {
            userId.isNullOrBlank() -> "Bạn cần đăng nhập để đăng bài"
            selectedMatchTime.isNullOrBlank() -> "Vui lòng chọn thời gian thi đấu"
            !DateTimeUtils.isFutureMatchTime(selectedMatchTime) ->
                "Thời gian thi đấu phải ở tương lai"
            selectedPitch == null -> "Vui lòng chọn sân thi đấu"
            else -> null
        }?.let {
            sendEffectGlobal(AppEvents.ShowGlobalError(it))
            return null
        }

        val validUserId = userId.orEmpty()
        val validMatchTime = selectedMatchTime.orEmpty()
        val validPitch = selectedPitch ?: return null

        return MatchRequestInsertDto(
            userId = validUserId,
            postedByType = "PLAYER",
            type = "FIND_OPPONENT",
            matchTime = validMatchTime,
            description = description?.trim(),
            pitchId = validPitch.id,
            subPitchId = validPitch.subPitchSelected?.id,
            skillLevel = moreInfo?.teamLevel.toFootballLevel(),
            matchFormat = matchType.label,
            contactPhone = phoneNumber?.trim()?.takeIf(String::isNotEmpty),
            feeType = moreInfo?.fee,
            ageGroup = moreInfo?.age,
            teamStyle = moreInfo?.teamStyle,
            teamStatus = moreInfo?.teamStatus,
            rules = moreInfo?.rule.orEmpty(),
            moreNotes = moreInfo?.moreNotes?.trim()?.takeIf(String::isNotEmpty),
            photoUrls = photos
        )
    }

    private fun String?.toFootballLevel(): String? {
        return when (this) {
            "WEAK" -> "Trung Bình - Yếu"
            "AVERAGE" -> "Trung Bình - Khá"
            "STRONG" -> "Khá - Mạnh"
            "VERY_STRONG" -> "Mạnh"
            else -> null
        }
    }
}
