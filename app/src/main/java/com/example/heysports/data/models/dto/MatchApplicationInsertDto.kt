package com.example.heysports.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchApplicationInsertDto(
    @SerialName("match_request_id")
    val matchRequestId: Long,
    @SerialName("applicant_user_id")
    val applicantUserId: String,
    @SerialName("applicant_team_id")
    val applicantTeamId: Long? = null,
    val message: String? = null,
    @SerialName("contact_phone")
    val contactPhone: String? = null
)
