package com.example.heysports.ui.features.intro.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heysports.R
import com.example.heysports.ui.features.intro.INTRO_FINAL_LOGO_END_PROGRESS
import com.example.heysports.ui.features.intro.INTRO_FINAL_LOGO_START_PROGRESS
import com.example.heysports.ui.features.intro.INTRO_WORDMARK_END_PROGRESS
import com.example.heysports.ui.features.intro.INTRO_WORDMARK_START_PROGRESS
import com.example.heysports.ui.theme.GreenLight
import kotlin.math.abs

@Composable
fun HeySportsWordmark(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val logoMorphProgress = (
            (progress - INTRO_FINAL_LOGO_START_PROGRESS) /
                    (INTRO_FINAL_LOGO_END_PROGRESS - INTRO_FINAL_LOGO_START_PROGRESS)
            ).coerceIn(0f, 1f)
    val logoProgress = easeOut(
        logoMorphProgress
    )
    val logoRevealProgress = easeOut(
        ((logoMorphProgress - 0.24f) / 0.76f).coerceIn(0f, 1f)
    )
    val haloPulse = (1f - abs(logoMorphProgress * 2f - 1f)).coerceIn(0f, 1f)
    val textProgress = easeOut(
        (
                (progress - INTRO_WORDMARK_START_PROGRESS) /
                        (INTRO_WORDMARK_END_PROGRESS - INTRO_WORDMARK_START_PROGRESS)
                ).coerceIn(0f, 1f)
    )

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val centerCircleDiameter = minOf(
            maxWidth * 0.52f,
            maxHeight * 0.1892f
        )
        val logoAssetSize = centerCircleDiameter / 0.80f

        Box(
            modifier = Modifier
                .offset(y = maxHeight * 0.01f)
                .size(logoAssetSize),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val haloRadius = size.minDimension * (0.28f + logoProgress * 0.22f)
                drawCircle(
                    color = GreenLight.copy(alpha = 0.07f * haloPulse),
                    radius = haloRadius
                )
            }
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(logoAssetSize)
                    .scale(0.30f + logoRevealProgress * 0.70f)
                    .alpha(logoRevealProgress)
            )
        }
        Column(
            modifier = Modifier.offset(y = 126.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.intro_app_name),
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.5.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .offset(y = (12 * (1f - textProgress)).dp)
                    .alpha(textProgress),
                fontFamily = FontFamily.Serif
            )
            Text(
                text = stringResource(R.string.intro_tagline),
                color = Color.White.copy(alpha = 0.76f),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.2.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .offset(y = 12.dp)
                    .alpha(textProgress)
            )
        }
    }
}

private fun easeOut(value: Float): Float {
    val inverse = 1f - value.coerceIn(0f, 1f)
    return 1f - inverse * inverse * inverse
}
