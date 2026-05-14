package com.example.heysports.ui.features.main.tabs.home.posts

import android.net.Uri
import com.example.heysports.domain.models.PitchSelectionModel
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
    val pitchRepository: PitchRepository
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
            is MatchRequestEffect.OnCostChange -> updateState { copy(cost = effect.cost) }
            is MatchRequestEffect.OnDescriptionChange -> updateState { copy(description = effect.desc) }
            is MatchRequestEffect.OnIsShowMyTeamChange -> updateState { copy(isShowMyTeam = effect.isShow) }
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
}
