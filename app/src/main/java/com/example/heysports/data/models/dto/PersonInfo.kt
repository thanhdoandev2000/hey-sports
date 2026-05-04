package com.example.heysports.data.models.dto

import com.example.heysports.domain.models.PersonInfo

data class PersonInfoDto(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val phone: String = "",
    val photo: String = ""
) {
    fun toDomain() = PersonInfo(
        id = uid,
        name = name,
        email = email,
        phoneNumber = phone,
        avatar = photo
    )
}