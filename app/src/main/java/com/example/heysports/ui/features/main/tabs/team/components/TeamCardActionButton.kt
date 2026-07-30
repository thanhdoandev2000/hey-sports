package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@Composable
internal fun TeamCardActionButton(
    label: String,
    icon: ImageVector,
    filled: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(size_48dp)
            .clickable { },
        color = if (filled) Color.White else Color.Transparent,
        contentColor = if (filled) PrimaryGreen else Color.White,
        shape = RoundedCornerShape(size_6dp),
        border = BorderStroke(
            width = size_1dp,
            color = Color.White.copy(alpha = 0.9f)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            JPIcon(
                icon = icon,
                tint = if (filled) PrimaryGreen else Color.White,
                size = size_20dp
            )
            JPSpacer(width = size_6dp)
            JPText(
                text = label,
                color = if (filled) PrimaryGreen else Color.White,
                fontSize = size_14sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
        }
    }
}
