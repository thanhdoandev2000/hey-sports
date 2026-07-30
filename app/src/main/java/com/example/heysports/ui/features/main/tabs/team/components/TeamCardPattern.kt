package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
internal fun TeamCardPattern(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val patternColor = Color.White.copy(alpha = 0.075f)
        val strokeWidth = 1.dp.toPx()

        drawLine(
            color = patternColor,
            start = Offset(size.width * 0.66f, 0f),
            end = Offset(size.width, size.height * 0.58f),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = patternColor,
            start = Offset(size.width * 0.78f, 0f),
            end = Offset(size.width, size.height * 0.37f),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = patternColor,
            start = Offset(size.width * 0.52f, size.height),
            end = Offset(size.width, size.height * 0.45f),
            strokeWidth = strokeWidth
        )
        drawCircle(
            color = patternColor,
            radius = size.height * 0.28f,
            center = Offset(size.width * 0.92f, size.height * 0.48f),
            style = Stroke(width = strokeWidth)
        )
    }
}
