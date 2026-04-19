package com.example.heysports.data.model.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.heysports.ui.features.main.navigations.HomeRoute
import com.example.heysports.ui.features.main.navigations.MapsRoute
import com.example.heysports.ui.features.main.navigations.ProfileRoute
import com.example.heysports.ui.features.main.navigations.TeamRoute

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
        route = MapsRoute,
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