package com.example.heysports.ui.features.main.tabs.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.app.SpaceContent
import com.example.heysports.ui.components.cores.*
import com.example.heysports.ui.features.main.tabs.home.NextMatches
import com.example.heysports.ui.theme.*

@Composable
private fun TimerChip(
    modifier: Modifier = Modifier,
    time: String = "00",
    type: String = "Giờ",
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CardShape,
            color = Color.White.copy(alpha = 0.15f),
            modifier = Modifier.wrapContentSize()
        ) {
            JPText(
                text = time,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = size_20sp,
                modifier = Modifier.padding(size_12dp)
            )
        }
        JPSpacer(height = size_4dp)
        JPText(text = type, color = Color.White)
    }
}

@Composable
fun UpcomingMatchCard(
    matches: NextMatches,
    onMarkAttendance: (String) -> Unit = {},
    onOpenMaps: (String) -> Unit = {}
) {
    JPCard(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.background(LightGreenBackground, shape = CardShape)) {
                JPIcon(
                    icon = Icons.Outlined.AccessTime,
                    tint = PrimaryGreen,
                    modifier = Modifier.padding(size_4dp)
                )
            }
            JPSpacer(width = size_4dp)
            JPText(
                text = stringResource(R.string.homeNextMatches),
                color = PrimaryGreen,
                fontWeight = FontWeight.Bold,
                fontSize = size_15sp
            )
        }
        JPSpacer(height = size_8dp)
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
                text = "Bắt đầu sau",
                color = Color.White.copy(0.6f),
                fontSize = size_12sp,
                fontWeight = FontWeight.SemiBold
            )
            JPSpacer(height = size_4dp)
            Row(Modifier.wrapContentSize()) {
                TimerChip()
                SpaceContent()
                TimerChip(type = "Phút")
                SpaceContent()
                TimerChip(type = "Giây")
            }
        }
        JPSpacer(height = size_10dp)
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
        JPSpacer(height = size_6dp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            JPIcon(icon = Icons.Filled.LocationOn, tint = Color.Gray)
            JPSpacer(width = size_4dp)
            JPText(text = matches.location, color = Color.DarkGray)
        }
        JPSpacer(height = size_6dp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            JPIcon(icon = Icons.Filled.Group, tint = Color.Gray)
            JPSpacer(width = size_4dp)
            JPText(
                text = stringResource(
                    R.string.commonMatches,
                    matches.homeTeam.name,
                    matches.awayTeam.name
                ),
                color = Color.DarkGray
            )
        }
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
            ) { onOpenMaps(matches.id) }
            JPSpacer(width = size_16dp)
            JPButton(
                label = R.string.homeAttendance,
                modifier = Modifier.weight(1f),
                height = size_42dp
            ) { onMarkAttendance(matches.id) }
        }
    }
}

@Composable
@Preview
@AppPreview
private fun UpcomingMatchCardPreview() {
    UpcomingMatchCard(
        matches = NextMatches(
            id = "1",
            dateTime = "18:00 - Tối Nay",
            location = "Tuyên Sơn",
            homeTeam = com.example.heysports.ui.features.main.tabs.home.Team(
                id = "1",
                name = "FC AE Cây Khế"
            ),
            awayTeam = com.example.heysports.ui.features.main.tabs.home.Team(
                id = "2",
                name = "FC Lính Mới"
            )
        )
    )
}