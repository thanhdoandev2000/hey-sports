package com.example.heysports.ui.components.cores

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun JPSpacer(height: Dp = 0.dp, width: Dp = 0.dp) {
    Spacer(
        Modifier
            .height(height)
            .width(width)
    )
}