package com.example.heysports.ui.features.main.tabs.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.HorizontalDivider
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
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.app.IconTextRow
import com.example.heysports.ui.components.app.UserAvatar
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPOutlineButton
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.main.tabs.home.Matchmaking
import com.example.heysports.ui.theme.*

@Composable
fun MatchmakingSectionHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.background(BgRedColor, shape = CardShape)) {
            JPIcon(
                icon = Icons.Outlined.LocalFireDepartment,
                tint = Color.Red,
                modifier = Modifier.padding(size_4dp)
            )
        }
        JPSpacer(width = size_4dp)
        JPText(
            text = stringResource(R.string.homeFindMatches),
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontSize = size_15sp
        )
    }
}

@Composable
fun MatchBoardItem(
    item: Matchmaking,
    index: Int,
    onClick: (id: String) -> Unit
) {
    Column(Modifier.wrapContentSize()) {
        if (index != 0) HorizontalDivider(
            modifier = Modifier.padding(vertical = size_12dp),
            thickness = size_1dp,
            color = Color.LightGray
        )
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            UserAvatar(name = item.name, size = size_40dp, borderWidth = size_0)
            JPSpacer(width = size_8dp)
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                JPText(
                    text = item.name,
                    fontSize = size_15sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    IconTextRow(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Outlined.AccessTime,
                        iconSize = size_18dp,
                        text = item.dateTime,
                        fontSize = size_12sp
                    )
                    IconTextRow(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Outlined.LocationOn,
                        iconSize = size_18dp,
                        text = item.location,
                        fontSize = size_12sp
                    )
                }
            }
        }
        JPSpacer(height = size_6dp)
        JPText(
            text = item.description,
            color = Color.Gray,
            fontFamily = FontFamily.SansSerif,
            maxLines = 2,
            fontSize = size_12sp
        )
        JPOutlineButton(
            label = if (item.isFindMember) R.string.homeRequestJoin else R.string.homeAcceptMatches,
            contentColor = PrimaryGreen,
            mTop = size_8dp,
            height = size_32dp,
            weight = 0.3f,
        ) { onClick(item.id) }
    }
}

@Composable
@AppPreview
@Preview
private fun MatchBoardItemPreview() {
    MatchBoardItem(
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