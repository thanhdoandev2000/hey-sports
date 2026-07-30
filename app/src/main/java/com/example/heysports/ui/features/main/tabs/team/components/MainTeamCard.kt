package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@Composable
internal fun MainTeamCard() {
    val shape = RoundedCornerShape(size_12dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFF075E20), Color(0xFF0D7A2C))
                )
            )
            .border(size_1dp, Color(0xFF07531D), shape)
    ) {
        TeamCardPattern(modifier = Modifier.matchParentSize())

        Column(modifier = Modifier.padding(size_16dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    TeamLogo(
                        size = 86.dp,
                        borderColor = Color.White
                    )
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(size_30dp),
                        color = GreenLight,
                        shape = CircleShape,
                        border = BorderStroke(size_2dp, Color.White)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            JPIcon(
                                icon = Icons.Default.Verified,
                                tint = Color.White,
                                size = size_20dp
                            )
                        }
                    }
                }

                JPSpacer(width = size_16dp)
                Column(modifier = Modifier.weight(1f)) {
                    JPText(
                        text = stringResource(R.string.team_sample_primary_name),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = size_20sp
                    )
                    JPText(
                        text = stringResource(R.string.team_sample_location),
                        color = Color.White.copy(alpha = 0.88f),
                        fontSize = size_13sp
                    )
                    JPSpacer(height = size_6dp)
                    Surface(
                        color = Color.White.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(size_4dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                horizontal = size_8dp,
                                vertical = size_4dp
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            JPIcon(
                                icon = Icons.Default.WorkspacePremium,
                                tint = HeySportsTertiary,
                                size = size_12dp
                            )
                            JPSpacer(width = size_4dp)
                            JPText(
                                text = stringResource(R.string.team_captain),
                                color = Color.White,
                                fontSize = size_11sp
                            )
                        }
                    }
                }
            }

            JPSpacer(height = size_16dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatItem(
                    value = "18",
                    label = stringResource(R.string.team_members),
                    modifier = Modifier.weight(1f)
                )
                StatDivider()
                StatItem(
                    value = "8",
                    label = stringResource(R.string.team_matches),
                    modifier = Modifier.weight(1f)
                )
                StatDivider()
                StatItem(
                    value = "5",
                    label = stringResource(R.string.team_wins),
                    modifier = Modifier.weight(1f)
                )
                StatDivider()
                StatItem(
                    value = "62%",
                    label = stringResource(R.string.team_win_rate),
                    modifier = Modifier.weight(1f)
                )
            }

            JPSpacer(height = size_16dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(size_10dp)
            ) {
                TeamCardActionButton(
                    label = stringResource(R.string.team_manage),
                    icon = Icons.Default.Settings,
                    modifier = Modifier.weight(1f),
                    filled = false
                )
                TeamCardActionButton(
                    label = stringResource(R.string.team_invite_member),
                    icon = Icons.Default.PersonAddAlt,
                    modifier = Modifier.weight(1f),
                    filled = true
                )
            }
        }
    }
}
