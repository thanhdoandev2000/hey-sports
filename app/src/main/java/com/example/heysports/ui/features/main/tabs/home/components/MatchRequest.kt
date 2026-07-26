package com.example.heysports.ui.features.main.tabs.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.extensions.getValue
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.cores.utils.DateTimeUtils.getDateTimeDisplay
import com.example.heysports.data.models.dto.MatchRequestDto
import com.example.heysports.data.models.enums.EMatchRequestType
import com.example.heysports.ui.components.app.IconTextRow
import com.example.heysports.ui.components.app.ShimmerBox
import com.example.heysports.ui.components.app.UserAvatar
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPOutlineButton
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun MatchRequestTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.background(BgRedColor, shape = CardShape)) {
            JPIcon(
                icon = Icons.Outlined.LocalFireDepartment,
                tint = RedColor,
                modifier = Modifier.padding(size_4dp)
            )
        }
        JPSpacer(width = size_4dp)
        JPText(
            text = stringResource(R.string.homeFindMatches),
            color = RedColor,
            fontWeight = FontWeight.Bold,
            fontSize = size_15sp
        )
    }
}

@Composable
fun MatchRequest(
    item: MatchRequestDto? = null,
    shimmer: Shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View),
    isLoading: Boolean = false,
    showDivider: Boolean = true,
    onClick: (id: String) -> Unit
) {
    val isTeam = item?.teamId != null
    val type = EMatchRequestType.fromValue(item?.type)
    Column(Modifier.wrapContentSize()) {
        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = size_10dp),
                thickness = size_1dp,
                color = Color.LightGray
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(size_6dp)
        ) {
            ShimmerBox(
                isLoading = isLoading,
                modifier = Modifier.size(size_40dp),
                shimmer = shimmer
            ) {
                UserAvatar(
                    name = if (isTeam) item.teamName else item?.userName,
                    size = size_40dp,
                    borderWidth = size_0
                )
            }
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy((- 2).dp)
            ) {
                ShimmerBox(
                    isLoading = isLoading,
                    modifier = Modifier.height(size_16dp),
                    shimmer = shimmer
                ) {
                    JPText(
                        text = if (isTeam) item.teamName else item?.userName,
                        fontSize = size_15sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(size_4dp)
                ) {
                    ShimmerBox(
                        isLoading = isLoading,
                        modifier = Modifier.height(size_14dp),
                        shimmer = shimmer
                    ) {
                        IconTextRow(
                            icon = Icons.Outlined.AccessTime,
                            iconSize = size_14dp,
                            text = getDateTimeDisplay(item?.matchTime),
                            fontSize = size_11sp
                        )
                    }
                    if (item?.pitchName != null) {
                        ShimmerBox(
                            isLoading = isLoading,
                            modifier = Modifier.height(size_14dp),
                            shimmer = shimmer
                        ) {
                            IconTextRow(
                                icon = Icons.Outlined.LocationOn,
                                iconSize = size_14dp,
                                text = item.pitchName.getValue(),
                                fontSize = size_11sp
                            )
                        }
                    }
                }
            }
            ShimmerBox(
                isLoading = isLoading,
                modifier = Modifier
                    .height(size_32dp)
                    .width(size_80dp),
                shimmer = shimmer
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            type.color.copy(alpha = 0.1f),
                            RoundedCornerShape(size_4dp)
                        )
                        .padding(vertical = size_4dp, horizontal = size_6dp)
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(size_4dp)
                ) {
                    Icon(
                        imageVector = type.icon,
                        contentDescription = null,
                        tint = type.color,
                        modifier = Modifier.size(size_16dp)
                    )
                    JPText(
                        text = stringResource(type.label),
                        color = type.color,
                        fontSize = size_11sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        JPSpacer(height = size_6dp)
        ShimmerBox(
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(size_36dp),
            shimmer = shimmer
        ) {
            JPText(
                text = item?.description,
                color = Color.Gray,
                fontFamily = FontFamily.SansSerif,
                maxLines = 2,
                fontSize = size_12sp
            )
        }
        JPSpacer(height = size_4dp)
        ShimmerBox(
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxWidth(0.33f)
                .height(size_32dp),
            shimmer = shimmer
        ) {
            JPOutlineButton(
                label = type.btbLabel,
                contentColor = type.color,
                mTop = size_8dp,
                height = size_32dp,
                borderColor = type.color.copy(0.2f),
                isWrapContent = true,
                pHoz = size_12dp,
                icon = type.btnIcon,
                textSize = size_13sp,
                iconSize = size_20dp
            ) { onClick(item?.id.toString()) }
        }
    }
}

@Composable
@AppPreview
@Preview
private fun MatchRequestPreview() {
    MatchRequest(
        item = MatchRequestDto(
            id = 2L,
            createdAt = "2026-05-06T09:31:35+00:00",
            postedByType = "TEAM",
            type = "FIND_OPPONENT",
            matchTime = "2026-05-07T19:00:00",
            description = "Cần tìm gấp 1 đội trung bình yếu, đã có sẵn sân cứng, đội nào nhận kèo giao lưu",
            status = "Opened",
            skillLevel = "Trung Bình - Yếu",
            teamId = 5L,
            teamName = "FC Tuyên Sơn Group",
            teamAvatar = "", // Để trống theo JSON
            teamArea = "Quận Hải Châu, Đà Nẵng",
            userId = null,
            userName = null,
            userAvatar = null,
            pitchId = 2L,
            pitchName = "Sân bóng Tuyên Sơn",
            pitchAddress = "Số 1 Vũ Thạnh, Hòa Cường Nam, Hải Châu, Đà Nẵng",
            pitchLat = "16.0355",
            pitchLong = "108.2235"
        )
    ) {}
}
