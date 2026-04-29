package com.example.heysports.cores.extensions

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle

fun DrawScope.drawFieldBackground() {
    drawRect(color = Color(0xFF1A3A1C))

    val stripeWidth = size.width / 10f
    for (i in 0 .. 9) {
        val x = i * stripeWidth
        drawRect(
            color = if (i % 2 == 0) Color(0x0AFFFFFF) else Color.Transparent,
            topLeft = Offset(x, 0f),
            size = androidx.compose.ui.geometry.Size(stripeWidth, size.height)
        )
    }

    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0x30A5D6A7), Color.Transparent),
            center = Offset(size.width / 2f, size.height * 0.28f),
            radius = size.width * 0.65f
        )
    )
}

fun TextStyle.getTextStyleWithoutSpace()= copy(
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.Both
    )
)