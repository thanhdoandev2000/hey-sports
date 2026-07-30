package com.example.heysports.ui.features.main.tabs.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.domain.models.UserInfo
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.cores.JPButton
import com.example.heysports.ui.features.main.tabs.profile.components.MyTeamCard
import com.example.heysports.ui.features.main.tabs.profile.components.PlayerProfileCard
import com.example.heysports.ui.features.main.tabs.profile.components.ProfileHero
import com.example.heysports.ui.features.main.tabs.profile.components.SettingsSupportCard
import com.example.heysports.ui.features.navigation.paddingBottomTab
import com.example.heysports.ui.theme.BgColorPage
import com.example.heysports.ui.theme.RedColor
import com.example.heysports.ui.theme.size_0
import com.example.heysports.ui.theme.size_14dp
import com.example.heysports.ui.theme.size_16dp
import com.example.heysports.ui.theme.size_1dp
import com.example.heysports.ui.theme.size_24dp
import com.example.heysports.ui.theme.size_48dp

@Composable
fun Profile(
    viewModel: ProfileViewModel,
    onSignOut: () -> Unit,
    onEditProfile: (() -> Unit)? = null,
    onOpenTeam: (() -> Unit)? = null,
    onOpenSettings: (() -> Unit)? = null,
    onOpenReputation: (() -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.loadProfile()
    }
    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ProfileUiEffect.NavigateToLogin -> onSignOut()
            }
        }
    }

    ProfileScreen(
        uiState = uiState,
        onSignOut = viewModel::signOut,
        onEditProfile = onEditProfile,
        onOpenTeam = onOpenTeam,
        onOpenSettings = onOpenSettings,
        onOpenReputation = onOpenReputation
    )
}

@Composable
internal fun ProfileScreen(
    uiState: ProfileUiState,
    onSignOut: () -> Unit,
    onEditProfile: (() -> Unit)? = null,
    onOpenTeam: (() -> Unit)? = null,
    onOpenSettings: (() -> Unit)? = null,
    onOpenReputation: (() -> Unit)? = null
) {
    HeySportContainer(
        isEdgeToEdge = true,
        isLoading = uiState.isLoading,
        bgColor = BgColorPage
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BgColorPage),
            contentPadding = PaddingValues(bottom = paddingBottomTab + size_24dp)
        ) {
            item {
                ProfileHero(
                    uiState = uiState,
                    onOpenSettings = onOpenSettings,
                    onOpenReputation = onOpenReputation
                )
            }
            item {
                Column(
                    modifier = Modifier.padding(
                        start = size_16dp,
                        top = size_14dp,
                        end = size_16dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(size_14dp)
                ) {
                    PlayerProfileCard(
                        profile = uiState.playerProfile,
                        onEditProfile = onEditProfile
                    )
                    MyTeamCard(
                        team = uiState.primaryTeam,
                        onOpenTeam = onOpenTeam
                    )
                    SettingsSupportCard(onOpenSettings)
                    JPButton(
                        modifier = Modifier.fillMaxWidth(),
                        label = R.string.profile_logout,
                        icon = Icons.AutoMirrored.Outlined.Logout,
                        bgColor = Color.Transparent,
                        textColor = RedColor,
                        border = BorderStroke(size_1dp, RedColor),
                        height = size_48dp,
                        mTop = size_0,
                        onClick = onSignOut
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@AppPreview
@Composable
private fun ProfilePreview() {
    ProfileScreen(
        uiState = ProfileUiState(
            isVerified = true,
            personInfo = UserInfo(
                id = "preview",
                name = "Thanh Đoàn",
                email = "thanhdoan@example.com",
                phoneNumber = "0946 613 608"
            ),
            playerStats = PlayerStats(matches = 12, wins = 8),
            ratingSummary = PlayerRatingSummary(score = 4.8),
            playerProfile = PlayerProfileSummary(
                position = "Tiền vệ",
                skillLevel = "Khá",
                area = "Sơn Trà, Đà Nẵng",
                memberSinceYear = "2025"
            ),
            primaryTeam = ProfileTeamSummary(
                id = 1,
                name = "Hey Sports FC",
                description = "Đội trưởng • 18 thành viên"
            )
        ),
        onSignOut = {}
    )
}
