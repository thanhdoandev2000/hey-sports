package com.example.heysports.ui.features.main.tabs.profile.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.heysports.ui.features.main.tabs.profile.ProfileUiState
import com.example.heysports.ui.theme.size_16dp

@Composable
internal fun ProfileHero(
    uiState: ProfileUiState,
    onOpenSettings: (() -> Unit)?,
    onOpenReputation: (() -> Unit)?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(340.dp)
    ) {
        ProfileHeader(
            user = uiState.personInfo,
            profile = uiState.playerProfile,
            isVerified = uiState.isVerified,
            onOpenSettings = onOpenSettings
        )
        OverviewCard(
            stats = uiState.playerStats,
            rating = uiState.ratingSummary,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = size_16dp),
            onOpenReputation = onOpenReputation
        )
    }
}
