package com.example.heysports.ui.features.main.tabs.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.app.UserAvatar
import com.example.heysports.ui.components.cores.JPButton
import com.example.heysports.ui.components.cores.JPCard
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.main.tabs.home.Matchmaking
import com.example.heysports.ui.theme.noPadding
import com.example.heysports.ui.theme.paddingSmall
import com.example.heysports.ui.theme.radiusDefault
import com.example.heysports.ui.theme.size_100dp
import com.example.heysports.ui.theme.size_12sp
import com.example.heysports.ui.theme.size_13sp
import com.example.heysports.ui.theme.size_14sp
import com.example.heysports.ui.theme.size_15sp
import com.example.heysports.ui.theme.size_1dp
import com.example.heysports.ui.theme.size_2dp
import com.example.heysports.ui.theme.size_32dp
import com.example.heysports.ui.theme.size_40dp
import com.example.heysports.ui.theme.size_4dp

@Composable
fun MatchmakingSectionHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        JPCard(
            modifier = Modifier.size(size_32dp),
            padding = size_4dp,
            containerColor = Color.Red
        ) {
            JPIcon(
                icon = Icons.Outlined.LocalFireDepartment,
                tint = Color.White
            )
        }
        JPSpacer(width = size_4dp)
        JPText(
            text = stringResource(R.string.homeFindMatches),
            color = Color.Red,
            fontWeight = FontWeight.SemiBold,
            fontSize = size_15sp
        )
    }
}

@Composable
fun MatchmakingSection(
    item: Matchmaking,
    index: Int,
    onClick: (id: String) -> Unit
) {
    Column(Modifier.wrapContentSize()) {
        if (index != 0) HorizontalDivider(
            modifier = Modifier.padding(vertical = 12.dp),
            thickness = size_1dp,
            color = Color.LightGray
        )
        Row(Modifier.fillMaxWidth()) {
            UserAvatar(name = item.name, size = size_32dp)
            JPSpacer(width = radiusDefault)
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                JPText(
                    text = item.name,
                    fontSize = size_15sp,
                    fontWeight = FontWeight.SemiBold
                )
                JPSpacer(height = paddingSmall)
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Row(modifier = Modifier.weight(1f)) {
                        JPIcon(
                            icon = Icons.Outlined.AccessTime, tint = Color.DarkGray, size = 20.dp
                        )
                        JPSpacer(width = size_4dp)
                        JPText(
                            text = item.dateTime,
                            color = Color.DarkGray,
                            maxLines = 1,
                            fontSize = size_13sp
                        )
                    }
                    JPSpacer(height = size_4dp)
                    Row(modifier = Modifier.weight(1f)) {
                        JPIcon(
                            icon = Icons.Outlined.LocationOn, tint = Color.DarkGray, size = 20.dp
                        )
                        JPSpacer(width = size_4dp)
                        JPText(
                            text = item.location,
                            color = Color.DarkGray,
                            maxLines = 1,
                            fontSize = size_13sp
                        )
                    }
                }
                JPSpacer(height = size_2dp)
                JPText(
                    text = item.description,
                    color = Color.Gray,
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.bodySmall.copy(
                        lineHeight = size_14sp, fontSize = 10.sp
                    ),
                    maxLines = 2,
                    fontSize = size_12sp
                )
                JPButton(
                    label = if (item.isFindMember) R.string.homeRequestJoin else R.string.homeAcceptMatches,
                    modifier = Modifier
                        .height(size_40dp)
                        .width(size_100dp)
                        .padding(noPadding),
                    border = BorderStroke(1.dp, Color.Gray),
                    bgColor = Color.White,
                    textColor = Color.Black,
                    mTop = paddingSmall,
                    contentPadding = PaddingValues(0.dp)
                ) { onClick(item.id) }
            }
        }
    }
}

@Composable
@AppPreview
@Preview
private fun MatchmakingSectionPreview() {
    MatchmakingSection(
        item = Matchmaking(
            id = "1",
            name = "FC Hải Châu(Cách 2km)",
            avatar = null,
            dateTime = "19:00 - Tối Nay",
            location = "Tuyên Sơn",
            description = "Cần tìm gấp 1 đội trung bình yếu, đã có sẵn sân cứng, đội nào nhận kèo giao lưu"
        ), index = 0
    ) {}
}