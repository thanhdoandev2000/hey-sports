package com.example.heysports.ui.features.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.heysports.ui.features.auth.login.Login
import com.example.heysports.ui.features.navigation.AuthGraph
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
        composable<LoginRoute> { Login() }
        composable<RegisterRoute> { Login() }
        composable<ForgotPasswordRoute> {Login() }
        composable<ResetPasswordRoute> { Login() }
    }
}