package com.example.heysports.ui.features.main.tabs.home.components

import PulsingDot
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.extensions.getTextStyleWithoutSpace
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.cores.utils.DateTimeUtils
import com.example.heysports.cores.utils.DateTimeUtils.DATE_DISPLAY
import com.example.heysports.ui.components.app.ShimmerBox
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.theme.*
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
private fun StatCard(
    icon: String,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(CardBg, CardShape)
            .border(size_1dp, CardBorder, CardShape)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = icon,
                fontSize = size_24sp
            )
            Spacer(modifier = Modifier.width(size_4dp))
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = value,
                    color = Color(0xFFF5C518),
                    fontSize = size_22sp,
                    fontWeight = FontWeight.ExtraBold,
                    style = TextStyle().getTextStyleWithoutSpace()
                )
                JPSpacer(height = size_2dp)
                Text(
                    text = label,
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = size_10sp,
                    fontWeight = FontWeight.SemiBold,
                    style = TextStyle().getTextStyleWithoutSpace()
                )
            }
        }
    }
}

@Composable
private fun UpcomingMatchChip(
    time: String,
    venue: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(ActiveCardBg, CardShape)
            .border(size_1dp, ActiveCardBorder, CardShape)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            PulsingDot()
            Spacer(modifier = Modifier.width(size_8dp))
            Column {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontSize = size_18sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        ) {
                            append(DateTimeUtils.convertServerTimeToDisplayTime(time))
                        }

                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFFF5C518),
                                fontSize = size_9sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        ) {
                            append(
                                if (DateTimeUtils.isToday(time)) stringResource(R.string.homeToday) else DateTimeUtils.convertServerTimeToDisplayTime(
                                    serverTime = time,
                                    pattern = DATE_DISPLAY
                                )
                            )
                        }
                    },
                    style = TextStyle().getTextStyleWithoutSpace()
                )
                JPSpacer(height = size_2dp)
                Text(
                    text = venue.uppercase(),
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = size_9sp,
                    fontWeight = FontWeight.SemiBold,
                    style = TextStyle().getTextStyleWithoutSpace()
                )
            }
        }
    }
}

@Composable
fun HeaderStatsRow(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    shimmer: Shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window),
    totalMatches: Int = 12,
    totalWins: Int = 8,
    upcomingTime: String = "19:00",
    upcomingVenue: String = "Tuyên Sơn"
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(size_8dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShimmerBox(
            isLoading = isLoading,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shimmer = shimmer
        ) {
            StatCard(
                icon = "⚽",
                value = "$totalMatches",
                label = stringResource(R.string.homeMatch),
                modifier = Modifier.weight(1f)
            )
        }

        ShimmerBox(
            isLoading = isLoading,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shimmer = shimmer
        ) {
            StatCard(
                icon = "🏆",
                value = "${totalWins}W",
                label = stringResource(R.string.homeWin),
                modifier = Modifier.weight(1f)
            )
        }

        ShimmerBox(
            isLoading = isLoading,
            modifier = Modifier
                .weight(1.3f)
                .fillMaxHeight(),
            shimmer = shimmer
        ) {

            UpcomingMatchChip(
                time = upcomingTime,
                venue = upcomingVenue,
                modifier = Modifier.weight(1.3f)
            )
        }
    }
}

@Preview
@Composable
@AppPreview
private fun HeaderStatsRowPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF145A2E))
            .padding(16.dp)
    ) {
        HeaderStatsRow()
    }
}
