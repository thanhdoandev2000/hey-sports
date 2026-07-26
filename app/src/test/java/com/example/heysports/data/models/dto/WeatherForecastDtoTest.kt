package com.example.heysports.data.models.dto

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class WeatherForecastDtoTest {
    @Test
    fun `Open-Meteo hourly response is decoded`() {
        val response = Json {
            ignoreUnknownKeys = true
        }.decodeFromString<WeatherForecastDto>(
            """
            {
              "timezone": "GMT",
              "hourly": {
                "time": ["2026-07-26T12:00", "2026-08-09T23:00"],
                "temperature_2m": [29.4, null],
                "weather_code": [2, null],
                "precipitation_probability": [25, null]
              }
            }
            """.trimIndent()
        )

        val hourly = requireNotNull(response.hourly)
        assertEquals("2026-07-26T12:00", hourly.time.first())
        assertEquals(29.4, requireNotNull(hourly.temperature.first()), 0.0)
        assertEquals(2, hourly.weatherCode.first())
        assertEquals(25, hourly.precipitationProbability.first())
        assertNull(hourly.temperature.last())
        assertNull(hourly.weatherCode.last())
        assertNull(hourly.precipitationProbability.last())
    }
}
