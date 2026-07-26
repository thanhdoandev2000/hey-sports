package com.example.heysports.data.sources.remote

import com.example.heysports.cores.utils.DateTimeUtils
import com.example.heysports.data.models.dto.WeatherForecastDto
import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.data.networks.safeApiCall
import com.example.heysports.di.IoDispatcher
import com.example.heysports.domain.models.MatchWeather
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherDataSource @Inject constructor(
    private val httpClient: HttpClient,
    private val json: Json,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getMatchWeather(
        latitude: Double,
        longitude: Double,
        matchTime: String
    ): NetworkResult<MatchWeather> {
        return safeApiCall(ioDispatcher) {
            val response = httpClient.get(FORECAST_URL) {
                parameter("latitude", latitude)
                parameter("longitude", longitude)
                parameter(
                    "hourly",
                    "temperature_2m,weather_code,precipitation_probability"
                )
                parameter("timezone", "UTC")
                parameter("forecast_days", 16)
            }
            val forecast = json.decodeFromString<WeatherForecastDto>(response.bodyAsText())
            val hourly = requireNotNull(forecast.hourly) {
                "Không có dữ liệu dự báo thời tiết"
            }
            val targetHour = DateTimeUtils.getWeatherForecastHour(matchTime)
            val index = hourly.time.indexOf(targetHour)
            require(index >= 0) {
                "Thời gian trận đấu nằm ngoài phạm vi dự báo"
            }

            MatchWeather(
                temperatureCelsius = hourly.temperature.getOrNull(index)?.roundToInt()
                    ?: error("Thiếu dữ liệu nhiệt độ"),
                weatherCode = hourly.weatherCode.getOrNull(index)
                    ?: error("Thiếu trạng thái thời tiết"),
                precipitationProbability = hourly.precipitationProbability.getOrNull(index)
            )
        }
    }

    private companion object {
        const val FORECAST_URL = "https://api.open-meteo.com/v1/forecast"
    }
}
