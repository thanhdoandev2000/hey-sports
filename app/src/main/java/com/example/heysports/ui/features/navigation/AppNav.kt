package com.example.heysports.ui.features.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.heysports.cores.events.AppEventBus
import com.example.heysports.cores.events.AppEvents
import com.example.heysports.data.model.enums.EBottomTabs
import com.example.heysports.ui.components.app.GlobalErrorDialog
import com.example.heysports.ui.features.auth.authGraph
import com.example.heysports.ui.features.getting.gettingGraph
import com.example.heysports.ui.features.main.navigations.mainGraph

@Composable
fun AppNavigation() {
    val viewModel = hiltViewModel<AppViewModel>()
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    val isGettingStarted by viewModel.isGettingStarted.collectAsStateWithLifecycle()

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isMainTab = EBottomTabs.entries.any { destination ->
        navBackStackEntry?.destination?.hierarchy?.any { it.hasRoute(destination.route::class) } == true
    }
    if (isLoggedIn == null || isGettingStarted == null) {
        return
    }

    var globalErrors by remember { mutableStateOf<List<String>>(emptyList()) }
    val startDestination = remember(isLoggedIn, isGettingStarted) {
        when {
            isGettingStarted == false -> GettingStartedGraph
            isLoggedIn == false -> AuthGraph
            else -> MainGraph
        }
    }

    LaunchedEffect(Unit) {
        AppEventBus.globalEffect.collect { event ->
            when (event) {
                is AppEvents.ShowGlobalError -> {
                    if (!globalErrors.any { it == event.message }) {
                        globalErrors = globalErrors + event.message
                    }
                }

                else -> {}
            }
        }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isMainTab,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                BottomApp(navController)
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth / 8 },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth / 8 },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth / 8 },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth / 8 },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            gettingGraph(navController)
            authGraph(navController)
            mainGraph(navController)
        }
        GlobalErrorDialog(messages = globalErrors) {
            globalErrors = emptyList()
        }
    }
}