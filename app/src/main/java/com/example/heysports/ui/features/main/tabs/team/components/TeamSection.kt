package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.theme.size_8dp

@Composable
internal fun TeamSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        SectionHeader(title = title)
        JPSpacer(height = size_8dp)
        content()
    }
}
