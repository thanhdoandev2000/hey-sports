package com.example.heysports.ui.features.intro.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.ui.features.intro.INTRO_BALL_FLIGHT_END_PROGRESS
import com.example.heysports.ui.features.intro.INTRO_FINAL_LOGO_END_PROGRESS
import com.example.heysports.ui.features.intro.INTRO_FINAL_LOGO_START_PROGRESS
import com.example.heysports.ui.features.intro.INTRO_HANDOFF_PROGRESS
import com.example.heysports.ui.features.intro.INTRO_KICK_CONTACT_PROGRESS
import com.example.heysports.ui.features.intro.INTRO_KICK_FOOT_X_IN_PLAYER
import com.example.heysports.ui.features.intro.INTRO_KICK_FOOT_Y_IN_PLAYER
import com.example.heysports.ui.features.intro.INTRO_LOGO_DETACH_END_PROGRESS
import com.example.heysports.ui.features.intro.INTRO_PLAYER_END_PROGRESS
import com.example.heysports.ui.features.intro.INTRO_PLAYER_START_PROGRESS
import com.example.heysports.ui.theme.GreenDark
import com.example.heysports.ui.theme.GreenLight
import com.example.heysports.ui.theme.PrimaryGreen
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin

private const val FIELD_REVEAL_END_PROGRESS = 0.28f

@Composable
fun FootballKickAnimation(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val safeProgress = progress.coerceIn(0f, 1f)

    BoxWithConstraints(modifier = modifier) {
        val playerWidth = minOf(maxWidth * 0.70f, 280.dp)
        val playerHeight = playerWidth * 1.10f
        val kickContactX = maxWidth * 0.22f + playerWidth * INTRO_KICK_FOOT_X_IN_PLAYER
        val kickContactY = maxHeight * 0.47f + playerHeight * INTRO_KICK_FOOT_Y_IN_PLAYER

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawField(progress = safeProgress)
            drawAnimatedBall(
                progress = safeProgress,
                start = Offset(
                    x = kickContactX.toPx(),
                    y = kickContactY.toPx()
                )
            )
        }

        if (
            safeProgress >= INTRO_PLAYER_START_PROGRESS &&
            safeProgress < INTRO_PLAYER_END_PROGRESS
        ) {
            val runProgress = easeInOut(
                (
                    (safeProgress - INTRO_PLAYER_START_PROGRESS) /
                        (INTRO_KICK_CONTACT_PROGRESS - INTRO_PLAYER_START_PROGRESS)
                    ).coerceIn(0f, 1f)
            )
            val playerX = maxWidth * lerp(-0.38f, 0.22f, runProgress)
            val playerY = maxHeight * 0.47f
            val playerAlpha = if (safeProgress <= INTRO_KICK_CONTACT_PROGRESS) {
                easeOut(
                    (
                        (safeProgress - INTRO_PLAYER_START_PROGRESS) / 0.06f
                        ).coerceIn(0f, 1f)
                )
            } else {
                1f - (
                    (safeProgress - INTRO_KICK_CONTACT_PROGRESS) /
                        (INTRO_PLAYER_END_PROGRESS - INTRO_KICK_CONTACT_PROGRESS)
                    ).coerceIn(0f, 1f)
            }

            Image(
                painter = painterResource(playerPoseFor(safeProgress)),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .offset(x = playerX, y = playerY)
                    .size(width = playerWidth, height = playerHeight)
                    .alpha(playerAlpha)
            )
        }
    }
}

@DrawableRes
private fun playerPoseFor(progress: Float): Int {
    val runPoseProgress = (
        (progress - INTRO_PLAYER_START_PROGRESS) /
            (INTRO_KICK_CONTACT_PROGRESS - INTRO_PLAYER_START_PROGRESS)
        ).coerceIn(0f, 1f)

    return when {
        runPoseProgress < 0.32f -> R.drawable.intro_player_run_1
        runPoseProgress < 0.60f -> R.drawable.intro_player_run_2
        runPoseProgress < 0.84f -> R.drawable.intro_player_run_3
        else -> R.drawable.intro_player_kick
    }
}

