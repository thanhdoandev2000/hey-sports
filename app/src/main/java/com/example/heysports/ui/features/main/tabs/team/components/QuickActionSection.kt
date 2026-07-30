package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.heysports.R
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.theme.*

@Composable
internal fun QuickActionSection() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(size_10dp),
        border = BorderStroke(size_line, DividerColor)
    ) {
        Column(modifier = Modifier.padding(size_12dp)) {
            SectionHeader(title = stringResource(R.string.team_quick_actions))
            JPSpacer(height = size_10dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(size_8dp)
            ) {
                QuickActionItem(
                    icon = Icons.Default.Groups,
                    label = stringResource(R.string.team_lineup),
                    modifier = Modifier.weight(1f)
                )
                QuickActionItem(
                    icon = Icons.Default.CalendarMonth,
                    label = stringResource(R.string.team_schedule),
                    modifier = Modifier.weight(1f)
                )
                QuickActionItem(
                    icon = Icons.AutoMirrored.Filled.Assignment,
                    label = stringResource(R.string.team_requests),
                    modifier = Modifier.weight(1f),
                    hasBadge = true
                )
                QuickActionItem(
                    icon = Icons.Default.BarChart,
                    label = stringResource(R.string.team_stats),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
