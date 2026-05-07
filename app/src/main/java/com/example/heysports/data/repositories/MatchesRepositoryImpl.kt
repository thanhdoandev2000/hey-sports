package com.example.heysports.data.repositories

import com.example.heysports.data.models.dto.MatchRequestDto
import com.example.heysports.data.models.dto.UpcomingMatchDto
import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.data.sources.remote.DatabaseDataSource
import com.example.heysports.domain.repositories.MatchesRepository
import jakarta.inject.Inject

class MatchesRepositoryImpl @Inject constructor(
    private val db: DatabaseDataSource
) : MatchesRepository {

    override suspend fun getUpcomingMatches(): NetworkResult<List<UpcomingMatchDto>> {
        return db.getUpcomingMatches()
    }

    override suspend fun getMatchRequests(): NetworkResult<List<MatchRequestDto>> {
        return db.getMatchRequests()
    }
}

