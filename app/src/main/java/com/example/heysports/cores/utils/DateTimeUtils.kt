package com.example.heysports.cores.utils

import com.example.heysports.data.models.enums.ETimeType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateTimeUtils {
    const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    const val TIME_DISPLAY = "HH:mm"

    const val DATE_DISPLAY = "dd/MM"

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
}