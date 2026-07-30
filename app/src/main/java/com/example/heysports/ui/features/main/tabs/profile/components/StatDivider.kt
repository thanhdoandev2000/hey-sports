package com.example.heysports.ui.features.main.tabs.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.heysports.ui.theme.DividerColor
import com.example.heysports.ui.theme.size_80dp
import com.example.heysports.ui.theme.size_line

@Composable
internal fun StatDivider() {
    Box(
        modifier = Modifier
            .width(size_line)
            .height(size_80dp)
            .background(DividerColor)
    )
}
