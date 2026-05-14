package com.example.heysports.domain.models

data class PitchSelectionModel(
    val id: Long,
    val name: String,
    val address: String,
    val photo: String?,
    val subPitches: List<SubPitchSelectionModel>,
    var subPitchSelected: SubPitchSelectionModel? = null
) {
    val displayName: String
        get() = if (subPitchSelected != null) {
            "${subPitchSelected?.pitchName} - $name"
        } else {
            name
        }
}

data class SubPitchSelectionModel(
    val id: Long,
    val pitchName: String,
    val type: String,
    val isAvailable: Boolean
)