package com.example.heysports.data.sources.remote

import com.example.heysports.data.models.dto.LiveMatchDto
import com.example.heysports.data.models.dto.MatchRequestDto
import com.example.heysports.data.models.dto.MatchUpcomingDto
import com.example.heysports.data.models.dto.PitchDto
import com.example.heysports.data.models.dto.toDomain
import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.data.networks.safeApiCall
import com.example.heysports.di.IoDispatcher
import com.example.heysports.domain.models.PitchSelectionModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
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

    suspend fun getPitches(search: String): NetworkResult<List<PitchSelectionModel>> {
        return safeApiCall {
            db.postgrest.rpc(
                function = "get_pitches",
                parameters = buildJsonObject {
                    put(
                        "search",
                        JsonPrimitive(search)
                    )
                }
            ).decodeList<PitchDto>().map(PitchDto::toDomain)
        }
    }
}