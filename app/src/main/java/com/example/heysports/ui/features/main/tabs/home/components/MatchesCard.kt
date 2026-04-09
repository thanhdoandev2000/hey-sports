package com.example.heysports.ui.features.main.tabs.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.cores.JPButton
import com.example.heysports.ui.components.cores.JPCard
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.main.tabs.home.NextMatches
import com.example.heysports.ui.features.main.tabs.team.Team
import com.example.heysports.ui.theme.paddingMedium
import com.example.heysports.ui.theme.radiusDefault
import com.example.heysports.ui.theme.size_15sp
import com.example.heysports.ui.theme.size_42dp
import com.example.heysports.ui.theme.size_4dp

@Composable
fun MatchesCard(
    matches: NextMatches,
    onMarkAttendance: (String) -> Unit = {},
    onOpenMaps: (String) -> Unit = {}
) {
    JPCard(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            JPIcon(icon = Icons.Outlined.LocalFireDepartment, tint = Color.Red)
            JPSpacer(width = size_4dp)
            JPText(
                text = stringResource(R.string.homeNextMatches),
                color = Color.Red,
                fontWeight = FontWeight.SemiBold,
                fontSize = size_15sp
            )
        }

        JPSpacer(height = 16.dp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            JPIcon(icon = Icons.Outlined.AccessTime, tint = Color.Black)
            JPSpacer(width = size_4dp)
            JPText(text = matches.dateTime)
        }
        JPSpacer(height = radiusDefault)
        Row(verticalAlignment = Alignment.CenterVertically) {
            JPIcon(icon = Icons.Outlined.LocationOn, tint = Color.Black)
            JPSpacer(width = size_4dp)
            JPText(text = matches.location)
        }
        JPSpacer(height = radiusDefault)
        Row(verticalAlignment = Alignment.CenterVertically) {
            JPIcon(icon = Icons.Outlined.Group, tint = Color.Black)
            JPSpacer(width = size_4dp)
            JPText(
                text = stringResource(
                    R.string.commonMatches,
                    matches.homeTeam.name,
                    matches.awayTeam.name
                )
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
                border = BorderStroke(1.dp, Color.Gray),
                bgColor = Color.White,
                textColor = Color.Black,
                height = size_42dp
            ) { onOpenMaps(matches.id) }
            JPSpacer(width = paddingMedium)
            JPButton(
                label = R.string.homeAttendance,
                modifier = Modifier.weight(1f),
                height = size_42dp
            ) { onMarkAttendance(matches.id) }
        }
    }
}

@Preview
@Composable
@AppPreview
private fun MatchesCardPreview() {
    MatchesCard(
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