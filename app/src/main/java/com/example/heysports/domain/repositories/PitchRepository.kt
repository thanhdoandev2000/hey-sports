package com.example.heysports.domain.repositories

import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.domain.models.PitchSelectionModel

interface PitchRepository {

    suspend fun getPitches(search: String): NetworkResult<List<PitchSelectionModel>>
}