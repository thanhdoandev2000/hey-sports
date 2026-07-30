package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.size_12sp
import com.example.heysports.ui.theme.size_20sp

@Composable
internal fun StatItem(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        JPText(
            text = value,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = size_20sp
        )
        JPText(
            text = label,
            color = Color.White.copy(alpha = 0.82f),
            fontSize = size_12sp
        )
    }
}
