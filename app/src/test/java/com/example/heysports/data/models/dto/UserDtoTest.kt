package com.example.heysports.data.models.dto

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class UserDtoTest {
    @Test
    fun `profile fields are mapped to domain user`() {
        val user = UserDto(
            id = "user-id",
            fullName = "Thanh Đoàn",
            email = "thanh@example.com",
            avatar = "https://example.com/avatar.jpg",
            matchesPlayed = 12,
            rating = 4.8,
            skillLevel = "STRONG",
            createdAt = "2025-02-10T08:30:00Z"
        ).toDomain()

        assertEquals("Thanh Đoàn", user.name)
        assertEquals(12, user.matchesPlayed)
        assertEquals(4.8, requireNotNull(user.rating), 0.0)
        assertEquals("STRONG", user.skillLevel)
        assertEquals("2025-02-10T08:30:00Z", user.createdAt)
    }

    @Test
    fun `non-positive rating is treated as unavailable`() {
        val user = UserDto(
            id = "user-id",
            rating = 0.0
        ).toDomain()

        assertNull(user.rating)
    }
}
