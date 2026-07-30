package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.TextPrimary
import com.example.heysports.ui.theme.size_16sp

@Composable
internal fun SectionHeader(title: String) {
    JPText(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = size_16sp,
        color = TextPrimary
    )
}
