package com.example.heysports.data.model.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.heysports.ui.features.main.routes.HomeRoute
import com.example.heysports.ui.features.main.routes.MatchingRoute
import com.example.heysports.ui.features.main.routes.ProfileRoute
import com.example.heysports.ui.features.main.routes.TeamRoute

enum class EBottomTabs(
    val route: Any,
    val label: String,
    val icon: ImageVector
) {
    HOME(
        route = HomeRoute,
        label = "Trang chủ",
        icon = Icons.Outlined.Home
    ),
    MATCHING(
        route = MatchingRoute,
        label = "Tìm sân",
        icon = Icons.Outlined.Map,
    ),
    TEAM(
        route = TeamRoute,
        label = "Đội",
        icon = Icons.Outlined.Shield
    ),
    PROFILE(
        route = ProfileRoute,
        label = "Hồ Sơ",
        icon = Icons.Outlined.Person
    )
}