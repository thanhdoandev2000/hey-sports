package com.example.heysports.ui.features.main.tabs.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.heysports.R
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.cores.JPButton

@Composable
fun Profile(
    viewModel: ProfileViewModel,
    onSignOut: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ProfileUiEffect.NavigateToLogin -> onSignOut()
            }
        }
    }
    ProfileScreen(viewModel::signOut)
}

@Composable
private fun ProfileScreen(onSignOut: () -> Unit) {
    HeySportContainer(isEdgeToEdge = false) {
        JPButton(label = R.string.authLogout) {
            onSignOut()
        }
    }
}