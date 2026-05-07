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
import com.example.heysports.data.models.dto.UpcomingMatchDto
import com.example.heysports.ui.components.cores.*
import com.example.heysports.ui.theme.*
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun UpcomingMatch(
    match: UpcomingMatchDto? = null,
    isLoading: Boolean = false,
    onMarkAttendance: (String) -> Unit = {},
    onOpenMaps: (String) -> Unit = {}
) {

    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    @Composable
    fun Modifier.shimmerIf() = if (isLoading) {
        this
            .shimmer(shimmer)
            .background(Color.LightGray, RoundedCornerShape(size_4dp))
    } else this
    JPCard(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(
                        if (isLoading) Color.Transparent else HighlightBackground,
                        shape = CardShape
                    )
                    .shimmerIf()
            ) {
                if (! isLoading) JPIcon(
                    icon = Icons.Outlined.AccessTime,
                    tint = PrimaryGreen,
                    modifier = Modifier.padding(size_4dp)
                ) else JPSpacer(height = size_24dp, width = size_24dp)
            }
            JPSpacer(width = size_4dp)
            Box(
                modifier = Modifier
                    .then(
                        if (isLoading) Modifier
                            .fillMaxWidth(0.7f)
                            .height(size_16dp) else Modifier
                    )
                    .shimmerIf()
            ) {
                if (! isLoading) JPText(
                    text = stringResource(R.string.homeNextMatches),
                    color = PrimaryGreen,
                    fontWeight = FontWeight.Bold,
                    fontSize = size_15sp
                )
            }
        }
        JPSpacer(height = size_8dp)
        Column(
            modifier = Modifier
                .background(if (isLoading) Color.Transparent else PrimaryGreen, CardShape)
                .wrapContentSize()
                .padding(size_8dp)
                .fillMaxWidth()
                .shimmerIf(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (! isLoading) {
                JPText(
                    text = stringResource(R.string.homeMatchStart),
                    color = Color.White.copy(0.6f),
                    fontSize = size_12sp,
                    fontWeight = FontWeight.SemiBold
                )
                JPSpacer(height = size_4dp)
                CountdownTimer(matchTime = match?.matchTime)
            } else {
                JPSpacer(height = size_100dp)
            }
        }
        JPSpacer(height = size_10dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(size_10dp))
                .background(if (isLoading) Color.Transparent else HighlightBackground)
                .padding(size_10dp)
                .shimmerIf(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (! isLoading) {
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
            } else {
                JPSpacer(height = size_40dp)
            }
        }
        JPSpacer(height = size_6dp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .shimmerIf()
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            if (isLoading) {
                JPSpacer(height = size_16dp)
            } else {
                JPIcon(icon = Icons.Filled.LocationOn, tint = Color.Gray)
                JPSpacer(width = size_4dp)
                JPText(text = match?.pitchAddress, color = Color.DarkGray)
            }
        }
        JPSpacer(height = size_6dp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .shimmerIf()
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            if (isLoading) {
                JPSpacer(height = size_16dp)
            } else {
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
        Row(
            Modifier
                .fillMaxWidth()
                .then(if (isLoading) Modifier.height(size_50dp) else Modifier.wrapContentHeight())
                .shimmerIf()
        ) {
            if (! isLoading) {
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
private fun UpcomingMatchPreview() {
    UpcomingMatch()
}