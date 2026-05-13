package com.example.heysports.ui.features.navigation

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
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
import com.example.heysports.ui.theme.BgColorPage
import com.example.heysports.ui.theme.GreenDark
import com.example.heysports.ui.theme.PrimaryGreen
import com.example.heysports.ui.theme.size_0
import com.example.heysports.ui.theme.size_20dp
import kotlin.math.roundToInt

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(), startDestination: Any
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isMainTab = EBottomTabs.entries.any { destination ->
        navBackStackEntry?.destination?.hierarchy?.any { it.hasRoute(destination.route::class) } == true
    }

    var globalErrors by remember { mutableStateOf<List<String>>(emptyList()) }

    var bottomBarHeightPx by remember { mutableFloatStateOf(0f) }
    val bottomBarOffsetHeightPx = remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current

    val bottomPadding by remember {
        derivedStateOf {
            if (isMainTab) {
                with(density) {
                    bottomBarHeightPx.toDp()
                }
            } else size_0
        }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                if (kotlin.math.abs(delta) < 2f) return Offset.Zero
                val newOffset = bottomBarOffsetHeightPx.floatValue + delta
                bottomBarOffsetHeightPx.floatValue = newOffset.coerceIn(- bottomBarHeightPx, 0f)
                return Offset.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                val target = if (bottomBarOffsetHeightPx.floatValue < - bottomBarHeightPx / 2) {
                    - bottomBarHeightPx
                } else 0f
                animate(
                    initialValue = bottomBarOffsetHeightPx.floatValue,
                    targetValue = target,
                    animationSpec = spring(stiffness = Spring.StiffnessMedium)
                ) { value, _ ->
                    bottomBarOffsetHeightPx.floatValue = value
                }
                return super.onPostFling(consumed, available)
            }
        }
    }

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

    CompositionLocalProvider(
        LocalBottomBarHeight provides bottomBarHeightPx,
        LocalBottomBarOffset provides bottomBarOffsetHeightPx.floatValue
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(nestedScrollConnection),
            containerColor = BgColorPage,
            bottomBar = {
                Box(
                    modifier = Modifier.then(
                        if (isMainTab) Modifier
                            .fillMaxWidth()
                            .offset {
                                IntOffset(
                                    x = 0,
                                    y = - bottomBarOffsetHeightPx.floatValue.roundToInt()
                                )
                            }
                            .background(
                                color = GreenDark,
                                shape = RoundedCornerShape(topEnd = size_20dp, topStart = size_20dp)
                            ) else Modifier.background(Color.Transparent)
                    )
                ) {
                    AnimatedVisibility(
                        visible = isMainTab,
                        enter = slideInVertically(
                            initialOffsetY = { fullHeight -> fullHeight },
                            animationSpec = tween(durationMillis = DURATION, easing = EASING)
                        ),
                        exit = slideOutVertically(
                            targetOffsetY = { fullHeight -> fullHeight },
                            animationSpec = tween(durationMillis = DURATION, easing = EASING)
                        )
                    ) {
                        BottomApp(
                            navController = navController,
                            modifier = Modifier.onSizeChanged { size ->
                                bottomBarHeightPx = size.height.toFloat()
                            }
                        )
                    }
                }
            }, contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = bottomPadding
                ),
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
}
