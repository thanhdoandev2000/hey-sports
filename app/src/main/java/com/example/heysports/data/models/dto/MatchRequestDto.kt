package com.example.heysports.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchRequestDto(
    val id: Long,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("posted_by_type")
    val postedByType: String,

    val type: String,

    @SerialName("match_time")
    val matchTime: String? = null,

    val description: String? = null,
    val status: String? = null,
    @SerialName("skill_level")
    val skillLevel: String? = null,

    @SerialName("match_format")
    val matchFormat: String? = null,

    @SerialName("contact_phone")
    val contactPhone: String? = null,

    @SerialName("fee_type")
    val feeType: String? = null,

    @SerialName("age_group")
    val ageGroup: String? = null,

    @SerialName("team_style")
    val teamStyle: String? = null,

    @SerialName("team_status")
    val teamStatus: String? = null,

    val rules: List<String> = emptyList(),

    @SerialName("more_notes")
    val moreNotes: String? = null,

    @SerialName("photo_urls")
    val photoUrls: List<String> = emptyList(),

    @SerialName("team_id")
    val teamId: Long? = null,

    @SerialName("team_name")
    val teamName: String? = null,

    @SerialName("team_avatar")
    val teamAvatar: String? = null,

    @SerialName("team_area")
    val teamArea: String? = null,

    @SerialName("user_id")
    val userId: String? = null,

    @SerialName("user_name")
    val userName: String? = null,

    @SerialName("user_avatar")
    val userAvatar: String? = null,

    @SerialName("pitch_id")
    val pitchId: Long? = null,

    @SerialName("sub_pitch_id")
    val subPitchId: Long? = null,

    @SerialName("pitch_name")
    val pitchName: String? = null,

    @SerialName("pitch_address")
    val pitchAddress: String? = null,

    @SerialName("pitch_lat")
    val pitchLat: String? = null,

    @SerialName("pitch_long")
    val pitchLong: String? = null
)
