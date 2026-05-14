package com.example.heysports.ui.features.main.tabs.home.components

import com.example.heysports.ui.components.app.PulsingDot
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.cores.utils.DateTimeUtils
import com.example.heysports.data.models.dto.LiveMatchDto
import com.example.heysports.data.models.enums.EMatchStatus
import com.example.heysports.ui.components.app.ShimmerBox
import com.example.heysports.ui.components.app.UserAvatar
import com.example.heysports.ui.components.cores.JPCard
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPOutlineButton
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun LiveMatch(
    isLoading: Boolean = false,
    match: LiveMatchDto? = null,
    shimmer: Shimmer
) {
    val containerSize = LocalWindowInfo.current.containerSize
    val screenWidth = with(LocalDensity.current) { containerSize.width.toDp() }
    val maxCardWidth = screenWidth * (1.5f / 2f)
    val status = EMatchStatus.fromString(match?.status)
    JPCard(
        containerColor = Color.White,
        contentColor = Color.Black,
        space = size_0,
        padding = size_0,
        modifier = Modifier.widthIn(max = maxCardWidth)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    status.bgColor,
                    shape = RoundedCornerShape(topStart = size_6dp, topEnd = size_6dp)
                )
                .padding(vertical = size_8dp, horizontal = size_12dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ShimmerBox(
                isLoading = isLoading, shimmer = shimmer,
                modifier = Modifier
                    .width(size_50dp)
                    .height(size_16dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(size_4dp)
                ) {
                    PulsingDot(size = size_8dp, color = status.color)
                    JPText(
                        text = stringResource(status.label),
                        color = status.color,
                        fontWeight = FontWeight.Medium,
                        fontSize = size_12sp
                    )
                }
            }
            ShimmerBox(
                isLoading = isLoading, shimmer = shimmer,
                modifier = Modifier
                    .width(size_50dp)
                    .height(size_16dp)
            ) {
                JPText(
                    text = if (status == EMatchStatus.UPCOMING) DateTimeUtils.getDateDisplay(match?.startTime) else "${match?.currentMinutes ?: 0}' · Hiệp ${match?.half ?: 1}",
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = size_12sp,
                    textAlign = TextAlign.Center
                )
            }
            ShimmerBox(
                isLoading = isLoading, shimmer = shimmer,
                modifier = Modifier
                    .width(size_40dp)
                    .height(size_16dp)
            ) {
                JPText(
                    text = "${match?.duration ?: 90}'",
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = size_12sp
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = size_12dp, vertical = size_12dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ShimmerBox(
                isLoading = isLoading, shimmer = shimmer,
                modifier = Modifier
                    .width(size_50dp)
                    .height(size_50dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(size_4dp)
                ) {
                    UserAvatar(
                        name = match?.hostTeamName.orEmpty(),
                        size = size_36dp,
                        borderWidth = size_0
                    )
                    JPText(
                        text = match?.hostTeamName.orEmpty(),
                        color = Color.Black,
                        fontSize = size_11sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        textAlign = TextAlign.Center
                    )
                }
            }

            ShimmerBox(
                isLoading = isLoading, shimmer = shimmer,
                modifier = Modifier
                    .width(size_80dp)
                    .height(size_40dp)
            ) {
                if (status == EMatchStatus.UPCOMING) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        JPText(
                            text = DateTimeUtils.getTimeDisplay(match?.startTime),
                            fontSize = size_28sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = status.color
                        )
                        JPText(text = "Còn 2 ngày", fontSize = size_13sp, color = Color.Gray)
                    }
                } else {
                    JPText(
                        text = "${match?.hostScore ?: 0} — ${match?.guestScore ?: 0}",
                        fontSize = size_28sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            ShimmerBox(
                isLoading = isLoading, shimmer = shimmer,
                modifier = Modifier
                    .width(size_50dp)
                    .height(size_50dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(size_4dp)
                ) {
                    UserAvatar(
                        name = match?.guestTeamName.orEmpty(),
                        size = size_36dp,
                        borderWidth = size_0
                    )
                    JPText(
                        text = match?.guestTeamName.orEmpty(),
                        color = Color.Black,
                        fontSize = size_11sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        match?.let {
            val progress = when (status) {
                EMatchStatus.LIVE,
                EMatchStatus.HALFTIME -> it.currentMinutes.toFloat() / it.duration.toFloat()
                EMatchStatus.FINISHED -> 100f
                else -> 0f
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = size_12dp)
                    .height(size_4dp)
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(size_2dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .background(GreenDark, RoundedCornerShape(size_2dp))
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = size_12dp, vertical = size_10dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ShimmerBox(
                isLoading = isLoading, shimmer = shimmer,
                modifier = Modifier
                    .weight(1f)
                    .height(size_16dp)
            ) {
                Row(
                    modifier = Modifier.wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(size_4dp)
                ) {
                    JPIcon(
                        icon = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = Color.Gray,
                        size = size_16dp
                    )
                    JPText(
                        text = "${match?.subPitchName} · ${match?.pitchName}",
                        fontSize = size_12sp,
                        color = Color.Gray,
                        maxLines = 1
                    )
                }
            }
            ShimmerBox(
                isLoading = isLoading, shimmer = shimmer,
                modifier = Modifier
                    .width(size_80dp)
                    .height(size_28dp)
            ) {
                JPOutlineButton(
                    isWrapContent = true,
                    height = size_28dp,
                    mTop = size_0,
                    borderColor = status.color.copy(0.5f),
                    label = status.btnLabel,
                    contentColor = status.color,
                    onClick = {},
                    textSize = size_12sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
@Preview
@AppPreview
private fun LiveMatchPreview() {
    LiveMatch(
        match = null,
        shimmer = rememberShimmer(ShimmerBounds.View)
    )
}