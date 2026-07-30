package com.example.heysports.ui.features.main.tabs.profile.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.extensions.asRatingText
import com.example.heysports.cores.extensions.asStatText
import com.example.heysports.cores.extensions.optionalClickable
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.main.tabs.profile.PlayerRatingSummary
import com.example.heysports.ui.features.main.tabs.profile.PlayerStats
import com.example.heysports.ui.theme.*

@Composable
internal fun OverviewCard(
    stats: PlayerStats,
    rating: PlayerRatingSummary,
    modifier: Modifier = Modifier,
    onOpenReputation: (() -> Unit)?
) {
    ProfileCard(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = size_16dp,
            top = size_14dp,
            end = size_10dp,
            bottom = size_16dp
        )
    ) {
        JPText(
            text = stringResource(R.string.profile_overview),
            color = TextPrimary,
            fontSize = size_18sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OverviewStat(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.SportsSoccer,
                value = stats.matches.asStatText(),
                label = stringResource(R.string.profile_matches)
            )
            StatDivider()
            OverviewStat(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.EmojiEvents,
                iconColor = HeySportsTertiary,
                value = stats.wins.asStatText(),
                label = stringResource(R.string.profile_wins)
            )
            StatDivider()
            OverviewStat(
                modifier = Modifier
                    .weight(1f)
                    .optionalClickable(onOpenReputation),
                icon = Icons.Outlined.Star,
                iconColor = HeySportsTertiary,
                value = rating.score.asRatingText(),
                label = stringResource(R.string.profile_reputation_short),
                showChevron = true
            )
        }
    }
}
