package com.example.heysports.ui.features.main.tabs.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.extensions.optionalClickable
import com.example.heysports.ui.components.app.UserAvatar
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.main.tabs.profile.ProfileTeamSummary
import com.example.heysports.ui.theme.*

@Composable
internal fun MyTeamCard(
    team: ProfileTeamSummary?,
    onOpenTeam: (() -> Unit)?
) {
    ProfileCard(
        modifier = Modifier.optionalClickable(onOpenTeam)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(size_12dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Shield,
                contentDescription = null,
                tint = PrimaryGreen,
                modifier = Modifier.size(size_24dp)
            )
            JPText(
                text = stringResource(R.string.profile_my_team),
                fontSize = size_18sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(size_14dp)
        ) {
            UserAvatar(
                name = team?.name ?: stringResource(R.string.profile_no_team),
                imageUrl = team?.avatar,
                size = 64.dp,
                borderWidth = size_1dp,
                borderColor = PrimaryGreen,
                backgroundColor = Color.White,
                textColor = PrimaryGreen,
                contentDescription = stringResource(
                    R.string.profile_team_avatar_content_description
                )
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(size_3dp)
            ) {
                JPText(
                    text = team?.name ?: stringResource(R.string.profile_no_team),
                    color = TextPrimary,
                    fontSize = size_18sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                JPText(
                    text = team?.description
                        ?.takeIf(String::isNotBlank)
                        ?: stringResource(R.string.profile_team_description),
                    color = TextSecondary,
                    fontSize = size_13sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = TextPrimary,
                modifier = Modifier.size(size_24dp)
            )
        }
    }
}
