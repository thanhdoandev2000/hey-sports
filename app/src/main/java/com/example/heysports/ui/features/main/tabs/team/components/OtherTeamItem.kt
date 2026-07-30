package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.heysports.R
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@Composable
internal fun OtherTeamItem() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        color = Color.White,
        shape = RoundedCornerShape(size_8dp),
        border = BorderStroke(size_line, DividerColor)
    ) {
        Row(
            modifier = Modifier.padding(size_12dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TeamLogo(size = size_48dp)
            JPSpacer(width = size_12dp)
            Column(modifier = Modifier.weight(1f)) {
                JPText(
                    text = stringResource(R.string.team_sample_secondary_name),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = size_16sp
                )
                JPSpacer(height = size_2dp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    JPText(
                        text = stringResource(R.string.team_member_count, 12),
                        fontSize = size_12sp,
                        color = TextSecondary
                    )
                    JPSpacer(width = size_8dp)
                    Surface(
                        color = LightGreenBackground,
                        shape = RoundedCornerShape(size_4dp)
                    ) {
                        JPText(
                            text = stringResource(R.string.team_member_role),
                            color = PrimaryGreen,
                            fontSize = size_10sp,
                            modifier = Modifier.padding(
                                horizontal = size_8dp,
                                vertical = size_4dp
                            )
                        )
                    }
                }
            }
            JPIcon(
                icon = Icons.Default.ChevronRight,
                tint = TextSecondary,
                size = size_24dp
            )
        }
    }
}
