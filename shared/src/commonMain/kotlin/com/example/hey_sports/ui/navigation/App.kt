package com.example.hey_sports.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hey_sports.ui.designsystem.HeySportsTheme
import com.example.hey_sports.ui.screens.auth.AccountChoice
import com.example.hey_sports.ui.screens.auth.AccountDestination
import com.example.hey_sports.ui.screens.welcome.Welcome
import com.example.hey_sports.ui.screens.welcome.rememberWelcomePagerState

@Composable
@Preview
fun App() {
    var accountDestination by rememberSaveable { mutableStateOf<String?>(null) }
    val welcomePager = rememberWelcomePagerState()
    HeySportsTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val destination = accountDestination
            when (destination) {
                null -> Welcome(
                    pagerState = welcomePager,
                    onExplore = { accountDestination = "account" }
                )
                "account" -> AccountChoice(
                    onSignUp = { accountDestination = "signup" },
                    onSignIn = { accountDestination = "signin" },
                    onBack = { accountDestination = null }
                )
                else -> AccountDestination(
                    isSignUp = destination == "signup",
                    onBack = { accountDestination = "account" }
                )
            }
        }
    }
}
