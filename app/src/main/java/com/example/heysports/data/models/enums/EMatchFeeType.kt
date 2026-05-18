package com.example.heysports.data.models.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.GroupAdd
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.SportsSoccer
import androidx.compose.ui.graphics.vector.ImageVector

enum class EMatchFeeType(
    val label: String,
    val subLabel: String,
    val icon: ImageVector
) {
    FIFTY_FIFTY(
        "50/50",
        "Chia đều chi phí",
        Icons.Rounded.Groups,
    ),

    SEVENTY_THIRTY(
        "70/30",
        "Thua 70%\nThắng 30%",
        Icons.Rounded.GroupAdd,
    ),

    LOSE_PAYS_ALL(
        "100",
        "Đội thua trả toàn bộ",
        Icons.Rounded.MonetizationOn,
    ),

    PITCH_AND_WATER(
        "Sân/Nc",
        "Chia tiền sân nước",
        Icons.Rounded.SportsSoccer,
    ),

    FREE(
        "Free",
        "Giao lưu vui vẽ free",
        Icons.Rounded.EmojiEvents,
    );
}