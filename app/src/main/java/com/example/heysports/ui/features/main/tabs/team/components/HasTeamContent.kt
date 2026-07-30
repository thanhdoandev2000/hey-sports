package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.heysports.R
import com.example.heysports.ui.features.navigation.paddingBottomTab
import com.example.heysports.ui.theme.size_12dp
import com.example.heysports.ui.theme.size_16dp
import com.example.heysports.ui.theme.size_24dp

@Composable
internal fun HasTeamContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(
            start = size_16dp,
            top = size_12dp,
            end = size_16dp,
            bottom = paddingBottomTab + size_24dp
        ),
        verticalArrangement = Arrangement.spacedBy(size_12dp)
    ) {
        item {
            MainTeamCard()
        }

        item {
            QuickActionSection()
        }

        item {
            TeamSection(title = stringResource(R.string.team_other_teams)) {
                OtherTeamItem()
            }
        }

        item {
            TeamSection(title = stringResource(R.string.team_recent_activities)) {
                RecentActivitiesCard()
            }
        }
    }
}
