package com.example.heysports.ui.features

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.heysports.ui.features.navigation.AppNavigation
import com.example.heysports.ui.features.navigation.AuthGraph
import com.example.heysports.ui.features.navigation.MainGraph
import com.example.heysports.ui.features.navigation.OnBoardingGraph
import com.example.heysports.ui.theme.HeySportsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            appViewModel.destination.value == null
        }
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val iconView = splashScreenView.iconView

            val slideUp = ObjectAnimator.ofFloat(iconView, "translationY", 0f, -800f)
            val fadeOut = ObjectAnimator.ofFloat(splashScreenView.view, "alpha", 1f, 0f)
            val scaleX = ObjectAnimator.ofFloat(iconView, "scaleX", 1f, 1.2f)
            val scaleY = ObjectAnimator.ofFloat(iconView, "scaleY", 1f, 1.2f)

            slideUp.duration = 500
            slideUp.interpolator = AnticipateOvershootInterpolator()
            fadeOut.duration = 400
            fadeOut.startDelay = 100
            scaleX.duration = 300
            scaleY.duration = 300
            scaleX.interpolator = OvershootInterpolator()
            scaleY.interpolator = OvershootInterpolator()

            AnimatorSet().apply {
                playTogether(slideUp, fadeOut, scaleX, scaleY)
                doOnEnd { splashScreenView.remove() }
                start()
            }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val destination by appViewModel.destination.collectAsStateWithLifecycle()
            destination?.let { currentDestination ->
                val startRoute = when (currentDestination) {
                    SplashDestination.Onboarding -> OnBoardingGraph
                    SplashDestination.Login -> AuthGraph
                    SplashDestination.Home -> MainGraph
                }

                val navController = rememberNavController()
                HeySportsTheme {
                    AppNavigation(
                        navController = navController,
                        startDestination = startRoute
                    )
                }
            }
        }
    }
}