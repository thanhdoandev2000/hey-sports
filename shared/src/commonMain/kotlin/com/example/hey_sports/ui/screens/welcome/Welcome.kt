package com.example.hey_sports.ui.screens.welcome

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import com.example.hey_sports.ui.designsystem.HeySportsDimensions
import com.example.hey_sports.ui.designsystem.HeySportsSpacing
import heysports.shared.generated.resources.Res
import heysports.shared.generated.resources.onboarding_find_match
import heysports.shared.generated.resources.onboarding_find_pitch
import heysports.shared.generated.resources.onboarding_manage_team
import heysports.shared.generated.resources.welcome_explore
import heysports.shared.generated.resources.welcome_matches_description
import heysports.shared.generated.resources.welcome_matches_title
import heysports.shared.generated.resources.welcome_next
import heysports.shared.generated.resources.welcome_pitches_description
import heysports.shared.generated.resources.welcome_pitches_title
import heysports.shared.generated.resources.welcome_progress
import heysports.shared.generated.resources.welcome_teams_description
import heysports.shared.generated.resources.welcome_teams_title
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private data class WelcomePage(
    val title: StringResource,
    val description: StringResource,
    val image: DrawableResource
)

private val pages = listOf(
    WelcomePage(
        title = Res.string.welcome_matches_title,
        description = Res.string.welcome_matches_description,
        image = Res.drawable.onboarding_find_match
    ),
    WelcomePage(
        title = Res.string.welcome_pitches_title,
        description = Res.string.welcome_pitches_description,
        image = Res.drawable.onboarding_find_pitch
    ),
    WelcomePage(
        title = Res.string.welcome_teams_title,
        description = Res.string.welcome_teams_description,
        image = Res.drawable.onboarding_manage_team
    )
)

@Composable
internal fun rememberWelcomePagerState(): PagerState = rememberPagerState { pages.size }

@Composable
internal fun Welcome(
    pagerState: PagerState,
    onExplore: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val current = pagerState.currentPage
    val isLast = current == pages.lastIndex
    Box(
        modifier = Modifier.fillMaxSize().safeDrawingPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(HeySportsSpacing.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(HeySportsSpacing.Medium)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f).fillMaxWidth()
            ) { index ->
                WelcomePageContent(pages[index], index)
            }
            Button(
                onClick = {
                    if (isLast) onExplore()
                    else scope.launch { pagerState.animateScrollToPage(current + 1) }
                },
                enabled = !pagerState.isScrollInProgress,
                modifier = Modifier.fillMaxWidth().heightIn(min = HeySportsDimensions.ButtonMinHeight),
                shape = HeySportsDimensions.ButtonShape
            ) {
                Text(stringResource(if (isLast) Res.string.welcome_explore else Res.string.welcome_next))
            }
        }
    }
}

@Composable
private fun WelcomePageContent(page: WelcomePage, index: Int) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val imageHeight = minOf(
            maxWidth * HeySportsDimensions.OnboardingImageWidthFraction,
            maxHeight * HeySportsDimensions.OnboardingImageHeightFraction,
            HeySportsDimensions.OnboardingImageMaxHeight
        )
        Column(
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .heightIn(min = maxHeight),
            verticalArrangement = Arrangement.spacedBy(HeySportsSpacing.Large, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(page.image),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(imageHeight),
                contentScale = ContentScale.Fit
            )
            PageIndicator(index)
            Text(
                text = stringResource(page.title),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(page.description),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun PageIndicator(index: Int) {
    val progress = stringResource(Res.string.welcome_progress, index + 1, pages.size)
    val selectedColor = MaterialTheme.colorScheme.primary
    val remainingColor = MaterialTheme.colorScheme.outline
    Row(
        modifier = Modifier.semantics { contentDescription = progress },
        horizontalArrangement = Arrangement.spacedBy(HeySportsSpacing.Small)
    ) {
        repeat(pages.size) { position ->
            val selected = index == position
            val height = HeySportsDimensions.PageIndicatorHeight
            val width = if (selected) HeySportsDimensions.PageIndicatorActiveWidth else height
            Canvas(Modifier.size(width = width, height = height)) {
                drawRoundRect(
                    color = if (selected) selectedColor else remainingColor,
                    cornerRadius = CornerRadius(size.height / 2)
                )
            }
        }
    }
}
