package com.example.heysports.ui.features.intro.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.ui.features.intro.INTRO_HANDOFF_PROGRESS
import com.example.heysports.ui.features.intro.INTRO_KICK_FOOT_X_IN_PLAYER
import com.example.heysports.ui.features.intro.INTRO_KICK_FOOT_Y_IN_PLAYER
import com.example.heysports.ui.features.intro.INTRO_LOGO_DETACH_END_PROGRESS
import kotlin.math.PI
import kotlin.math.sin

private val INITIAL_LOGO_SIZE = 108.dp
private const val LOGO_VISUAL_SCALE = 0.80f
private const val BALL_DIAMETER_WIDTH_RATIO = 0.086f

@Composable
fun InitialLogoTransition(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val morphProgress = (
            (progress - INTRO_HANDOFF_PROGRESS) /
                    (INTRO_LOGO_DETACH_END_PROGRESS - INTRO_HANDOFF_PROGRESS)
            ).coerceIn(0f, 1f)
    val dropProgress = easeInOut(morphProgress)
    val logoFadeProgress = easeInOut(
        ((morphProgress - 0.08f) / 0.84f).coerceIn(0f, 1f)
    )
    val logoAlpha = 1f - logoFadeProgress

    BoxWithConstraints(modifier = modifier) {
        val playerWidth = minOf(maxWidth * 0.70f, 280.dp)
        val playerHeight = playerWidth * 1.10f
        val kickContactX = maxWidth * 0.22f + playerWidth * INTRO_KICK_FOOT_X_IN_PLAYER
        val kickContactY = maxHeight * 0.47f + playerHeight * INTRO_KICK_FOOT_Y_IN_PLAYER
        val logoSizeAtBall = maxWidth * BALL_DIAMETER_WIDTH_RATIO / LOGO_VISUAL_SCALE
        val currentLogoSize = lerpDp(
            start = INITIAL_LOGO_SIZE.value,
            end = logoSizeAtBall.value,
            amount = dropProgress
        ).dp
        val logoCenterX = lerpDp(
            start = maxWidth.value * 0.50f,
            end = kickContactX.value,
            amount = dropProgress
        ).dp
        val logoCenterY = (
                lerpDp(
                    start = maxHeight.value * 0.50f,
                    end = kickContactY.value,
                    amount = dropProgress
                ) - sin(dropProgress * PI.toFloat()) * maxHeight.value * 0.035f
                ).dp

        if (logoAlpha > 0f) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier
                    .offset(
                        x = logoCenterX - currentLogoSize / 2,
                        y = logoCenterY - currentLogoSize / 2
                    )
                    .size(currentLogoSize)
                    .alpha(logoAlpha)
            )
        }
    }
}

private fun lerpDp(start: Float, end: Float, amount: Float): Float =
    start + (end - start) * amount.coerceIn(0f, 1f)

private fun easeInOut(value: Float): Float = if (value < 0.5f) {
    4f * value * value * value
} else {
    1f - ((- 2f * value + 2f) * (- 2f * value + 2f) * (- 2f * value + 2f)) / 2f
}
