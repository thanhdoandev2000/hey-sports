package com.example.heysports.domain.repositories

import com.example.heysports.data.models.dto.LiveMatchDto
import com.example.heysports.data.models.dto.MatchRequestDto
import com.example.heysports.data.models.dto.MatchUpcomingDto
import com.example.heysports.data.models.response.NetworkResult

interface MatchesRepository {
    suspend fun getUpcomingMatches(): NetworkResult<List<MatchUpcomingDto>>

    suspend fun getMatchRequests(): NetworkResult<List<MatchRequestDto>>

    suspend fun getLiveMatches(): NetworkResult<List<LiveMatchDto>>
}