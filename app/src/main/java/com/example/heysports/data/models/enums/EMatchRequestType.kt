package com.example.heysports.data.models.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.GroupAdd
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.PersonAddAlt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.heysports.R
import com.example.heysports.ui.theme.PrimaryGreen

enum class EMatchRequestType(
    val value: String,
    val icon: ImageVector,
    val label: Int,
    val color: Color,
    val btbLabel: Int,
    val btnIcon: ImageVector
) {
    FIND_OPPONENT(
        value = "FIND_OPPONENT",
        icon = Icons.Default.SportsSoccer,
        label = R.string.homeFindMatch,
        color = PrimaryGreen,
        btbLabel = R.string.homeAcceptMatches,
        btnIcon = Icons.Outlined.Handshake
    ),
    FIND_PLAYER(
        value = "FIND_PLAYER",
        icon = Icons.Default.PersonSearch,
        label = R.string.homeFindPlayer,
        color = Color(0xFF2196F3),
        btbLabel = R.string.homeRequestJoin,
        btnIcon = Icons.Outlined.PersonAddAlt
    ),
    REQUEST_SLOT(
        value = "REQUEST_SLOT",
        icon = Icons.AutoMirrored.Filled.Login,
        label = R.string.homeRequestJoin,
        color = Color(0xFFFF9800),
        btbLabel = R.string.homeAccept,
        btnIcon = Icons.Outlined.CheckCircleOutline
    ),
    RECRUITING_SLOT(
        value = "RECRUITING_SLOT",
        icon = Icons.Default.Groups,
        label = R.string.homeFindMembers,
        color = Color(0xFF9C27B0),
        btbLabel = R.string.homeApply,
        btnIcon = Icons.Outlined.GroupAdd
    ),
    OTHER(
        value = "OTHER",
        icon = Icons.Default.SportsSoccer,
        label = R.string.empty,
        color = Color.Gray,
        btbLabel = R.string.empty,
        btnIcon = Icons.Default.PersonAdd
    );

    companion object {
        fun fromValue(value: String?): EMatchRequestType {
            return entries.find { it.value == value } ?: OTHER
        }
    }
}

