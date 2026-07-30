package com.example.heysports.ui.features.main.tabs.profile.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.heysports.ui.theme.size_2dp

@Composable
internal fun FootballFieldPattern() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val strokeWidth = size_2dp.toPx()
        val lineColor = Color.White.copy(alpha = 0.07f)
        drawLine(
            color = lineColor,
            start = Offset(size.width * 0.58f, 0f),
            end = Offset(size.width * 0.7f, size.height),
            strokeWidth = strokeWidth
        )
        drawCircle(
            color = lineColor,
            radius = size.minDimension * 0.15f,
            center = Offset(size.width * 0.62f, size.height * 0.45f),
            style = Stroke(strokeWidth)
        )
        drawLine(
            color = lineColor,
            start = Offset(0f, size.height * 0.58f),
            end = Offset(size.width, size.height * 0.08f),
            strokeWidth = strokeWidth
        )
        drawRect(
            color = lineColor,
            topLeft = Offset(size.width * 0.88f, size.height * 0.15f),
            size = androidx.compose.ui.geometry.Size(size.width * 0.2f, size.height * 0.45f),
            style = Stroke(strokeWidth)
        )
    }
}
