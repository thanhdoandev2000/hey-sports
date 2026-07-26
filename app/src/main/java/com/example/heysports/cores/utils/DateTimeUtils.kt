package com.example.heysports.cores.utils

import com.example.heysports.cores.extensions.castTo
import com.example.heysports.data.models.enums.ETimeType
import java.text.SimpleDateFormat
import java.text.ParsePosition
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateTimeUtils {
    const val DATE_TIME_SERVER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    private const val DATE_TIME_OFFSET_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX"
    const val TIME_DISPLAY = "HH:mm"

    const val DATE_DISPLAY = "dd/MM"
    const val DATE_DISPLAY_FULL = "dd/MM/yyyy"

    fun getCurrentDate(): Calendar {
        return Calendar.getInstance(Locale.US)
    }

    internal fun isToday(dateTime: String): Boolean {
        return try {
            val date = parseServerDate(dateTime) ?: return false

            val input = Calendar.getInstance().apply { time = date }
            val now = getCurrentDate()

            input.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                    input.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)
        } catch (_: Exception) {
            false
        }
    }

    fun parseMatchTime(matchTime: String): Date? {
        return parseServerDate(matchTime)
    }

    internal fun isFutureMatchTime(
        matchTime: String?,
        nowMillis: Long = System.currentTimeMillis()
    ): Boolean {
        if (matchTime.isNullOrBlank()) return false
        return parseServerDate(matchTime)?.time?.let { it > nowMillis } == true
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
            val date = parseServerDate(serverTime) ?: return ""
            SimpleDateFormat(pattern, Locale.US).format(date)
        } catch (_: Exception) {
            ""
        }
    }

    internal fun getDateTimeDisplay(dateTime: String?): String {
        return try {
            if (dateTime.isNullOrEmpty()) return ""
            val date = parseServerDate(dateTime) ?: return ""

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

    internal fun isSameDay(a: Calendar, b: Calendar) =
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

    internal fun getDateDisplay(dateTime: String?): String {
        return try {
            if (dateTime.isNullOrEmpty()) return ""
            val date = parseServerDate(dateTime) ?: return ""

            val input = Calendar.getInstance().apply { time = date }
            val now = getCurrentDate()

            when {
                isSameDay(input, now) -> "Hôm nay"
                isYesterday(input, now) -> "Hôm qua"
                isTomorrow(input, now) -> "Ngày mai"
                else -> SimpleDateFormat(DATE_DISPLAY_FULL, Locale.US).format(date)
            }
        } catch (_: Exception) {
            ""
        }
    }

    internal fun getTimeDisplay(dateTime: String?): String {
        return try {
            if (dateTime.isNullOrEmpty()) return ""
            val date = parseServerDate(dateTime) ?: return ""
            SimpleDateFormat(TIME_DISPLAY, Locale.US).format(date)
        } catch (_: Exception) {
            ""
        }
    }

    internal fun Calendar.toDow(): String =
        when (get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "T2"
            Calendar.TUESDAY -> "T3"
            Calendar.WEDNESDAY -> "T4"
            Calendar.THURSDAY -> "T5"
            Calendar.FRIDAY -> "T6"
            Calendar.SATURDAY -> "T7"
            else -> "CN"
        }

    internal fun Calendar.isHost(): Boolean = when (get(Calendar.DAY_OF_WEEK)) {
        Calendar.SATURDAY,
        Calendar.SUNDAY -> true

        else -> false
    }

    internal fun Calendar.toDom(): String = "%02d".format(get(Calendar.DAY_OF_MONTH))
    internal fun Calendar.toMon(): String = "Th${get(Calendar.MONTH) + 1}"
    internal fun Calendar.toSummaryDate(): String =
        "${toDow()}, ${toDom()}/${get(Calendar.MONTH) + 1}"

    internal fun Calendar.addDays(days: Int): Calendar =
        (this.clone() as Calendar).also { it.add(Calendar.DAY_OF_YEAR, days) }

    internal fun Calendar.toServerDateTime(): String {
        return runCatching {
            SimpleDateFormat(DATE_TIME_OFFSET_FORMAT, Locale.US).apply {
                timeZone = this@toServerDateTime.timeZone
            }.format(this.time)
        }.getOrDefault("")
    }

    internal fun convertMatchTimeString(dateTime: String?): String? {
      return  runCatching {
            if (dateTime.isNullOrEmpty()) return ""
            val date = parseServerDate(dateTime) ?: return ""
            val input = Calendar.getInstance().apply { time = date }

            val timeStr = SimpleDateFormat(TIME_DISPLAY, Locale.US).format(date)
            val dateStr = SimpleDateFormat(DATE_DISPLAY, Locale.US).format(date)
            val dateOfWeek = input.toDow()
            "$timeStr - $dateOfWeek, $dateStr"
        }.getOrDefault(null)
    }

    internal fun String?.convertToCalendar(pattern: String? = DATE_TIME_SERVER_FORMAT): Calendar {
        if (isNullOrEmpty()) return getCurrentDate()
        val date = if (pattern == DATE_TIME_SERVER_FORMAT) {
            parseServerDate(this)
        } else {
            SimpleDateFormat(pattern, Locale.US).parse(this)
        } ?: return getCurrentDate()
        val input = Calendar.getInstance().apply { time = date }
        return input
    }

    internal fun getTicketDateDisplay(dateTime: String?): String {
        if (dateTime.isNullOrBlank()) return ""
        val date = parseServerDate(dateTime) ?: return ""
        val calendar = Calendar.getInstance().apply { time = date }
        return "${calendar.toDow()}, ${SimpleDateFormat(DATE_DISPLAY, Locale.US).format(date)}"
    }

    internal fun getRelativeTimeDisplay(
        dateTime: String?,
        nowMillis: Long = System.currentTimeMillis()
    ): String {
        if (dateTime.isNullOrBlank()) return ""
        val createdAtMillis = parseServerDate(dateTime)?.time ?: return ""
        val elapsedMillis = (nowMillis - createdAtMillis).coerceAtLeast(0L)
        val elapsedMinutes = elapsedMillis / 60_000L

        return when {
            elapsedMinutes < 1L -> "vừa đăng"
            elapsedMinutes < 60L -> "$elapsedMinutes phút trước"
            elapsedMinutes < 1_440L -> "${elapsedMinutes / 60L} giờ trước"
            elapsedMinutes < 10_080L -> "${elapsedMinutes / 1_440L} ngày trước"
            else -> SimpleDateFormat(DATE_DISPLAY_FULL, Locale.US)
                .format(Date(createdAtMillis))
        }
    }

    internal fun getWeatherForecastHour(dateTime: String): String {
        val date = parseServerDate(dateTime) ?: return ""
        return SimpleDateFormat("yyyy-MM-dd'T'HH:00", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.format(date)
    }

    private fun parseServerDate(value: String): Date? {
        val normalizedValue = value.replace(
            Regex("""(\.\d{3})\d+([Zz]|[+-]\d{2}:\d{2})$"""),
            "$1$2"
        )
        val patterns = listOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            DATE_TIME_OFFSET_FORMAT,
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            DATE_TIME_SERVER_FORMAT
        )

        return patterns.firstNotNullOfOrNull { pattern ->
            val formatter = SimpleDateFormat(pattern, Locale.US).apply {
                isLenient = false
                if (pattern.endsWith("'Z'")) {
                    timeZone = java.util.TimeZone.getTimeZone("UTC")
                }
            }
            val position = ParsePosition(0)
            formatter.parse(normalizedValue, position)
                ?.takeIf { position.index == normalizedValue.length }
        }
    }
}
