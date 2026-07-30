package com.example.heysports.ui.features.main.tabs.maps

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.google.android.gms.maps.model.LatLng

@Immutable
internal data class PitchUiModel(
    val id: Long,
    val name: String,
    val rating: String,
    val reviewCount: Int,
    val distanceAndArea: String,
    val pitchTypes: List<String>,
    val availability: String,
    val price: String,
    @param:DrawableRes val photoRes: Int,
    val position: LatLng
)
