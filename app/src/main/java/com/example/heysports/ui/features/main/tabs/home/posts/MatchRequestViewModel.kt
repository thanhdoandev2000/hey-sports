package com.example.heysports.ui.features.main.tabs.home.posts

import android.net.Uri
import com.example.heysports.domain.repositories.UploadRepository
import com.example.heysports.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchRequestViewModel @Inject constructor(
    val uploadRepository: UploadRepository
) : BaseViewModel<MatchRequestUiState, MatchRequestEffect>(
    initialState = MatchRequestUiState(false),
    loadingReducer = { loading -> copy(isLoading = loading) }
) {

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
            is MatchRequestEffect.OnLocationChange -> updateState { copy(locationPitch = effect.location) }
            is MatchRequestEffect.OnCostChange -> updateState { copy(cost = effect.cost) }
            is MatchRequestEffect.OnDescriptionChange -> updateState { copy(description = effect.desc) }
            is MatchRequestEffect.OnIsShowMyTeamChange -> updateState { copy(isShowMyTeam = effect.isShow) }
        }
    }
}