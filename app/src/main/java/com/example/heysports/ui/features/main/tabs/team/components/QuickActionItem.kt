package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@Composable
internal fun QuickActionItem(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    hasBadge: Boolean = false
) {
    Surface(
        modifier = modifier
            .height(82.dp)
            .clickable { },
        color = Color.White,
        shape = RoundedCornerShape(size_8dp),
        border = BorderStroke(size_line, DividerColor),
        shadowElevation = size_1dp
    ) {
        Column(
            modifier = Modifier.padding(vertical = size_10dp, horizontal = size_4dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box {
                JPIcon(
                    icon = icon,
                    tint = PrimaryGreen,
                    size = size_30dp
                )
                if (hasBadge) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 4.dp, y = (- 4).dp)
                            .size(size_8dp)
                            .background(Color.Red, CircleShape)
                    )
                }
            }
            JPSpacer(height = size_6dp)
            JPText(
                text = label,
                fontSize = size_12sp,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}
