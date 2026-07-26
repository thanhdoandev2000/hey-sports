package com.example.heysports.domain.repositories

import com.example.heysports.data.models.dto.LiveMatchDto
import com.example.heysports.data.models.dto.MatchApplicationInsertDto
import com.example.heysports.data.models.dto.MatchRequestInsertDto
import com.example.heysports.data.models.dto.MatchRequestDto
import com.example.heysports.data.models.dto.MatchUpcomingDto
import com.example.heysports.data.models.dto.TeamOptionDto
import com.example.heysports.data.models.response.NetworkResult

interface MatchesRepository {
    suspend fun getUpcomingMatches(): NetworkResult<List<MatchUpcomingDto>>

    suspend fun getMatchRequests(): NetworkResult<List<MatchRequestDto>>

    suspend fun getMatchRequest(id: Long): NetworkResult<MatchRequestDto>

    suspend fun getMyTeams(): NetworkResult<List<TeamOptionDto>>

    suspend fun getLiveMatches(): NetworkResult<List<LiveMatchDto>>

    suspend fun createMatchRequest(request: MatchRequestInsertDto): NetworkResult<Unit>

    suspend fun claimMatchRequest(request: MatchApplicationInsertDto): NetworkResult<Unit>

    fun currentUserId(): String?
}
