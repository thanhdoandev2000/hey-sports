package com.example.heysports.ui.features.onboarding

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.heysports.ui.features.navigation.AuthGraph
import com.example.heysports.ui.features.navigation.OnBoardingGraph
import kotlinx.serialization.Serializable

@Serializable
object GettingRoute

fun NavGraphBuilder.onBoardingGraph(navController: NavController) {
    navigation<OnBoardingGraph>(startDestination = GettingRoute) {
        composable<GettingRoute> {
            val viewModel = hiltViewModel<OnboardingViewModel>()
            GettingStarted(viewModel) {
                navController.navigate(AuthGraph) {
                    popUpTo(GettingRoute) {
                        inclusive = true
                    }
                }
            }
        }
    }
}