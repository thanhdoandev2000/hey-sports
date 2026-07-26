package com.example.heysports.data.sources.remote

import com.example.heysports.data.models.dto.LiveMatchDto
import com.example.heysports.data.models.dto.MatchApplicationInsertDto
import com.example.heysports.data.models.dto.MatchRequestInsertDto
import com.example.heysports.data.models.dto.MatchRequestDto
import com.example.heysports.data.models.dto.MatchUpcomingDto
import com.example.heysports.data.models.dto.PitchDto
import com.example.heysports.data.models.dto.TeamOptionDto
import com.example.heysports.data.models.dto.toDomain
import com.example.heysports.data.models.response.NetworkResult
import com.example.heysports.data.networks.safeApiCall
import com.example.heysports.di.IoDispatcher
import com.example.heysports.domain.models.PitchSelectionModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.JsonNull
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
            db.from("match_requests_feed").select().decodeList<MatchRequestDto>()
        }
    }

    suspend fun getMatchRequest(id: Long): NetworkResult<MatchRequestDto> {
        return safeApiCall(ioDispatcher) {
            db.from("match_requests_details").select {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<MatchRequestDto>()
        }
    }

    suspend fun getMyTeams(): NetworkResult<List<TeamOptionDto>> {
        return safeApiCall(ioDispatcher) {
            db.from("my_teams").select().decodeList<TeamOptionDto>()
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

    suspend fun createMatchRequest(request: MatchRequestInsertDto): NetworkResult<Unit> {
        return safeApiCall(ioDispatcher) {
            db.from("match_requests").insert(request)
        }
    }

    suspend fun claimMatchRequest(
        request: MatchApplicationInsertDto
    ): NetworkResult<Unit> {
        return safeApiCall(ioDispatcher) {
            db.postgrest.rpc(
                function = "claim_match_request",
                parameters = buildJsonObject {
                    put("p_match_request_id", JsonPrimitive(request.matchRequestId))
                    put(
                        "p_applicant_team_id",
                        request.applicantTeamId?.let(::JsonPrimitive) ?: JsonNull
                    )
                    put(
                        "p_message",
                        request.message?.let(::JsonPrimitive) ?: JsonNull
                    )
                    put(
                        "p_contact_phone",
                        request.contactPhone?.let(::JsonPrimitive) ?: JsonNull
                    )
                }
            )
            Unit
        }
    }

    fun currentUserId(): String? = db.auth.currentUserOrNull()?.id
}
