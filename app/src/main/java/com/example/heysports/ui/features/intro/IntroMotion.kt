package com.example.heysports.ui.features.intro

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import com.example.heysports.R
import com.example.heysports.ui.features.intro.components.FootballKickAnimation
import com.example.heysports.ui.features.intro.components.HeySportsWordmark
import com.example.heysports.ui.features.intro.components.InitialLogoTransition
import com.example.heysports.ui.theme.HeySportsTheme
import kotlinx.coroutines.delay

private const val FULL_MOTION_DURATION_MILLIS = 1_300
private const val REDUCED_MOTION_DURATION_MILLIS = 160
private const val END_HOLD_DURATION_MILLIS = 100L
private const val REDUCED_MOTION_START_PROGRESS = INTRO_FINAL_LOGO_START_PROGRESS

@Composable
fun IntroMotion(
    shouldPlayMotion: Boolean,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = remember {
        Animatable(
            if (shouldPlayMotion) {
                INTRO_HANDOFF_PROGRESS
            } else {
                REDUCED_MOTION_START_PROGRESS
            }
        )
    }
    val currentOnFinished by rememberUpdatedState(onFinished)
    val isPreview = LocalInspectionMode.current

    LaunchedEffect(shouldPlayMotion, isPreview) {
        if (isPreview) return@LaunchedEffect

        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = if (shouldPlayMotion) {
                    FULL_MOTION_DURATION_MILLIS
                } else {
                    REDUCED_MOTION_DURATION_MILLIS
                },
                easing = LinearEasing
            )
        )
        delay(END_HOLD_DURATION_MILLIS)
        currentOnFinished()
    }

    IntroMotionScreen(
        progress = if (isPreview) 1f else progress.value,
        modifier = modifier
    )
}

@Composable
fun IntroMotionScreen(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val motionDescription = stringResource(R.string.intro_motion_content_description)

    Box(
        modifier = modifier
            .fillMaxSize()
            .clearAndSetSemantics {
                contentDescription = motionDescription
            }
    ) {
        FootballKickAnimation(
            progress = progress,
            modifier = Modifier.fillMaxSize()
        )
        InitialLogoTransition(
            progress = progress,
            modifier = Modifier.fillMaxSize()
        )
        HeySportsWordmark(
            progress = progress,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, widthDp = 390, heightDp = 844)
@Composable
private fun IntroMotionPreview() {
    HeySportsTheme {
        IntroMotionScreen(progress = 1f)
    }
}
