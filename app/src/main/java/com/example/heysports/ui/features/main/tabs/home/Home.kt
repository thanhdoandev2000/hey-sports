package com.example.heysports.ui.features.main.tabs.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.heysports.ui.components.cores.*
import com.example.heysports.ui.features.main.tabs.home.components.*
import com.example.heysports.ui.features.navigation.screenHeight
import com.example.heysports.ui.features.navigation.shimmer
import com.example.heysports.ui.theme.*
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun Home(
    onAttendanceClick: () -> Unit,
    onCreatePost: (route: Any) -> Unit,
    onAcceptMatch: (id: Long) -> Unit = {},
    refreshSignal: Boolean = false,
    onRefreshConsumed: () -> Unit = {}
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getDataFromServer()
    }
    LaunchedEffect(refreshSignal) {
        if (refreshSignal) {
            viewModel.getDataFromServer(true)
            onRefreshConsumed()
        }
    }
    HomeScreen(
        uiState,
        onGetData = { viewModel.getDataFromServer(true) },
        onAttendanceClick,
        onCreatePost = onCreatePost,
        onAcceptMatch = onAcceptMatch
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onGetData: () -> Unit,
    onAttendanceClick: () -> Unit,
    onCreatePost: (route: Any) -> Unit = {},
    onAcceptMatch: (id: Long) -> Unit = {}
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val shimmer = shimmer
    val headerHeight = screenHeight * 0.22f

    var showSheet by rememberSaveable { mutableStateOf(false) }

    HeySportContainer(isEdgeToEdge = true, isLoading = false) {
        Box(modifier = Modifier.fillMaxSize()) {
            HeaderSection(
                user = uiState.personInfo,
                modifier = Modifier.height(headerHeight),
                isLoading = uiState.isLoading,
                shimmer = shimmer,
                upComing = uiState.upComingMatches.firstOrNull()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(top = headerHeight * 0.88f)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = size_24dp, topEnd = size_24dp))
                    .background(BgColorPage)
            ) {
                PullToRefreshBox(
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = onGetData,
                    state = pullToRefreshState,
                    indicator = {
                        Indicator(
                            modifier = Modifier.align(Alignment.TopCenter),
                            isRefreshing = uiState.isRefreshing,
                            state = pullToRefreshState,
                            color = PrimaryGreen
                        )
                    }
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            start = size_16dp,
                            end = size_16dp,
                            top = size_14dp,
                            bottom = size_50dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(size_14dp)
                    ) {
                        items(
                            items = if (uiState.isLoadingUpComing) {
                                listOf(null)
                            } else {
                                uiState.upComingMatches
                            },
                            key = { match -> match?.id ?: "upcoming-loading" }
                        ) { match ->
                            MatchUpcoming(
                                match = match,
                                weather = match?.let { uiState.weatherByMatchId[it.id] },
                                isWeatherLoading = match?.id in uiState.weatherLoadingIds,
                                shimmer = shimmer,
                                isLoading = uiState.isLoadingUpComing,
                                onMarkAttendance = { onAttendanceClick() }
                            )
                        }
                        item {
                            JPCard(containerColor = Color.White, contentColor = Color.Black) {
                                Column {
                                    MatchRequestTitle()
                                    JPSpacer(height = size_16dp)
                                    (if (uiState.isLoadingMatchRequest) listOf(null, null, null)
                                    else uiState.matchRequests).forEach { item ->
                                        MatchRequest(
                                            item = item,
                                            shimmer = shimmer,
                                            isLoading = uiState.isLoadingMatchRequest,
                                            onClick = { id ->
                                                id.toLongOrNull()?.let(onAcceptMatch)
                                            }
                                        )
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
                                val displayedLiveMatches = if (uiState.isLiveLoading) {
                                    listOf(null, null)
                                } else {
                                    uiState.liveMatches
                                }
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(size_16dp)
                                ) {
                                    itemsIndexed(
                                        items = displayedLiveMatches,
                                        key = { index, match ->
                                            match?.id ?: "live-loading-$index"
                                        }
                                    ) { _, match ->
                                        LiveMatch(
                                            isLoading = uiState.isLiveLoading,
                                            match,
                                            shimmer = shimmer
                                        )
                                    }
                                }
                            }
                        }
                        items(
                            items = uiState.newsFeeds,
                            key = { news -> news.id }
                        ) { news ->
                            NewsFeed(news)
                        }
                    }
                }
                JPFloatActionButton(modifier = Modifier.align(Alignment.BottomEnd)) {
                    showSheet = true
                }
            }
        }

        JPBottomSheetModal(
            visible = showSheet,
            containerColor = Color.White,
            showDragHandle = true,
            contentPadding = PaddingValues(
                vertical = size_8dp,
                horizontal = size_16dp
            ),
            onDismiss = { showSheet = false }
        ) { dismiss ->
            QuickCreateSheet(
                items = uiState.newPosts,
                onDismiss = dismiss,
                onItemClick = { item ->
                    dismiss()
                    onCreatePost(item.route)
                }
            )
        }
    }
}

@Composable
@Preview
@AppPreview
private fun HomePreview() {
    HomeScreen(uiState = HomeUiState(), onGetData = {}, onAttendanceClick = {}) {}
}
