package com.example.heysports.ui.features.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.heysports.cores.events.AppEventBus
import com.example.heysports.cores.events.AppEvents
import com.example.heysports.cores.utils.Constant.Animation.DURATION
import com.example.heysports.cores.utils.Constant.Animation.EASING
import com.example.heysports.data.models.enums.EBottomTabs
import com.example.heysports.ui.components.app.GlobalErrorDialog
import com.example.heysports.ui.features.auth.authGraph
import com.example.heysports.ui.features.main.navigations.mainGraph
import com.example.heysports.ui.features.onboarding.onBoardingGraph

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(), startDestination: Any
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isMainTab = EBottomTabs.entries.any { destination ->
        navBackStackEntry?.destination?.hierarchy?.any { it.hasRoute(destination.route::class) } == true
    }

    var globalErrors by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {
        AppEventBus.globalEffect.collect { event ->
            when (event) {
                is AppEvents.ShowGlobalError -> {
                    if (! globalErrors.any { it == event.message }) {
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
                visible = isMainTab, enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = DURATION, easing = EASING)
                ), exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = DURATION, easing = EASING)
                )
            ) {
                BottomApp(navController)
            }
        }, contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth / 4 },
                    animationSpec = tween(DURATION, easing = EASING)
                ) + fadeIn(animationSpec = tween(DURATION))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> - fullWidth / 4 },
                    animationSpec = tween(DURATION, easing = EASING)
                ) + fadeOut(animationSpec = tween(DURATION))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> - fullWidth / 4 },
                    animationSpec = tween(DURATION, easing = EASING)
                ) + fadeIn(animationSpec = tween(DURATION))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth / 4 },
                    animationSpec = tween(DURATION, easing = EASING)
                ) + fadeOut(animationSpec = tween(DURATION))
            }) {
            onBoardingGraph(navController)
            authGraph(navController)
            mainGraph(navController)
        }
        GlobalErrorDialog(messages = globalErrors) {
            globalErrors = emptyList()
        }
    }
}