package com.example.heysports.data.models.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class EMatchRule(
    val label: String,
    val icon: ImageVector
) {
    NO_HARD_TACKLES(
        "Không đá rát",
        Icons.Outlined.Shield
    ),

    NO_FIGHTING(
        "Không đánh nhau",
        Icons.Outlined.Gavel
    ),

    FAIR_PLAY(
        "Fair play",
        Icons.Outlined.Handshake
    ),
    FRIENDLY_MATCH(
        "Giao lưu vui vẻ",
        Icons.Outlined.Groups
    ),

    NO_SLIDING(
        "Không xoạc bóng",
        Icons.Outlined.Block
    ),

    PUNCTUAL(
        "Đúng giờ",
        Icons.Outlined.Schedule
    ),

    NO_STUDS(
        "Không mang giày đinh",
        Icons.Outlined.DoNotStep
    ),

    ROTATION_ALLOWED(
        "Thay người thoải mái",
        Icons.Outlined.SyncAlt
    )
}