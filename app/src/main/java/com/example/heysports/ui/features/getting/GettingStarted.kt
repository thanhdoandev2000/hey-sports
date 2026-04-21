package com.example.heysports.ui.features.getting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.cores.JPButton
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.getting.slides.GettingSlide
import com.example.heysports.ui.theme.paddingDefault
import com.example.heysports.ui.theme.paddingMedium
import com.example.heysports.ui.theme.paddingSmall
import com.example.heysports.ui.theme.size_28dp
import com.example.heysports.ui.theme.size_32dp
import com.example.heysports.ui.theme.size_48dp
import com.example.heysports.ui.theme.size_4dp
import kotlinx.coroutines.launch

@Composable
fun GettingStarted(
    viewModel: GettingStartedViewModel, onStarted: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    GettingStartedScreen(slides = uiState.slides, onStarted)
}

@Composable
fun GettingStartedScreen(
    slides: List<GettingSlide>,
    onStarted: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { slides.size })
    val coroutineScope = rememberCoroutineScope()

    HeySportContainer(bgColor = Color.White) {
        Column(modifier = Modifier.padding(paddingDefault)) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = paddingSmall, top = paddingDefault)
            ) {
                if (pagerState.currentPage < slides.size - 1) {
                    TextButton(onClick = onStarted) {
                        JPText(text = stringResource(R.string.gettingSkip))
                    }
                } else {
                    Spacer(modifier = Modifier.height(size_48dp))
                }
            }
            HorizontalPager(
                state = pagerState, modifier = Modifier.weight(1f)
            ) { page ->
                GettingSlide(slide = slides[page])
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = size_32dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(slides.size) { iteration ->
                    val isSelected = pagerState.currentPage == iteration
                    val color = if (isSelected) Color(0xFF428645) else Color(0xFFD1D5DB)
                    val width = if (isSelected) size_28dp else paddingSmall

                    Box(
                        modifier = Modifier
                            .padding(horizontal = size_4dp)
                            .height(paddingSmall)
                            .width(width)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }

            val isLastPage = pagerState.currentPage == slides.size - 1
            val buttonLabel = if (isLastPage) R.string.gettingStarted else R.string.next
            JPButton(
                label = buttonLabel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingMedium)
            ) {
                if (isLastPage) {
                    onStarted()
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }
        }
    }
}

@Composable
@Preview
@AppPreview
fun GettingStartedPreview() {
    GettingStarted(viewModel = hiltViewModel()) {
    }
}