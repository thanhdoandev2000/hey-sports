package com.example.heysports.data.models.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.ui.graphics.vector.ImageVector

enum class EMatchType(val label: String, val icon: ImageVector) {
    FIVE_VS_FIVE("5 vs 5", Icons.Outlined.Group),
    SEVEN_VS_SEVEN("7 vs 7", Icons.Outlined.Group),
    ELEVEN_VS_ELEVEN("11 vs 11", Icons.Outlined.Group),
    FRIENDLY("Giao hữu", Icons.Outlined.Handshake);

    companion object {
        fun fromLabel(label: String) = entries.find { it.label == label } ?: FIVE_VS_FIVE
    }
}