package com.example.heysports.data.models.enums

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.heysports.R
import com.example.heysports.ui.theme.PrimaryGreen

enum class EMatchStatus(
    val status: String,
    val color: Color,
    val bgColor: Color = Color(0xFF9E9E9E),
    @param:StringRes val label: Int,
    @param:StringRes val btnLabel: Int = R.string.empty
) {
    UPCOMING(
        status = "upcoming",
        color = Color(0xFFFFA000),
        label = R.string.statusUpcoming,
        btnLabel = R.string.homeBtnViewCalendar
    ),
    LIVE(
        status = "live",
        color = Color.Red,
        label = R.string.statusLive,
        btnLabel = R.string.homeViewLive,
        bgColor = PrimaryGreen
    ),
    HALFTIME(
        status = "halftime",
        color = Color.Red,
        label = R.string.statusHalftime,
        btnLabel = R.string.homeViewLive,
        bgColor = PrimaryGreen
    ),
    FINISHED(
        status = "finished",
        color = PrimaryGreen,
        label = R.string.statusCompleted,
        btnLabel = R.string.homeViewDetail
    ),
    POSTPONED(
        status = "postponed",
        color = Color(0xFFD81B60),
        label = R.string.statusPostponed,
        btnLabel = R.string.homeViewDetail
    ),
    CANCELED(
        status = "canceled",
        color = Color(0xFF000000),
        label = R.string.statusCancelled,
        btnLabel = R.string.homeViewDetail
    );

    companion object {
        fun fromString(status: String?): EMatchStatus {
            return entries.find { it.status.equals(status, ignoreCase = true) } ?: UPCOMING
        }
    }
}