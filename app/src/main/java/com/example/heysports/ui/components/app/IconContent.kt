package com.example.heysports.ui.components.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.TextPrimary
import com.example.heysports.ui.theme.size_4dp

@Composable
fun IconTextRow(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    iconTint: Color = Color.Gray,
    textColor: Color = TextPrimary,
    iconSize: Dp = Dp.Unspecified,
    fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size_4dp)
    ) {
        JPIcon(
            icon = icon,
            tint = iconTint,
            size = iconSize
        )
        JPText(
            text = text,
            color = textColor,
            fontSize = fontSize,
            style = textStyle,
            maxLines = 1
        )
    }
}