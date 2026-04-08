package com.example.heysports.ui.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.heysports.ui.features.auth.authGraph
import com.example.heysports.ui.features.main.routes.mainGraph


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainGraph) {
        authGraph(navController)
        mainGraph(navController)
    }
}