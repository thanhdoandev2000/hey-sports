package com.example.heysports.ui.features

import android.animation.ValueAnimator
import android.graphics.Color as AndroidColor
import android.os.Build
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.heysports.ui.features.intro.IntroMotion
import com.example.heysports.ui.features.intro.IntroMotionScreen
import com.example.heysports.ui.features.navigation.AppNavigation
import com.example.heysports.ui.features.navigation.AuthGraph
import com.example.heysports.ui.features.navigation.MainGraph
import com.example.heysports.ui.features.navigation.OnBoardingGraph
import com.example.heysports.ui.theme.GreenDark
import com.example.heysports.ui.theme.HeySportsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

private const val SYSTEM_SPLASH_MOTION_DURATION_MILLIS = 1_500L
private const val SYSTEM_SPLASH_EXIT_FALLBACK_MILLIS = 250L
private const val SYSTEM_SPLASH_EXIT_TIMEOUT_MILLIS =
    SYSTEM_SPLASH_MOTION_DURATION_MILLIS + SYSTEM_SPLASH_EXIT_FALLBACK_MILLIS
private const val DESTINATION_CROSSFADE_DURATION_MILLIS = 300
private const val SYSTEM_TO_COMPOSE_HANDOFF_DURATION_MILLIS = 180L

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val appViewModel: AppViewModel by viewModels()
    private var hasSystemSplashExited by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        val isColdStart = ! hasPlayedIntroInProcess
        hasPlayedIntroInProcess = true
        val shouldPlayIntroMotion = isColdStart && (
            Build.VERSION.SDK_INT < Build.VERSION_CODES.O ||
                ValueAnimator.areAnimatorsEnabled()
        )
        val splashScreen = installSplashScreen()
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            hasSystemSplashExited = true
            val handoffInterpolator = DecelerateInterpolator()
            splashScreenView.iconView.animate()
                .alpha(0f)
                .scaleX(0.82f)
                .scaleY(0.82f)
                .setDuration(SYSTEM_TO_COMPOSE_HANDOFF_DURATION_MILLIS)
                .setInterpolator(handoffInterpolator)
                .start()
            splashScreenView.view.animate()
                .alpha(0f)
                .setDuration(SYSTEM_TO_COMPOSE_HANDOFF_DURATION_MILLIS)
                .setInterpolator(handoffInterpolator)
                .withEndAction(splashScreenView::remove)
                .start()
        }
        super.onCreate(savedInstanceState)
        updateSystemBars(isIntroVisible = isColdStart)
        setContent {
            var isIntroFinished by remember(isColdStart) {
                mutableStateOf(! isColdStart)
            }
            var shouldResolveDestination by remember {
                mutableStateOf(! isColdStart)
            }

            LaunchedEffect(Unit) {
                if (! shouldResolveDestination) {
                    withFrameNanos { }
                    shouldResolveDestination = true
                }
            }

            LaunchedEffect(isColdStart) {
                if (isColdStart && ! hasSystemSplashExited) {
                    delay(SYSTEM_SPLASH_EXIT_TIMEOUT_MILLIS)
                    hasSystemSplashExited = true
                }
            }

            val destination = if (shouldResolveDestination) {
                val resolvedDestination by appViewModel.destination.collectAsStateWithLifecycle()
                resolvedDestination
            } else {
                null
            }

            val startRoute = destination?.let { currentDestination ->
                when (currentDestination) {
                    SplashDestination.Onboarding -> OnBoardingGraph
                    SplashDestination.Login -> AuthGraph
                    SplashDestination.Home -> MainGraph
                }
            }
            val isIntroVisible = isColdStart && (! isIntroFinished || startRoute == null)
            val shouldShowDestination = startRoute != null && (! isColdStart || isIntroFinished)

            SideEffect {
                updateSystemBars(
                    useLightStatusBarIcons = isIntroVisible || startRoute == MainGraph,
                    isIntroVisible = isIntroVisible
                )
            }

            HeySportsTheme {
                AnimatedContent(
                    targetState = shouldShowDestination,
                    transitionSpec = {
                        fadeIn(tween(DESTINATION_CROSSFADE_DURATION_MILLIS)) togetherWith
                            fadeOut(tween(DESTINATION_CROSSFADE_DURATION_MILLIS))
                    },
                    label = "splashDestinationCrossfade"
                ) { showDestination ->
                    if (showDestination && startRoute != null) {
                        AppNavigation(startDestination = startRoute)
                    } else {
                        when {
                            isColdStart && ! hasSystemSplashExited -> {
                                IntroMotionScreen(progress = 0f)
                            }

                            isColdStart -> {
                                IntroMotion(
                                    shouldPlayMotion = shouldPlayIntroMotion,
                                    onFinished = { isIntroFinished = true }
                                )
                            }

                            else -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(GreenDark)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateSystemBars(
        useLightStatusBarIcons: Boolean = true,
        isIntroVisible: Boolean
    ) {
        val transparentBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT)
        enableEdgeToEdge(
            statusBarStyle = if (useLightStatusBarIcons) {
                transparentBarStyle
            } else {
                SystemBarStyle.auto(
                    lightScrim = AndroidColor.TRANSPARENT,
                    darkScrim = AndroidColor.TRANSPARENT
                )
            },
            navigationBarStyle = if (isIntroVisible) {
                transparentBarStyle
            } else {
                SystemBarStyle.auto(
                    lightScrim = AndroidColor.TRANSPARENT,
                    darkScrim = AndroidColor.TRANSPARENT
                )
            }
        )
    }

    private companion object {
        var hasPlayedIntroInProcess = false
    }
}
