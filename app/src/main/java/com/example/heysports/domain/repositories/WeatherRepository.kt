package com.example.heysports.domain.repositories

import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.domain.models.MatchWeather

interface WeatherRepository {
    suspend fun getMatchWeather(
        latitude: Double,
        longitude: Double,
        matchTime: String
    ): NetworkResult<MatchWeather>
}
