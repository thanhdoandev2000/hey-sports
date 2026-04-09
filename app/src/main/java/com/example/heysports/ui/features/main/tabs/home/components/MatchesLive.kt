package com.example.heysports.ui.features.main.tabs.home.components

import LiveDot
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.cores.JPCard
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.main.tabs.home.MatchLive
import com.example.heysports.ui.features.main.tabs.home.Team
import com.example.heysports.ui.theme.size_12sp
import com.example.heysports.ui.theme.size_13sp
import com.example.heysports.ui.theme.size_4dp

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun MatchesLive(match: MatchLive) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val maxCardWidth = screenWidth * (1.2f / 2f)

    JPCard(
        containerColor = Color.White,
        contentColor = Color.Black,
        modifier = Modifier.widthIn(max = maxCardWidth)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(size_4dp)
        ) {
            LiveDot()
            JPText(
                text = stringResource(R.string.homeLive),
                color = Color.Red,
                fontWeight = FontWeight.SemiBold
            )
            JPText(text = match.matchScore, fontWeight = FontWeight.Bold)
            JPText(text = "(${match.duration}')", color = Color.DarkGray)
        }
        JPText(
            text = stringResource(R.string.commonMatches, match.homeTeam.name, match.awayTeam.name),
            fontWeight = FontWeight.Bold,
            fontSize = size_13sp
        )
        JPText(
            text = match.location,
            maxLines = 1,
            fontSize = size_12sp,
            color = Color.Gray
        )
    }
}

@Composable
@Preview
@AppPreview
private fun MatchesLivePreview() {
    MatchesLive(
        MatchLive(
            id = "1",
            duration = 30,
            location = "Sân Tuyên Sơn, sân 6A",
            matchScore = "2-1",
            homeTeam = Team(id = "1", name = "FC AE Cây Khế"),
            awayTeam = Team(id = "2", name = "FC Lính Mới")
        )
    )
}