package com.example.hey_sports.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.hey_sports.ui.designsystem.HeySportsSpacing
import heysports.shared.generated.resources.Res
import heysports.shared.generated.resources.welcome_account_title
import heysports.shared.generated.resources.welcome_auth_pending
import heysports.shared.generated.resources.welcome_back
import heysports.shared.generated.resources.welcome_sign_in
import heysports.shared.generated.resources.welcome_sign_up
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AccountChoice(onSignUp: () -> Unit, onSignIn: () -> Unit, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(HeySportsSpacing.ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(HeySportsSpacing.Large, Alignment.CenterVertically)
    ) {
        Text(
            text = stringResource(Res.string.welcome_account_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Button(onClick = onSignUp) { Text(stringResource(Res.string.welcome_sign_up)) }
        OutlinedButton(onClick = onSignIn) { Text(stringResource(Res.string.welcome_sign_in)) }
        TextButton(onClick = onBack) { Text(stringResource(Res.string.welcome_back)) }
    }
}

@Composable
internal fun AccountDestination(isSignUp: Boolean, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(HeySportsSpacing.ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(HeySportsSpacing.Large, Alignment.CenterVertically)
    ) {
        Text(
            text = stringResource(if (isSignUp) Res.string.welcome_sign_up else Res.string.welcome_sign_in),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(Res.string.welcome_auth_pending),
            textAlign = TextAlign.Center
        )
        Button(onClick = onBack) { Text(stringResource(Res.string.welcome_back)) }
    }
}
