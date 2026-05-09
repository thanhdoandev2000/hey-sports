package com.example.heysports.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchUpcomingDto(
    @SerialName("id") val id: Long,
    @SerialName("host_team_id") val hostTeamId: Long,
    @SerialName("guest_team_id") val guestTeamId: Long?,
    @SerialName("sub_pitch_id") val subPitchId: Long,
    @SerialName("match_time") val matchTime: String,
    @SerialName("duration") val duration: Int?,
    @SerialName("description") val description: String?,

    // Đội chủ nhà
    @SerialName("host_team_name") val hostTeamName: String,
    @SerialName("host_team_avatar") val hostTeamAvatar: String?,

    // Đội khách
    @SerialName("guest_team_name") val guestTeamName: String?,
    @SerialName("guest_team_avatar") val guestTeamAvatar: String?,

    // Sân con
    @SerialName("sub_pitch_name") val subPitchName: String,
    @SerialName("price") val price: Double?,
    @SerialName("type") val type: String?,

    // Sân chính
    @SerialName("pitch_name") val pitchName: String,
    @SerialName("pitch_address") val pitchAddress: String?,
)