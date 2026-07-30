package com.example.heysports.ui.features.main.tabs.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@Composable
internal fun OverviewStat(
    modifier: Modifier,
    icon: ImageVector,
    value: String,
    label: String,
    iconColor: Color = PrimaryGreen,
    showChevron: Boolean = false
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(size_3dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(size_32dp)
            )
            JPText(
                text = value,
                color = PrimaryGreen,
                fontSize = size_28sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
            JPText(
                text = label,
                color = TextSecondary,
                fontSize = size_16sp,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
        if (showChevron) {
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = TextPrimary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(size_24dp)
            )
        }
    }
}
