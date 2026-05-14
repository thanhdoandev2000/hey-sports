package com.example.heysports.data.models.dto

import com.example.heysports.domain.models.PitchSelectionModel
import com.example.heysports.domain.models.SubPitchSelectionModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PitchDto(
    val id: Long,
    val name: String,
    val address: String,
    val photo: String? = null,

    @SerialName("sub_pitches")
    val subPitches: List<SubPitchDto>
)

@Serializable
data class SubPitchDto(
    val id: Long,

    @SerialName("pitch_name")
    val pitchName: String,

    val type: String,

    @SerialName("is_available")
    val isAvailable: Boolean
)

fun PitchDto.toDomain(): PitchSelectionModel {
    return PitchSelectionModel(
        id = id,
        name = name,
        address = address,
        photo = photo,
        subPitches = subPitches.map { it.toDomain() }
    )
}

fun SubPitchDto.toDomain(): SubPitchSelectionModel {
    return SubPitchSelectionModel(
        id = id,
        pitchName = pitchName,
        type = type,
        isAvailable = isAvailable
    )
}