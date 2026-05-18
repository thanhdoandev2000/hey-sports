package com.example.heysports.ui.features.main.navigations

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.heysports.cores.extensions.navigateSingleTop
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
        composable<HomeRoute> {
            Home(
                onAttendanceClick = {},
                onCreatePost = { navController.navigate(it) },
            )
        }
        composable<MapsRoute> { Maps() }
        composable<TeamRoute> { Team() }
        composable<ProfileRoute> {
            val viewModel = hiltViewModel<ProfileViewModel>()
            Profile(viewModel) {
                navController.navigateSingleTop(route = AuthGraph, popUpToRoute = MainGraph)
            }
        }
        composable<PostOpponentRoute> {
            val viewModel = hiltViewModel<MatchRequestViewModel>()
            FindOpponent(viewModel, onBack = { navController.popBackStack() })
        }
    }
}