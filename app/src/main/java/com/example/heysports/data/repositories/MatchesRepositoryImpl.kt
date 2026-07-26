package com.example.heysports.data.repositories

import com.example.heysports.data.models.dto.LiveMatchDto
import com.example.heysports.data.models.dto.MatchApplicationInsertDto
import com.example.heysports.data.models.dto.MatchRequestInsertDto
import com.example.heysports.data.models.dto.MatchRequestDto
import com.example.heysports.data.models.dto.MatchUpcomingDto
import com.example.heysports.data.models.dto.TeamOptionDto
import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.data.sources.remote.DatabaseDataSource
import com.example.heysports.domain.repositories.MatchesRepository
import jakarta.inject.Inject

class MatchesRepositoryImpl @Inject constructor(
    private val db: DatabaseDataSource
) : MatchesRepository {

    override suspend fun getUpcomingMatches(): NetworkResult<List<MatchUpcomingDto>> {
        return db.getUpcomingMatches()
    }

    override suspend fun getMatchRequests(): NetworkResult<List<MatchRequestDto>> {
        return db.getMatchRequests()
    }

    override suspend fun getMatchRequest(id: Long): NetworkResult<MatchRequestDto> {
        return db.getMatchRequest(id)
    }

    override suspend fun getMyTeams(): NetworkResult<List<TeamOptionDto>> {
        return db.getMyTeams()
    }

    override suspend fun getLiveMatches(): NetworkResult<List<LiveMatchDto>> {
        return db.getLiveMatches()
    }

    override suspend fun createMatchRequest(request: MatchRequestInsertDto): NetworkResult<Unit> {
        return db.createMatchRequest(request)
    }

    override suspend fun claimMatchRequest(
        request: MatchApplicationInsertDto
    ): NetworkResult<Unit> {
        return db.claimMatchRequest(request)
    }

    override fun currentUserId(): String? = db.currentUserId()
}
