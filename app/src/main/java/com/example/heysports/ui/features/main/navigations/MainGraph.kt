package com.example.heysports.ui.features.main.navigations

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.heysports.cores.extensions.navigateSingleTop
import com.example.heysports.ui.features.main.tabs.home.accept.AcceptMatch
import com.example.heysports.ui.features.main.tabs.home.accept.AcceptMatchViewModel
import com.example.heysports.ui.features.main.tabs.home.Home
import com.example.heysports.ui.features.main.tabs.home.posts.FindOpponent
import com.example.heysports.ui.features.main.tabs.home.posts.MatchRequestViewModel
import com.example.heysports.ui.features.main.tabs.maps.Maps
import com.example.heysports.ui.features.main.tabs.profile.Profile
import com.example.heysports.ui.features.main.tabs.profile.ProfileViewModel
import com.example.heysports.ui.features.main.tabs.team.Team
import com.example.heysports.ui.features.navigation.AuthGraph
import com.example.heysports.ui.features.navigation.MainGraph

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation<MainGraph>(startDestination = HomeRoute) {
        composable<HomeRoute> { backStackEntry ->
            val refreshHome by backStackEntry.savedStateHandle
                .getStateFlow(REFRESH_HOME_KEY, false)
                .collectAsStateWithLifecycle()
            Home(
                onAttendanceClick = {},
                onCreatePost = { navController.navigate(it) },
                onAcceptMatch = { id -> navController.navigate(AcceptMatchRoute(id)) },
                refreshSignal = refreshHome,
                onRefreshConsumed = {
                    backStackEntry.savedStateHandle[REFRESH_HOME_KEY] = false
                }
            )
        }
        composable<MapsRoute> { Maps() }
        composable<TeamRoute> { Team() }
        composable<ProfileRoute> {
            val viewModel = hiltViewModel<ProfileViewModel>()
            Profile(
                viewModel = viewModel,
                onSignOut = {
                    navController.navigateSingleTop(
                        route = AuthGraph,
                        popUpToRoute = MainGraph
                    )
                },
                onOpenTeam = {
                    navController.navigateSingleTop(route = TeamRoute)
                }
            )
        }
        composable<PostOpponentRoute> {
            val viewModel = hiltViewModel<MatchRequestViewModel>()
            FindOpponent(viewModel, onBack = { navController.popBackStack() })
        }
        composable<AcceptMatchRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<AcceptMatchRoute>()
            val viewModel = hiltViewModel<AcceptMatchViewModel>()
            AcceptMatch(
                matchRequestId = route.matchRequestId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onSubmitted = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(REFRESH_HOME_KEY, true)
                    navController.popBackStack()
                }
            )
        }
    }
}

private const val REFRESH_HOME_KEY = "refresh_home"
