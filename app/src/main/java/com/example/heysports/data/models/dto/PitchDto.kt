package com.example.heysports.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PitchDto(
    val id: Long,
    @SerialName("created_at")
    val createdAt: String,

    val name: String,

    @SerialName("own_id")
    val ownId: String,

    val description: String,

    @SerialName("arg_rating")
    val argRating: Long,

    @SerialName("phone_number")
    val phoneNumber: String,

    @SerialName("open_hour")
    val openHour: String,

    @SerialName("close_hour")
    val closeHour: String,

    @SerialName("min_price")
    val minPrice: String,

    @SerialName("max_price")
    val maxPrice: String,

    val status: String,

    @SerialName("has_packing")
    val hasPacking: Boolean,

    @SerialName("has_wifi")
    val hasWifi: Boolean,

    @SerialName("has_canteen")
    val hasCanteen: Boolean,

    @SerialName("has_toilet")
    val hasToilet: Boolean,

    val photo: String? = null,
    val address: String,
    val lat: String,
    val long: String
)
