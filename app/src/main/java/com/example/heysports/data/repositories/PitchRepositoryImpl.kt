package com.example.heysports.data.repositories

import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.data.sources.remote.DatabaseDataSource
import com.example.heysports.domain.models.PitchSelectionModel
import com.example.heysports.domain.repositories.PitchRepository
import javax.inject.Inject

class PitchRepositoryImpl @Inject constructor(
    private val db: DatabaseDataSource
) : PitchRepository {
    override suspend fun getPitches(search: String): NetworkResult<List<PitchSelectionModel>> {
        return db.getPitches(search)
    }
}