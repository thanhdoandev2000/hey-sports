package com.example.heysports.domain.models

data class UpComingMatches(
    val id: String,
    val dateTime: String,
    val hostTeam: Team,
    val guestTeam: Team,
    val location: String
)