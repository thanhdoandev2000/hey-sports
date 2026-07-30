package com.example.heysports.ui.features.main.tabs.maps.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Stadium
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.theme.GreenDark
import com.example.heysports.ui.theme.HeySportsTheme
import com.example.heysports.ui.theme.PrimaryGreen
import com.example.heysports.ui.theme.size_20dp

@Composable
internal fun PitchMapMarker(
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val width = if (selected) 54.dp else 42.dp
    val height = if (selected) 62.dp else 50.dp
    val markerColor = if (selected) GreenDark else PrimaryGreen

    Box(
        modifier = modifier.size(width = width, height = height),
        contentAlignment = Alignment.TopCenter
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val radius = size.width * 0.43f
            val center = Offset(size.width / 2f, radius + 2.dp.toPx())
            val pointer = Path().apply {
                moveTo(center.x - radius * 0.42f, center.y + radius * 0.64f)
                lineTo(center.x, size.height)
                lineTo(center.x + radius * 0.42f, center.y + radius * 0.64f)
                close()
            }

            drawPath(path = pointer, color = markerColor)
            drawCircle(
                color = markerColor,
                radius = radius,
                center = center
            )
            drawCircle(
                color = Color.White,
                radius = radius * 0.68f,
                center = center
            )
            drawCircle(
                color = markerColor.copy(alpha = 0.18f),
                radius = radius * 0.62f,
                center = center
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.95f),
                radius = radius,
                center = center,
                style = Stroke(width = if (selected) 3.dp.toPx() else 2.dp.toPx())
            )
        }

        JPIcon(
            icon = Icons.Outlined.Stadium,
            tint = markerColor,
            size = if (selected) 24.dp else size_20dp,
            modifier = Modifier.offset(y = if (selected) 13.dp else 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PitchMapMarkerPreview() {
    HeySportsTheme {
        PitchMapMarker(selected = true)
    }
}
