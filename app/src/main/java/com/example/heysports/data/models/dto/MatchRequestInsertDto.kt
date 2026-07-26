package com.example.heysports.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchRequestInsertDto(
    @SerialName("user_id")
    val userId: String,
    @SerialName("posted_by_type")
    val postedByType: String,
    val type: String,
    @SerialName("match_time")
    val matchTime: String,
    val description: String? = null,
    val status: String = "open",
    @SerialName("pitch_id")
    val pitchId: Long,
    @SerialName("sub_pitch_id")
    val subPitchId: Long? = null,
    @SerialName("skill_level")
    val skillLevel: String? = null,
    @SerialName("match_format")
    val matchFormat: String,
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
    val photoUrls: List<String> = emptyList()
)
