package com.example.heysports.ui.features.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.heysports.ui.features.auth.authGraph
import com.example.heysports.ui.features.main.routes.mainGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomApp(navController) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = MainGraph,
            modifier = Modifier.padding(paddingValues)
        ) {
            authGraph(navController)
            mainGraph(navController)
        }
    }
}