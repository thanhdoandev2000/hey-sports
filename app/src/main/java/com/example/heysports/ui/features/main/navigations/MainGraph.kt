package com.example.heysports.ui.features.main.navigations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.heysports.ui.features.main.tabs.home.Home
import com.example.heysports.ui.features.main.tabs.home.attendance.AttendanceMatch
import com.example.heysports.ui.features.main.tabs.maps.Maps
import com.example.heysports.ui.features.main.tabs.profile.Profile
import com.example.heysports.ui.features.main.tabs.team.Team
import com.example.heysports.ui.features.navigation.MainGraph

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation<MainGraph>(startDestination = HomeRoute) {
        composable<HomeRoute> {
            Home { navController.navigate(AttendanceRoute) }
        }
        composable<AttendanceRoute> { AttendanceMatch() }
        composable<MapsRoute> { Maps() }
        composable<TeamRoute> { Team() }
        composable<ProfileRoute> { Profile() }
    }
}