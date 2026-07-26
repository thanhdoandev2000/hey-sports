package com.example.heysports.cores.utils

import com.example.heysports.cores.utils.DateTimeUtils.toServerDateTime
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class DateTimeUtilsTest {
    private lateinit var originalTimeZone: TimeZone

    @Before
    fun setUp() {
        originalTimeZone = TimeZone.getDefault()
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"))
    }

    @After
    fun tearDown() {
        TimeZone.setDefault(originalTimeZone)
    }

    @Test
    fun `UTC timestamp is displayed in device timezone`() {
        assertEquals(
            "19:00",
            DateTimeUtils.getTimeDisplay("2026-05-20T12:00:00+00:00")
        )
    }

    @Test
    fun `timestamp parser supports Supabase microseconds`() {
        assertEquals(
            "19:00",
            DateTimeUtils.getTimeDisplay("2026-05-20T12:00:00.123456+00:00")
        )
    }

    @Test
    fun `server timestamp includes calendar offset`() {
        val calendar = Calendar.getInstance(
            TimeZone.getTimeZone("Asia/Ho_Chi_Minh"),
            Locale.US
        ).apply {
            set(2026, Calendar.MAY, 20, 19, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }

        assertEquals("2026-05-20T19:00:00+07:00", calendar.toServerDateTime())
    }

    @Test
    fun `weather forecast hour is normalized to UTC`() {
        assertEquals(
            "2026-07-26T12:00",
            DateTimeUtils.getWeatherForecastHour("2026-07-26T19:00:00+07:00")
        )
    }

    @Test
    fun `match time must be later than current time`() {
        val now = Calendar.getInstance(
            TimeZone.getTimeZone("Asia/Ho_Chi_Minh"),
            Locale.US
        ).apply {
            set(2026, Calendar.JULY, 25, 18, 36, 0)
            set(Calendar.MILLISECOND, 0)
        }

        assertEquals(
            false,
            DateTimeUtils.isFutureMatchTime(
                "2026-07-25T18:00:00+07:00",
                now.timeInMillis
            )
        )
        assertEquals(
            true,
            DateTimeUtils.isFutureMatchTime(
                "2026-07-25T19:00:00+07:00",
                now.timeInMillis
            )
        )
    }

    @Test
    fun `created time is displayed relatively`() {
        val now = Calendar.getInstance(
            TimeZone.getTimeZone("Asia/Ho_Chi_Minh"),
            Locale.US
        ).apply {
            set(2026, Calendar.JULY, 26, 12, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        assertEquals(
            "vừa đăng",
            DateTimeUtils.getRelativeTimeDisplay("2026-07-26T11:59:30+07:00", now)
        )
        assertEquals(
            "5 phút trước",
            DateTimeUtils.getRelativeTimeDisplay("2026-07-26T11:55:00+07:00", now)
        )
        assertEquals(
            "2 giờ trước",
            DateTimeUtils.getRelativeTimeDisplay("2026-07-26T10:00:00+07:00", now)
        )
        assertEquals(
            "2 ngày trước",
            DateTimeUtils.getRelativeTimeDisplay("2026-07-24T12:00:00+07:00", now)
        )
    }
}
