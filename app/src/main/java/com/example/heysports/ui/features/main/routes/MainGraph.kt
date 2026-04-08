package com.example.heysports.ui.features.main.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.heysports.ui.features.main.tabs.home.Home
import com.example.heysports.ui.features.navigation.MainGraph

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation<MainGraph>(startDestination = HomeRoute) {
        composable<HomeRoute> { Home() }
        composable<SettingRoute> { Home() }
    }
}