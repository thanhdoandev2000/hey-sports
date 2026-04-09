package com.example.heysports.ui.features.main.tabs.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.cores.JPCard
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.main.tabs.home.components.HomeHeader
import com.example.heysports.ui.features.main.tabs.home.components.MatchesCard
import com.example.heysports.ui.features.main.tabs.home.components.MatchesLive
import com.example.heysports.ui.features.main.tabs.home.components.MatchmakingSection
import com.example.heysports.ui.features.main.tabs.home.components.MatchmakingSectionHeader
import com.example.heysports.ui.features.main.tabs.home.components.NewsFeed
import com.example.heysports.ui.theme.paddingDefault
import com.example.heysports.ui.theme.paddingMedium
import com.example.heysports.ui.theme.paddingSmall
import com.example.heysports.ui.theme.size_14sp
import com.example.heysports.ui.theme.size_16sp

@Composable
fun Home() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(uiState)
}

@Composable
private fun HomeScreen(uiState: HomeUiState) {
    HeySportContainer {
        HomeHeader()
        LazyColumn(modifier = Modifier.padding(horizontal = paddingMedium)) {
            item { JPSpacer(height = paddingDefault) }
            items(items = uiState.nextMatches) { match -> MatchesCard(match) }
            item { JPSpacer(height = paddingMedium) }
            item {
                JPCard(containerColor = Color.White, contentColor = Color.Black) {
                    Column {
                        MatchmakingSectionHeader()
                        JPSpacer(height = paddingDefault)
                        uiState.matchmakingSections.forEachIndexed { index, matchmaking ->
                            MatchmakingSection(item = matchmaking, index = index) { }
                        }
                    }
                }
            }
            item { JPSpacer(height = paddingMedium) }
            item {
                Column {
                    JPText(
                        text = stringResource(R.string.homeTitleNews),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = size_16sp,
                        color = Color.Black
                    )
                    JPText(
                        text = stringResource(R.string.homeTitleStream),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium,
                        fontSize = size_14sp,
                        color = Color.Black
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(paddingDefault)) {
                        items(uiState.matchesLive) {
                            MatchesLive(it)
                        }
                    }
                }
            }
            items(uiState.newsFeeds) {
                JPSpacer(height = paddingDefault)
                NewsFeed(it)
            }
            item { JPSpacer(height = paddingSmall) }
        }
    }
}

@Composable
@Preview
@AppPreview
private fun HomePreview() {
    HomeScreen(uiState = HomeUiState())
}