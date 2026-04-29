package com.example.heysports.ui.features.main.tabs.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.heysports.ui.features.main.tabs.home.components.*
import com.example.heysports.ui.theme.*

@Composable
fun Home(onAttendanceClick: () -> Unit) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(uiState, onAttendanceClick)
}

@Composable
private fun HomeScreen(uiState: HomeUiState, onAttendanceClick: () -> Unit) {
    HeySportContainer(isEdgeToEdge = true) {
        Box(modifier = Modifier.fillMaxSize()) {
            HeaderSection()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = size_24dp, topEnd = size_24dp))
                    .background(BgColorPage)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(size_16dp),
                    verticalArrangement = Arrangement.spacedBy(size_16dp)
                ) {
                    items(items = uiState.nextMatches) { match ->
                        UpcomingMatchCard(
                            match,
                            onMarkAttendance = { onAttendanceClick() }
                        )
                    }
                    item {
                        JPCard(containerColor = Color.White, contentColor = Color.Black) {
                            Column {
                                MatchmakingSectionHeader()
                                JPSpacer(height = size_16dp)
                                uiState.matchmakingSections.forEachIndexed { index, matchmaking ->
                                    MatchBoardItem(item = matchmaking, index = index) { }
                                }
                            }
                        }
                    }
                    item {
                        Column {
                            JPText(
                                text = stringResource(R.string.homeTitleNews),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                fontSize = size_20sp,
                                color = PrimaryGreen
                            )
                            JPText(
                                text = stringResource(R.string.homeTitleStream),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium,
                                fontSize = size_14sp,
                                color = Color.Black
                            )
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(size_16dp)) {
                                items(uiState.matchesLive) {
                                    MatchesLive(it)
                                }
                            }
                        }
                    }
                    items(uiState.newsFeeds) {
                        NewsFeed(it)
                    }
                }
            }
        }
    }
}

@Composable
@Preview
@AppPreview
private fun HomePreview() {
    HomeScreen(uiState = HomeUiState()) {}
}