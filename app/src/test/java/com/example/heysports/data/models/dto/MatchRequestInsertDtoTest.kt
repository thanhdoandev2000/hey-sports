package com.example.heysports.data.models.dto

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Test

class MatchRequestInsertDtoTest {
    @Test
    fun `required post ownership fields are encoded`() {
        val payload = Json.encodeToString(
            MatchRequestInsertDto(
                userId = "dcfc4d76-6b4e-47fc-a8af-44be52f658d1",
                postedByType = "PLAYER",
                type = "FIND_OPPONENT",
                matchTime = "2026-07-26T19:00:00+07:00",
                pitchId = 1,
                matchFormat = "5 vs 5",
                skillLevel = "Trung Bình - Khá",
                feeType = "FIFTY_FIFTY",
                ageGroup = "U18",
                teamStyle = "CASUAL",
                teamStatus = "FULL_SQUAD",
                rules = listOf("FRIENDLY_MATCH", "PUNCTUAL"),
                moreNotes = "Đến sân trước 15 phút",
                photoUrls = listOf("https://example.com/match.jpg")
            )
        )

        assertTrue(payload.contains("\"posted_by_type\":\"PLAYER\""))
        assertTrue(payload.contains("\"type\":\"FIND_OPPONENT\""))
        assertTrue(payload.contains("\"fee_type\":\"FIFTY_FIFTY\""))
        assertTrue(payload.contains("\"age_group\":\"U18\""))
        assertTrue(payload.contains("\"team_style\":\"CASUAL\""))
        assertTrue(payload.contains("\"team_status\":\"FULL_SQUAD\""))
        assertTrue(payload.contains("\"rules\":[\"FRIENDLY_MATCH\",\"PUNCTUAL\"]"))
        assertTrue(payload.contains("\"more_notes\":\"Đến sân trước 15 phút\""))
        assertTrue(payload.contains("\"photo_urls\":[\"https://example.com/match.jpg\"]"))
    }
}
