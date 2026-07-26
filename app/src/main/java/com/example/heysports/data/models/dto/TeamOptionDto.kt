package com.example.heysports.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamOptionDto(
    val id: Long,
    @SerialName("team_name")
    val teamName: String,
    val avatar: String? = null,
    val level: String? = null,
    @SerialName("phone_number")
    val phoneNumber: String? = null
)
