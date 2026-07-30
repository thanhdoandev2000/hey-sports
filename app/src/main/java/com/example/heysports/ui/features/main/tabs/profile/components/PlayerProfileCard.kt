package com.example.heysports.ui.features.main.tabs.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.heysports.R
import com.example.heysports.cores.extensions.optionalClickable
import com.example.heysports.cores.extensions.orDefaultIfBlank
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.main.tabs.profile.PlayerProfileSummary
import com.example.heysports.ui.theme.*

@Composable
internal fun PlayerProfileCard(
    profile: PlayerProfileSummary,
    onEditProfile: (() -> Unit)?
) {
    ProfileCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.PersonOutline,
                contentDescription = null,
                tint = PrimaryGreen,
                modifier = Modifier.size(size_24dp)
            )
            JPText(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = size_12dp),
                text = stringResource(R.string.profile_player_profile),
                fontSize = size_18sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.optionalClickable(onEditProfile),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(size_6dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = null,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(size_20dp)
                )
                JPText(
                    text = stringResource(R.string.profile_edit),
                    color = PrimaryGreen,
                    fontSize = size_14sp
                )
            }
        }
        HorizontalDivider(color = DividerColor, thickness = size_line)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(size_8dp)
        ) {
            ProfileAttribute(
                modifier = Modifier.weight(1f),
                icon = Icons.AutoMirrored.Outlined.DirectionsRun,
                label = stringResource(R.string.profile_position),
                value = profile.position.orDefaultIfBlank(
                    stringResource(R.string.profile_not_updated)
                )
            )
            ProfileAttribute(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.BarChart,
                label = stringResource(R.string.profile_level),
                value = profile.skillLevel.orDefaultIfBlank(
                    stringResource(R.string.profile_not_updated)
                )
            )
            ProfileAttribute(
                modifier = Modifier.weight(1.2f),
                icon = Icons.Outlined.LocationOn,
                label = stringResource(R.string.profile_area),
                value = profile.area.orDefaultIfBlank(
                    stringResource(R.string.profile_not_updated)
                )
            )
        }
    }
}
