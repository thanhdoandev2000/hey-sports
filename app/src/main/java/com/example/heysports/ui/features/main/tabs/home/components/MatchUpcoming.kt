package com.example.heysports.ui.features.main.tabs.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.heysports.R
import com.example.heysports.cores.extensions.getValue
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.models.dto.MatchUpcomingDto
import com.example.heysports.ui.components.app.ShimmerBox
import com.example.heysports.ui.components.cores.*
import com.example.heysports.ui.theme.*
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun MatchUpcoming(
    match: MatchUpcomingDto? = null,
    isLoading: Boolean = false,
    shimmer: Shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View),
    onMarkAttendance: (String) -> Unit = {},
    onOpenMaps: (String) -> Unit = {}
) {
    JPCard(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ShimmerBox(
                isLoading = isLoading,
                modifier = Modifier.size(size_24dp),
                shimmer = shimmer
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = HighlightBackground,
                            shape = CardShape
                        )
                ) {
                    JPIcon(
                        icon = Icons.Outlined.AccessTime,
                        tint = PrimaryGreen,
                        modifier = Modifier.padding(size_4dp)
                    )
                }
            }
            JPSpacer(width = size_4dp)
            ShimmerBox(
                isLoading = isLoading,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(size_16dp),
                shimmer = shimmer
            ) {
                JPText(
                    text = stringResource(R.string.homeNextMatches),
                    color = PrimaryGreen,
                    fontWeight = FontWeight.Bold,
                    fontSize = size_15sp
                )
            }
        }
        JPSpacer(height = size_8dp)
        ShimmerBox(
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(size_100dp),
            shimmer = shimmer
        ) {
            Column(
                modifier = Modifier
                    .background(PrimaryGreen, CardShape)
                    .wrapContentSize()
                    .padding(size_8dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                JPText(
                    text = stringResource(R.string.homeMatchStart),
                    color = Color.White.copy(0.6f),
                    fontSize = size_12sp,
                    fontWeight = FontWeight.SemiBold
                )
                JPSpacer(height = size_4dp)
                CountdownTimer(matchTime = match?.matchTime)
            }
        }
        JPSpacer(height = size_10dp)
        ShimmerBox(
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(size_40dp),
            shimmer = shimmer
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(size_10dp))
                    .background(HighlightBackground)
                    .padding(size_10dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    JPText(text = "⛅")
                    Spacer(modifier = Modifier.width(size_8dp))
                    JPText(
                        text = "28°C · Có mây",
                        color = PrimaryGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
                JPText(text = "Thích hợp để đá bóng", fontSize = size_13sp)
            }
        }

        JPSpacer(height = size_6dp)
        ShimmerBox(
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(size_16dp),
            shimmer = shimmer
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                JPIcon(icon = Icons.Filled.LocationOn, tint = Color.Gray)
                JPSpacer(width = size_4dp)
                JPText(text = match?.pitchAddress, color = Color.DarkGray)
            }
        }
        JPSpacer(height = size_6dp)
        ShimmerBox(
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(size_16dp),
            shimmer = shimmer
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                JPIcon(icon = Icons.Filled.Group, tint = Color.Gray)
                JPSpacer(width = size_4dp)
                JPText(
                    text = stringResource(
                        R.string.commonMatches,
                        match?.hostTeamName.getValue(),
                        match?.guestTeamName.getValue()
                    ),
                    color = Color.DarkGray
                )
            }
        }
        JPSpacer(height = size_6dp)
        ShimmerBox(
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(size_50dp),
            shimmer = shimmer
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                JPButton(
                    label = R.string.homeOpenMaps,
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(size_line, PrimaryGreen),
                    bgColor = Color.White,
                    textColor = PrimaryGreen,
                    height = size_42dp
                ) { onOpenMaps(match?.id.toString()) }
                JPSpacer(width = size_16dp)
                JPButton(
                    label = R.string.homeAttendance,
                    modifier = Modifier.weight(1f),
                    height = size_42dp
                ) { onMarkAttendance(match?.id.toString()) }
            }
        }
    }
}

@Composable
@Preview
@AppPreview
private fun MatchUpcomingPreview() {
    MatchUpcoming()
}