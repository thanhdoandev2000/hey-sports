package com.example.heysports.domain.models

data class UserInfo(
    val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String? = null,
    val avatar: String? = null,
    val matchesPlayed: Int = 0,
    val rating: Double? = null,
    val skillLevel: String? = null,
    val createdAt: String? = null
)
