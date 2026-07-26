package com.example.heysports.data.repositories

import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.data.sources.remote.WeatherDataSource
import com.example.heysports.domain.models.MatchWeather
import com.example.heysports.domain.repositories.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDataSource: WeatherDataSource
) : WeatherRepository {
    override suspend fun getMatchWeather(
        latitude: Double,
        longitude: Double,
        matchTime: String
    ): NetworkResult<MatchWeather> {
        return weatherDataSource.getMatchWeather(latitude, longitude, matchTime)
    }
}
