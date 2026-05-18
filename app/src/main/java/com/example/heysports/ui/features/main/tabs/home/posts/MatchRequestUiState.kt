package com.example.heysports.ui.features.main.tabs.home.posts

import android.net.Uri
import com.example.heysports.data.models.enums.EMatchType
import com.example.heysports.domain.models.PitchSelectionModel
import com.example.heysports.ui.base.UiEffect
import com.example.heysports.ui.base.UiState

data class MatchRequestUiState(
    val isLoading: Boolean,
    val photos: List<String> = mutableListOf(),
    val startTime: String? = null,
    val matchType: EMatchType = EMatchType.FIVE_VS_FIVE,
    val pitch: PitchSelectionModel? = null,
    val phoneNumber: String? = null,
    val description: String? = null,
    val moreInfo: MoreInformationUiState? = null
) : UiState

data class MoreInformationUiState(
    val fee: String? = null,
    val age: String? = null,
    val moreNotes: String? = null,
    val rule: List<String>? = null,
    val teamLevel: String? = null,
    val teamStyle: String? = null,
    val teamStatus: String? = null
)

sealed class MatchRequestEffect : UiEffect {
    data class OnMatchTypeChange(val type: EMatchType) : MatchRequestEffect()
    data class OnDescriptionChange(val desc: String) : MatchRequestEffect()
    data class OnDateChange(val date: String) : MatchRequestEffect()
    data class OnPhotoAdded(val uri: Uri) : MatchRequestEffect()
    data class OnLocationChange(val pitch: PitchSelectionModel) : MatchRequestEffect()
    data class OnUpdatePhoneNumber(val phoneNumber: String) : MatchRequestEffect()
    data class OnUpdateMoreInfo(val info: MoreInformationUiState) : MatchRequestEffect()
}


data class SelectionModel<T>(
    val isLoading: Boolean,
    val items: List<T>
)
