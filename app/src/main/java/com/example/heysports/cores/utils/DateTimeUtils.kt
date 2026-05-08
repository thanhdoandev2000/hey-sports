package com.example.heysports.cores.utils

import com.example.heysports.cores.extensions.castTo
import com.example.heysports.data.models.enums.ETimeType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateTimeUtils {
    const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    const val TIME_DISPLAY = "HH:mm"

    const val DATE_DISPLAY = "dd/MM"
    const val DATE_DISPLAY_FULL = "dd/MM/yyyy"

    fun getCurrentDate(): Calendar {
        return Calendar.getInstance(Locale.US)
    }

    internal fun isToday(dateTime: String): Boolean {
        return try {
            val sdf = SimpleDateFormat(DATE_TIME_FORMAT, Locale.US)
            val date = sdf.parse(dateTime) ?: return false

            val input = Calendar.getInstance().apply { time = date }
            val now = getCurrentDate()

            input.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                    input.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)
        } catch (_: Exception) {
            false
        }
    }

    fun parseMatchTime(matchTime: String): Date? {
        return try {
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.US).parse(matchTime)
        } catch (_: Exception) {
            null
        }
    }

    fun getTimerValue(diff: Long, type: ETimeType): String {
        return when (type) {
            ETimeType.DATE -> String.format(Locale.US, "%02d", diff / 86400000)
            ETimeType.TIME -> String.format(Locale.US, "%02d", (diff % 86400000) / 3600000)
            ETimeType.MINUTE -> String.format(Locale.US, "%02d", (diff % 3600000) / 60000)
            ETimeType.SECOND -> String.format(Locale.US, "%02d", (diff % 60000) / 1000)
        }
    }

    fun convertServerTimeToDisplayTime(serverTime: String, pattern: String = TIME_DISPLAY): String {
        return try {
            val sdf = SimpleDateFormat(DATE_TIME_FORMAT, Locale.US)
            val date = sdf.parse(serverTime) ?: return ""
            SimpleDateFormat(pattern, Locale.US).format(date)
        } catch (_: Exception) {
            ""
        }
    }

    internal fun getDateTimeDisplay(dateTime: String?): String {
        return try {
            if (dateTime.isNullOrEmpty()) return ""
            val sdf = SimpleDateFormat(DATE_TIME_FORMAT, Locale.US)
            val date = sdf.parse(dateTime) ?: return ""

            val input = Calendar.getInstance().apply { time = date }
            val now = getCurrentDate()

            val timeStr = SimpleDateFormat(TIME_DISPLAY, Locale.US).format(date)

            val label = when {
                isSameDay(input, now) -> "Hôm nay"
                isYesterday(input, now) -> "Hôm qua"
                isTomorrow(input, now) -> "Ngày mai"
                else -> SimpleDateFormat(DATE_DISPLAY_FULL, Locale.US).format(date)
            }

            "$timeStr - $label"
        } catch (_: Exception) {
            ""
        }
    }

    private fun isSameDay(a: Calendar, b: Calendar) =
        a.get(Calendar.YEAR) == b.get(Calendar.YEAR) &&
                a.get(Calendar.DAY_OF_YEAR) == b.get(Calendar.DAY_OF_YEAR)

    private fun isYesterday(input: Calendar, now: Calendar): Boolean {
        val yesterday = (now.clone().castTo<Calendar>())?.apply { add(Calendar.DAY_OF_YEAR, - 1) }
        return yesterday != null && isSameDay(input, yesterday)
    }

    private fun isTomorrow(input: Calendar, now: Calendar): Boolean {
        val tomorrow = (now.clone().castTo<Calendar>())?.apply { add(Calendar.DAY_OF_YEAR, 1) }
        return tomorrow != null && isSameDay(input, tomorrow)
    }
}