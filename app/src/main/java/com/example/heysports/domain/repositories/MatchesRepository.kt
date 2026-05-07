package com.example.heysports.domain.repositories

import com.example.heysports.data.models.dto.MatchRequestDto
import com.example.heysports.data.models.dto.UpcomingMatchDto
import com.example.heysports.data.models.response.NetworkResult

interface MatchesRepository {
    suspend fun getUpcomingMatches(): NetworkResult<List<UpcomingMatchDto>>

    suspend fun getMatchRequests(): NetworkResult<List<MatchRequestDto>>
}