package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.ui.graphics.vector.ImageVector

internal data class ActivityData(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val showUnreadDot: Boolean = false,
    val showChevron: Boolean = false
)