private fun DrawScope.drawField(progress: Float) {
    drawRect(color = GreenDark)
    val revealProgress = easeOut(
        (
            (progress - INTRO_HANDOFF_PROGRESS) /
                (FIELD_REVEAL_END_PROGRESS - INTRO_HANDOFF_PROGRESS)
            ).coerceIn(0f, 1f)
    )
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(GreenDark, PrimaryGreen)
        ),
        alpha = revealProgress
    )

    val lineColor = GreenLight.copy(alpha = revealProgress * 0.20f)
    val lineWidth = 1.5.dp.toPx()
    val fieldLeft = 1.dp.toPx()
    val fieldRight = size.width - 1.dp.toPx()
    val fieldTop = size.height * 0.08f
    val fieldBottom = size.height * 0.94f
    val fieldWidth = fieldRight - fieldLeft
    val fieldHeight = fieldBottom - fieldTop
    val center = Offset(
        x = size.width / 2f,
        y = fieldTop + fieldHeight / 2f
    )
    val centerCircleRadius = minOf(size.width * 0.26f, fieldHeight * 0.11f)
    val penaltyAreaWidth = fieldWidth * 0.67f
    val penaltyAreaDepth = fieldHeight * 0.12f
    val goalAreaWidth = fieldWidth * 0.27f
    val goalAreaDepth = fieldHeight * 0.04f
    val penaltySpotDistance = fieldHeight * 0.09f
    val penaltyArcRadius = size.width * 0.12f
    val cornerRadius = size.width * 0.05f

    drawRect(
        color = lineColor,
        topLeft = Offset(fieldLeft, fieldTop),
        size = Size(fieldWidth, fieldHeight),
        style = Stroke(width = lineWidth)
    )

    drawLine(
        color = lineColor,
        start = Offset(fieldLeft, center.y),
        end = Offset(fieldRight, center.y),
        strokeWidth = lineWidth
    )
    drawCircle(
        color = lineColor,
        radius = centerCircleRadius,
        center = center,
        style = Stroke(width = lineWidth)
    )
    drawCircle(
        color = lineColor,
        radius = 2.5.dp.toPx(),
        center = center
    )

    drawConnectedArea(
        color = lineColor,
        centerX = center.x,
        width = penaltyAreaWidth,
        goalLineY = fieldTop,
        depth = penaltyAreaDepth,
        direction = 1f,
        strokeWidth = lineWidth
    )
    drawConnectedArea(
        color = lineColor,
        centerX = center.x,
        width = goalAreaWidth,
        goalLineY = fieldTop,
        depth = goalAreaDepth,
        direction = 1f,
        strokeWidth = lineWidth
    )
    drawConnectedArea(
        color = lineColor,
        centerX = center.x,
        width = penaltyAreaWidth,
        goalLineY = fieldBottom,
        depth = penaltyAreaDepth,
        direction = -1f,
        strokeWidth = lineWidth
    )
    drawConnectedArea(
        color = lineColor,
        centerX = center.x,
        width = goalAreaWidth,
        goalLineY = fieldBottom,
        depth = goalAreaDepth,
        direction = -1f,
        strokeWidth = lineWidth
    )

    val topPenaltySpot = Offset(center.x, fieldTop + penaltySpotDistance)
    val bottomPenaltySpot = Offset(center.x, fieldBottom - penaltySpotDistance)
    drawCircle(color = lineColor, radius = 2.dp.toPx(), center = topPenaltySpot)
    drawCircle(color = lineColor, radius = 2.dp.toPx(), center = bottomPenaltySpot)

    val penaltyArcVerticalOffset = penaltyAreaDepth - penaltySpotDistance
    val penaltyArcIntersectionAngle = (
        asin(
            (penaltyArcVerticalOffset / penaltyArcRadius).coerceIn(-1f, 1f)
        ) * 180f / PI.toFloat()
        )
    val penaltyArcSweep = 180f - penaltyArcIntersectionAngle * 2f

    drawArc(
        color = lineColor,
        startAngle = penaltyArcIntersectionAngle,
        sweepAngle = penaltyArcSweep,
        useCenter = false,
        topLeft = Offset(
            center.x - penaltyArcRadius,
            topPenaltySpot.y - penaltyArcRadius
        ),
        size = Size(penaltyArcRadius * 2f, penaltyArcRadius * 2f),
        style = Stroke(width = lineWidth)
    )
    drawArc(
        color = lineColor,
        startAngle = 180f + penaltyArcIntersectionAngle,
        sweepAngle = penaltyArcSweep,
        useCenter = false,
        topLeft = Offset(
            center.x - penaltyArcRadius,
            bottomPenaltySpot.y - penaltyArcRadius
        ),
        size = Size(penaltyArcRadius * 2f, penaltyArcRadius * 2f),
        style = Stroke(width = lineWidth)
    )

    drawCornerArc(
        color = lineColor,
        topLeft = Offset(fieldLeft - cornerRadius, fieldTop - cornerRadius),
        radius = cornerRadius,
        startAngle = 0f,
        strokeWidth = lineWidth
    )
    drawCornerArc(
        color = lineColor,
        topLeft = Offset(fieldRight - cornerRadius, fieldTop - cornerRadius),
        radius = cornerRadius,
        startAngle = 90f,
        strokeWidth = lineWidth
    )
    drawCornerArc(
        color = lineColor,
        topLeft = Offset(fieldRight - cornerRadius, fieldBottom - cornerRadius),
        radius = cornerRadius,
        startAngle = 180f,
        strokeWidth = lineWidth
    )
    drawCornerArc(
        color = lineColor,
        topLeft = Offset(fieldLeft - cornerRadius, fieldBottom - cornerRadius),
        radius = cornerRadius,
        startAngle = 270f,
        strokeWidth = lineWidth
    )

    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                GreenLight.copy(alpha = 0.14f * revealProgress),
                Color.Transparent
            ),
            center = center,
            radius = size.width * 0.58f
        ),
        radius = size.width * 0.58f,
        center = center
    )
}

