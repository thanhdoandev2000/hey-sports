package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.heysports.ui.theme.size_1dp
import com.example.heysports.ui.theme.size_34dp

@Composable
internal fun StatDivider() {
    Box(
        modifier = Modifier
            .height(size_34dp)
            .width(size_1dp)
            .background(Color.White.copy(alpha = 0.5f))
    )
}
