package com.example.heysports.data.sources.remote

import com.example.heysports.data.models.dto.LiveMatchDto
import com.example.heysports.data.models.dto.MatchRequestDto
import com.example.heysports.data.models.dto.MatchUpcomingDto
import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.data.networks.safeApiCall
import com.example.heysports.di.IoDispatcher
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DatabaseDataSource @Inject constructor(
    private val db: SupabaseClient,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getUpcomingMatches(): NetworkResult<List<MatchUpcomingDto>> {
        return safeApiCall(ioDispatcher) {
            db.from("get_upcoming_matches").select().decodeList<MatchUpcomingDto>()
        }
    }

    suspend fun getMatchRequests(): NetworkResult<List<MatchRequestDto>> {
        return safeApiCall {
            db.from("match_requests_details").select().decodeList<MatchRequestDto>()
        }
    }

    suspend fun getLiveMatches(): NetworkResult<List<LiveMatchDto>> {
        return safeApiCall {
            db.from("get_matches_live").select().decodeList<LiveMatchDto>()
        }
    }
}