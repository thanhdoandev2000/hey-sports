package com.example.hey_sports.ui.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
internal fun HeySportsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = HeySportsLightColors,
        typography = HeySportsTypography,
        content = content
    )
}
