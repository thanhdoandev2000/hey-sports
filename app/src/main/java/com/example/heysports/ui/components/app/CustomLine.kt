package com.example.heysports.ui.components.app

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.heysports.ui.theme.size_1dp

@Composable
fun CustomLine(
    color: Color = Color.LightGray,
    height: Dp = size_1dp
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = 4f
        )
    }
}