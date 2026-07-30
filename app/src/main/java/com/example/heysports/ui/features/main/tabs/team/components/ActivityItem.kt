package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@Composable
internal fun ActivityItem(activity: ActivityData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = size_12dp, vertical = size_10dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(size_40dp),
            color = LightGreenBackground,
            shape = CircleShape
        ) {
            Box(contentAlignment = Alignment.Center) {
                JPIcon(
                    icon = activity.icon,
                    tint = PrimaryGreen,
                    size = size_24dp
                )
            }
        }
        JPSpacer(width = size_12dp)
        Column(modifier = Modifier.weight(1f)) {
            JPText(
                text = activity.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = size_13sp,
                maxLines = 1
            )
            JPText(
                text = activity.subtitle,
                fontSize = size_11sp,
                color = TextSecondary,
                maxLines = 1
            )
        }
        when {
            activity.showUnreadDot -> Box(
                modifier = Modifier
                    .size(size_8dp)
                    .background(PrimaryGreen, CircleShape)
            )

            activity.showChevron -> JPIcon(
                icon = Icons.Default.ChevronRight,
                tint = TextSecondary,
                size = size_20dp
            )
        }
    }
}
