package com.example.heysports.domain.models

data class Team(
    val id: String,
    val name: String,
    val avatar: String? = null,
    val teamMember: List<UserInfo> = listOf(),
    val description: String? = null,
    val captain: UserInfo? = null
)