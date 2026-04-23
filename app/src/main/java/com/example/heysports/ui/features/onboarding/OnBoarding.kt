package com.example.heysports.ui.features.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.cores.JPText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GettingStarted(viewModel: OnboardingViewModel, onStarted: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OnboardingScreen(onboardingPages = uiState.slides) {
        viewModel.updatePreview()
        onStarted()
    }
}

@Composable
fun OnboardingScreen(onboardingPages: List<OnboardingPage>, onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == onboardingPages.lastIndex

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            OnboardingPageContent(page = onboardingPages[page])
        }

        AnimatedVisibility(
            visible = !isLastPage,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 52.dp, end = 24.dp),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = stringResource(R.string.gettingSkip),
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF2E7D32),
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    scope.launch { onFinish() }
                }
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PageIndicator(
                pageCount = onboardingPages.size,
                currentPage = pagerState.currentPage
            )

            Spacer(Modifier.height(32.dp))
            AnimatedContent(
                targetState = isLastPage,
                transitionSpec = {
                    fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                }
            ) { isLast ->
                if (isLast) {
                    Button(
                        onClick = {
                            scope.launch {
                                onFinish()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E7D32)
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PlayArrow,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.gettingStarted),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E7D32)
                        )
                    ) {
                        Text(
                            text =  stringResource(R.string.next),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Spacer(Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(page) {
        visible = false
        delay(100)
        visible = true
    }

    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else 30.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(400)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.weight(0.8f))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(220.dp)
                .offset(y = offsetY)
                .alpha(alpha)
                .shadow(
                    elevation = 12.dp,
                    shape = CircleShape,
                    ambientColor = Color(0xFF2E7D32).copy(alpha = 0.15f),
                    spotColor = Color(0xFF2E7D32).copy(alpha = 0.3f)
                )
                .clip(CircleShape)
                .background(page.backgroundColor)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2E7D32).copy(alpha = 0.12f))
            ) {
                Icon(
                    imageVector = page.icon,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        Spacer(Modifier.height(48.dp))

        JPText(
            text = stringResource(page.title),
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF1A1A1A),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .offset(y = offsetY)
                .alpha(alpha)
        )

        Spacer(Modifier.height(16.dp))

        JPText(
            text = stringResource(page.description),
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF757575),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .offset(y = offsetY)
                .alpha(alpha)
        )

        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun PageIndicator(
    pageCount: Int,
    currentPage: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage
            val width by animateDpAsState(
                targetValue = if (isSelected) 32.dp else 8.dp,
                animationSpec = spring(stiffness = Spring.StiffnessMedium)
            )
            val color by animateColorAsState(
                targetValue = if (isSelected) Color(0xFF2E7D32) else Color(0xFFBDBDBD),
                animationSpec = tween(300)
            )
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(width)
                    .clip(CircleShape)
                    .background(color)
            )
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