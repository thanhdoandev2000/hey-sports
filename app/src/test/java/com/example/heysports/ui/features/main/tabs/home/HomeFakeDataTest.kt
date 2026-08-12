package com.example.heysports.ui.features.main.tabs.home

import com.example.heysports.cores.utils.DateTimeUtils
import com.example.heysports.data.models.dto.LiveMatchDto
import com.example.heysports.data.models.dto.MatchRequestDto
import com.example.heysports.data.models.dto.MatchUpcomingDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeFakeDataTest {
    @Test
    fun `fallback fills every Home content section`() {
        val state = HomeFakeData.initialState()

        assertTrue(state.personInfo?.name?.isNotBlank() == true)
        assertTrue(state.upComingMatches.isNotEmpty())
        assertTrue(state.matchRequests.isNotEmpty())
        assertTrue(state.liveMatches.isNotEmpty())
        assertTrue(state.newsFeeds.isNotEmpty())
        assertEquals(
            state.upComingMatches.map { it.id }.toSet(),
            state.weatherByMatchId.keys
        )
        assertTrue(
            state.upComingMatches.all { DateTimeUtils.isFutureMatchTime(it.matchTime) }
        )
    }

    @Test
    fun `server content always wins over fallback`() {
        val upcoming = listOf(serverUpcoming())
        val requests = listOf(serverRequest())
        val live = listOf(serverLive())

        assertSame(upcoming, HomeFakeData.upcomingOrFallback(upcoming))
        assertSame(requests, HomeFakeData.requestsOrFallback(requests))
        assertSame(live, HomeFakeData.liveOrFallback(live))
    }

    @Test
    fun `empty server content uses fallback`() {
        assertTrue(HomeFakeData.upcomingOrFallback(emptyList()).isNotEmpty())
        assertTrue(HomeFakeData.requestsOrFallback(emptyList()).isNotEmpty())
        assertTrue(HomeFakeData.liveOrFallback(emptyList()).isNotEmpty())
    }

    @Test
    fun `match request fallback covers every supported feed case`() {
        assertEquals(
            setOf("FIND_OPPONENT", "FIND_PLAYER", "REQUEST_SLOT", "RECRUITING_SLOT"),
            HomeFakeData.matchRequests().map { it.type }.toSet()
        )
    }

    private fun serverUpcoming() = MatchUpcomingDto(
        id = 99L,
        hostTeamId = null,
        guestTeamId = null,
        subPitchId = null,
        matchTime = "2099-01-01T18:00:00+07:00",
        duration = 90,
        description = null,
        hostTeamName = "Server A",
        hostTeamAvatar = null,
        guestTeamName = "Server B",
        guestTeamAvatar = null,
        subPitchName = null,
        price = null,
        type = null,
        pitchName = "Server pitch",
        pitchAddress = "Server address"
    )

    private fun serverRequest() = MatchRequestDto(
        id = 99L,
        createdAt = "2026-08-01T10:00:00+07:00",
        postedByType = "USER",
        type = "FIND_OPPONENT"
    )

    private fun serverLive() = LiveMatchDto(
        id = 99L,
        updateAt = "2026-08-01T10:00:00+07:00",
        status = "live",
        currentMinutes = 10,
        half = 1,
        duration = 90,
        hostScore = 0,
        guestScore = 0,
        startTime = "2026-08-01T09:50:00+07:00",
        hostTeamId = null,
        hostTeamName = "Server A",
        guestTeamId = null,
        guestTeamName = "Server B",
        subPitchId = null,
        subPitchName = null,
        subPitchType = null,
        pitchID = null,
        pitchName = null,
        pitchAddress = null
    )
}
