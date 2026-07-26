package com.example.heysports.domain.models

data class MatchWeather(
    val temperatureCelsius: Int,
    val weatherCode: Int,
    val precipitationProbability: Int?
)
