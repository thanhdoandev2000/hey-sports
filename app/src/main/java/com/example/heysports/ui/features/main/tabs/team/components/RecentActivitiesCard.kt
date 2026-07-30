package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.ui.theme.DividerColor
import com.example.heysports.ui.theme.size_8dp
import com.example.heysports.ui.theme.size_line

@Composable
internal fun RecentActivitiesCard() {
    val activities = listOf(
        ActivityData(
            title = stringResource(R.string.team_recent_join_title),
            subtitle = stringResource(R.string.team_recent_join_time),
            icon = Icons.Default.PersonAdd,
            showUnreadDot = true
        ),
        ActivityData(
            title = stringResource(R.string.team_recent_match_title),
            subtitle = stringResource(R.string.team_recent_match_time),
            icon = Icons.Default.CalendarMonth,
            showChevron = true
        )
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(size_8dp),
        border = BorderStroke(size_line, DividerColor)
    ) {
        Column {
            activities.forEachIndexed { index, activity ->
                ActivityItem(activity = activity)
                if (index < activities.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 64.dp),
                        thickness = size_line,
                        color = DividerColor
                    )
                }
            }
        }
    }
}
