package com.example.heysports.ui.features.main.tabs.home.posts

import android.net.Uri
import com.example.heysports.data.models.enums.EMatchType
import com.example.heysports.domain.models.DropdownModel
import com.example.heysports.ui.base.UiEffect
import com.example.heysports.ui.base.UiState

data class MatchRequestUiState(
    val isLoading: Boolean,
    val photos: List<String> = mutableListOf(),
    val startTime: String? = null,
    val matchType: EMatchType = EMatchType.FIVE_VS_FIVE,
    val locationPitch: DropdownModel? = null,
    val cost: String? = null,
    val description: String? = null,
    val isShowMyTeam: Boolean = true
) : UiState

sealed class MatchRequestEffect : UiEffect {
    data class OnMatchTypeChange(val type: EMatchType) : MatchRequestEffect()
    data class OnDescriptionChange(val desc: String) : MatchRequestEffect()
    data class OnDateChange(val date: String) : MatchRequestEffect()
    data class OnPhotoAdded(val uri: Uri) : MatchRequestEffect()
    data class OnCostChange(val cost: String) : MatchRequestEffect()
    data class OnIsShowMyTeamChange(val isShow: Boolean) : MatchRequestEffect()
    data class OnLocationChange(val location: DropdownModel) : MatchRequestEffect()
}