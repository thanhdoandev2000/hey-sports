package com.example.heysports.data.models.dto

import com.example.heysports.cores.extensions.getValue
import com.example.heysports.domain.models.UserInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    @SerialName("full_name")
    val fullName: String? = null,
    @SerialName("phone_number")
    val phoneNumber: String? = null,
    val email: String? = null,
    val avatar: String? = null,
    @SerialName("date_of_birth")
    val dateOfBirth: String? = null,
    @SerialName("matches_played")
    val matchesPlayed: Int = 0,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    val rating: Double = 0.0,
    @SerialName("shirt_number")
    val shirtNumber: Int? = null,
    @SerialName("skill_level")
    val skillLevel: String? = null,
    val role: String? = null,
    val password: String? = null
) {
    fun toDomain() = UserInfo(
        id = id,
        name = fullName.getValue(),
        email = email.getValue(),
        phoneNumber = phoneNumber,
        avatar = avatar,
        matchesPlayed = matchesPlayed,
        rating = rating.takeIf { it > 0.0 },
        skillLevel = skillLevel,
        createdAt = createdAt
    )

}
