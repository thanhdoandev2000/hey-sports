package com.example.heysports.ui.features.main.tabs.home.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.extensions.drawFieldBackground
import com.example.heysports.cores.extensions.getValue
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.models.dto.MatchUpcomingDto
import com.example.heysports.domain.models.UserInfo
import com.example.heysports.ui.components.app.ShimmerBox
import com.example.heysports.ui.components.app.UserAvatar
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    user: UserInfo?,
    shimmer: Shimmer,
    isLoading: Boolean = false,
    upComing: MatchUpcomingDto? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bg")

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.55f, animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine), repeatMode = RepeatMode.Reverse
        ), label = "glow"
    )

    Box(modifier = modifier.wrapContentSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
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
                    ShimmerBox(
                        isLoading = isLoading,
                        shimmer = shimmer,
                        modifier = Modifier.size(size_48dp)
                    ) {
                        UserAvatar(
                            name = user?.name.getValue(),
                            size = size_48dp,
                            isLoading = isLoading
                        )
                    }

                    JPSpacer(width = size_8dp)
                    Column(Modifier.weight(1f)) {
                        JPText(
                            text = stringResource(R.string.homeWelcome),
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = size_13sp
                        )
                        ShimmerBox(
                            isLoading,
                            modifier = Modifier
                                .height(size_16dp)
                                .fillMaxWidth(0.5f),
                            shimmer = shimmer
                        ) {
                            JPText(
                                text = user?.name.getValue(),
                                color = Color.White,
                                fontSize = size_16sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
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
                    isLoading = isLoading,
                    shimmer = shimmer,
                    totalWins = 8,
                    upcomingTime = upComing?.matchTime.getValue(),
                    upcomingVenue = upComing?.pitchName.getValue()
                )
            }
        }
    }
}

@Composable
@AppPreview
@Preview
private fun HeaderSectionPreview() {
    HeaderSection(
        user = UserInfo(
            id = "1",
            name = "Doan Tien Thanh",
            email = ""
        ),
        shimmer = rememberShimmer(ShimmerBounds.View)
    )
}