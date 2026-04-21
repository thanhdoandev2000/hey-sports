package com.example.heysports.ui.features.getting

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.heysports.ui.features.navigation.AuthGraph
import com.example.heysports.ui.features.navigation.GettingStartedGraph
import kotlinx.serialization.Serializable


@Serializable
object GettingStartedRoute

fun NavGraphBuilder.gettingGraph(navController: NavController) {
    navigation<GettingStartedGraph>(startDestination = GettingStartedRoute) {
        composable<GettingStartedRoute> {
            val viewModel = hiltViewModel<GettingStartedViewModel>()
            GettingStarted(viewModel) {
                navController.navigate(AuthGraph) {
                    popUpTo(GettingStartedGraph) {
                        inclusive = true
                    }
                }
            }
        }
    }
}