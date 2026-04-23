package com.example.heysports.ui.features.auth

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.heysports.cores.extensions.navigateSingleTop
import com.example.heysports.ui.features.auth.login.Login
import com.example.heysports.ui.features.auth.login.LoginViewModel
import com.example.heysports.ui.features.auth.register.RegisterScreen
import com.example.heysports.ui.features.navigation.AuthGraph
import com.example.heysports.ui.features.navigation.MainGraph
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object RegisterRoute

@Serializable
object ForgotPasswordRoute

@Serializable
object ResetPasswordRoute

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<AuthGraph>(startDestination = LoginRoute) {
        composable<LoginRoute> {
            val viewModel = hiltViewModel<LoginViewModel>()
            Login(viewModel, onRegister = {navController.navigate(RegisterRoute)}, onForgotPassword = {}) {
                navController.navigateSingleTop(MainGraph)
            }
        }
        composable<RegisterRoute> { RegisterScreen() }
        composable<ForgotPasswordRoute> { }
        composable<ResetPasswordRoute> { }
    }
}