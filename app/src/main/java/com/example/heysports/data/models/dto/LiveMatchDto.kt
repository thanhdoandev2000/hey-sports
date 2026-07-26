package com.example.heysports.data.models.dto

import kotlinx.serialization.*

@Serializable
data class LiveMatchDto(
    val id: Long,

    @SerialName("update_at")
    val updateAt: String,

    val status: String,

    @SerialName("current_minutes")
    val currentMinutes: Long,

    val half: Long,
    val duration: Long,

    @SerialName("host_score")
    val hostScore: Long,

    @SerialName("guest_score")
    val guestScore: Long,

    @SerialName("start_time")
    val startTime: String,

    @SerialName("host_team_id")
    val hostTeamId: Long?,

    @SerialName("host_team_name")
    val hostTeamName: String?,

    @SerialName("guest_team_id")
    val guestTeamId: Long?,

    @SerialName("guest_team_name")
    val guestTeamName: String?,

    @SerialName("sub_pitch_id")
    val subPitchId: Long?,

    @SerialName("sub_pitch_name")
    val subPitchName: String?,

    @SerialName("sub_pitch_type")
    val subPitchType: String?,

    @SerialName("pitch_id")
    val pitchID: Long?,

    @SerialName("pitch_name")
    val pitchName: String?,

    @SerialName("pitch_address")
    val pitchAddress: String?
)
