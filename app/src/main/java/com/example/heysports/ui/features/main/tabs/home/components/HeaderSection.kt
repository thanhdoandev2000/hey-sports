package com.example.heysports.ui.features.main.tabs.home.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.cores.extensions.drawFieldBackground
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.app.UserAvatar
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@Composable
fun HeaderSection() {
    val infiniteTransition = rememberInfiniteTransition(label = "bg")

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.55f, animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine), repeatMode = RepeatMode.Reverse
        ), label = "glow"
    )
    Box(modifier = Modifier.wrapContentSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.28f)
                .drawBehind { drawFieldBackground() }
                .statusBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                GreenLight.copy(alpha = glowAlpha * 0.25f), Color.Transparent
                            ), center = Offset.Unspecified, radius = 600f
                        )
                    )
                    .padding(size_16dp)
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserAvatar("Doan Tien Thanh", size = size_48dp)
                    JPSpacer(width = size_8dp)
                    Column(Modifier.weight(1f)) {
                        JPText(
                            text = "Xin Chào!",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = size_13sp
                        )
                        JPText(
                            text = "ĐOÀN TIẾN THÀNH",
                            color = Color.White,
                            fontSize = size_16sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(contentAlignment = Alignment.TopEnd) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.15f),
                            modifier = Modifier.size(size_40dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFD32F2F),
                            modifier = Modifier
                                .size(size_10dp)
                                .offset(x = (- 2).dp, y = 2.dp),
                            border = BorderStroke(1.dp, Color(0xFF1A5319))
                        ) {}
                    }
                }
                JPSpacer(height = size_16dp)
                HeaderStatsRow(
                    modifier = Modifier.fillMaxHeight(0.75f),
                    totalMatches = 12,
                    totalWins = 8,
                    upcomingTime = "19:00",
                    upcomingVenue = "Tuyên Sơn"
                )
            }
        }
    }
}

@Composable
@AppPreview
@Preview
private fun HeaderSectionPreview() {
    HeaderSection()
}