private fun DrawScope.drawConnectedArea(
    color: Color,
    centerX: Float,
    width: Float,
    goalLineY: Float,
    depth: Float,
    direction: Float,
    strokeWidth: Float
) {
    val left = centerX - width / 2f
    val right = centerX + width / 2f
    val farY = goalLineY + depth * direction

    drawLine(
        color = color,
        start = Offset(left, goalLineY),
        end = Offset(left, farY),
        strokeWidth = strokeWidth
    )
    drawLine(
        color = color,
        start = Offset(right, goalLineY),
        end = Offset(right, farY),
        strokeWidth = strokeWidth
    )
    drawLine(
        color = color,
        start = Offset(left, farY),
        end = Offset(right, farY),
        strokeWidth = strokeWidth
    )
}

private fun DrawScope.drawCornerArc(
    color: Color,
    topLeft: Offset,
    radius: Float,
    startAngle: Float,
    strokeWidth: Float
) {
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = 90f,
        useCenter = false,
        topLeft = topLeft,
        size = Size(radius * 2f, radius * 2f),
        style = Stroke(width = strokeWidth)
    )
}

private fun DrawScope.drawAnimatedBall(
    progress: Float,
    start: Offset
) {
    val end = Offset(
        x = size.width * 0.50f,
        y = size.height * 0.51f
    )
    val ballRadius = size.width * 0.043f

    if (progress < INTRO_HANDOFF_PROGRESS) return

    if (progress < INTRO_LOGO_DETACH_END_PROGRESS) {
        val morphProgress = (
            (progress - INTRO_HANDOFF_PROGRESS) /
                (INTRO_LOGO_DETACH_END_PROGRESS - INTRO_HANDOFF_PROGRESS)
            ).coerceIn(0f, 1f)
        val dropProgress = easeInOut(morphProgress)
        drawFootball(
            center = Offset(
                x = lerp(size.width * 0.50f, start.x, dropProgress),
                y = lerp(size.height * 0.50f, start.y, dropProgress) -
                    sin(dropProgress * PI.toFloat()) * size.height * 0.035f
            ),
            radius = lerp(ballRadius * 1.95f, ballRadius, dropProgress),
            alpha = easeInOut(
                ((morphProgress - 0.08f) / 0.84f).coerceIn(0f, 1f)
            )
        )
        return
    }

    if (progress < INTRO_KICK_CONTACT_PROGRESS) {
        val bounceProgress = (
            (progress - INTRO_LOGO_DETACH_END_PROGRESS) /
                (INTRO_KICK_CONTACT_PROGRESS - INTRO_LOGO_DETACH_END_PROGRESS)
            ).coerceIn(0f, 1f)
        val bounce = abs(sin(bounceProgress * PI.toFloat() * 2f)) * size.height * 0.009f
        drawFootball(
            center = start.copy(y = start.y - bounce),
            radius = ballRadius
        )
        return
    }

    if (progress > INTRO_FINAL_LOGO_END_PROGRESS) return

    if (progress > INTRO_BALL_FLIGHT_END_PROGRESS) {
        drawBallToLogoMorph(
            progress = progress,
            center = end,
            ballRadius = ballRadius * 1.45f
        )
        return
    }

    val flightProgress = easeOut(
        (progress - INTRO_KICK_CONTACT_PROGRESS) /
            (INTRO_BALL_FLIGHT_END_PROGRESS - INTRO_KICK_CONTACT_PROGRESS)
    )
    val ballCenter = Offset(
        x = lerp(start.x, end.x, flightProgress),
        y = lerp(start.y, end.y, flightProgress) -
            sin(flightProgress * PI.toFloat()) * size.height * 0.10f
    )

    repeat(3) { index ->
        val trailProgress = (flightProgress - (index + 1) * 0.055f).coerceAtLeast(0f)
        val trailCenter = Offset(
            x = lerp(start.x, end.x, trailProgress),
            y = lerp(start.y, end.y, trailProgress) -
                sin(trailProgress * PI.toFloat()) * size.height * 0.10f
        )
        drawCircle(
            color = Color.White.copy(alpha = 0.12f - index * 0.025f),
            radius = ballRadius * (0.82f - index * 0.12f),
            center = trailCenter
        )
    }

    drawFootball(
        center = ballCenter,
        radius = lerp(ballRadius, ballRadius * 1.45f, flightProgress)
    )

    if (progress < INTRO_KICK_CONTACT_PROGRESS + 0.08f) {
        val burstProgress = (
            (progress - INTRO_KICK_CONTACT_PROGRESS) / 0.08f
            ).coerceIn(0f, 1f)
        repeat(3) { index ->
            val angle = -0.55f + index * 0.36f
            val length = lerp(
                10.dp.toPx(),
                34.dp.toPx(),
                burstProgress
            )
            drawLine(
                color = Color.White.copy(alpha = 0.75f * (1f - burstProgress)),
                start = start - Offset(ballRadius * 1.5f, 0f),
                end = start - Offset(
                    x = cos(angle) * length,
                    y = -sin(angle) * length
                ),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}

private fun DrawScope.drawBallToLogoMorph(
    progress: Float,
    center: Offset,
    ballRadius: Float
) {
    val morphProgress = (
        (progress - INTRO_FINAL_LOGO_START_PROGRESS) /
            (INTRO_FINAL_LOGO_END_PROGRESS - INTRO_FINAL_LOGO_START_PROGRESS)
        ).coerceIn(0f, 1f)
    val easedProgress = easeOut(morphProgress)
    val logoRevealProgress = easeOut(
        ((morphProgress - 0.24f) / 0.76f).coerceIn(0f, 1f)
    )
    val fieldHeight = size.height * 0.86f
    val centerCircleRadius = minOf(size.width * 0.26f, fieldHeight * 0.11f)
    val finalLogoLineEnd = centerCircleRadius * (2f / 3f)
    val lineStart = ballRadius * 0.82f
    val lineEnd = lerp(
        lineStart,
        finalLogoLineEnd,
        easedProgress
    )
    val lineAlpha = 0.72f * easedProgress * (1f - logoRevealProgress)

    drawCircle(
        color = GreenLight.copy(alpha = 0.10f * (1f - morphProgress)),
        radius = lerp(ballRadius * 1.15f, ballRadius * 2.6f, easedProgress),
        center = center
    )
    drawLine(
        color = Color.White.copy(alpha = lineAlpha),
        start = center - Offset(lineStart, 0f),
        end = center - Offset(lineEnd, 0f),
        strokeWidth = 2.dp.toPx(),
        cap = StrokeCap.Round
    )
    drawLine(
        color = Color.White.copy(alpha = lineAlpha),
        start = center + Offset(lineStart, 0f),
        end = center + Offset(lineEnd, 0f),
        strokeWidth = 2.dp.toPx(),
        cap = StrokeCap.Round
    )
    drawCircle(
        color = Color.White.copy(alpha = lineAlpha),
        radius = 3.dp.toPx(),
        center = center - Offset(lineEnd, 0f)
    )
    drawCircle(
        color = Color.White.copy(alpha = lineAlpha),
        radius = 3.dp.toPx(),
        center = center + Offset(lineEnd, 0f)
    )
    drawFootball(
        center = center,
        radius = ballRadius,
        alpha = 1f - easedProgress * 0.86f
    )
}

private fun DrawScope.drawFootball(
    center: Offset,
    radius: Float,
    alpha: Float = 1f
) {
    drawCircle(
        color = Color(0xFFFAFCF8).copy(alpha = alpha),
        radius = radius,
        center = center
    )
    drawCircle(
        color = Color(0xFFDDE8DD).copy(alpha = alpha),
        radius = radius,
        center = center,
        style = Stroke(width = 1.dp.toPx())
    )

    val pentagon = Path()
    repeat(5) { index ->
        val angle = -PI.toFloat() / 2f + index * (2f * PI.toFloat() / 5f)
        val point = Offset(
            x = center.x + cos(angle) * radius * 0.35f,
            y = center.y + sin(angle) * radius * 0.35f
        )
        if (index == 0) pentagon.moveTo(point.x, point.y) else pentagon.lineTo(point.x, point.y)
    }
    pentagon.close()
    drawPath(path = pentagon, color = GreenDark.copy(alpha = alpha))

    repeat(5) { index ->
        val angle = -PI.toFloat() / 2f + index * (2f * PI.toFloat() / 5f)
        val outer = Offset(
            x = center.x + cos(angle) * radius * 0.70f,
            y = center.y + sin(angle) * radius * 0.70f
        )
        drawCircle(
            color = GreenDark.copy(alpha = alpha),
            radius = radius * 0.12f,
            center = outer
        )
    }
}

private fun easeInOut(value: Float): Float {
    val safeValue = value.coerceIn(0f, 1f)
    return if (safeValue < 0.5f) {
        4f * safeValue * safeValue * safeValue
    } else {
        1f - ((-2f * safeValue + 2f) * (-2f * safeValue + 2f) *
            (-2f * safeValue + 2f)) / 2f
    }
}

private fun easeOut(value: Float): Float {
    val inverse = 1f - value.coerceIn(0f, 1f)
    return 1f - inverse * inverse * inverse
}

private fun lerp(start: Float, end: Float, amount: Float): Float =
    start + (end - start) * amount.coerceIn(0f, 1f)
