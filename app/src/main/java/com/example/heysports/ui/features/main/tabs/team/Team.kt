package com.example.heysports.ui.features.main.tabs.team

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.heysports.R
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.features.main.tabs.team.components.EmptyTeamContent
import com.example.heysports.ui.features.main.tabs.team.components.HasTeamContent
import com.example.heysports.ui.features.main.tabs.team.components.TeamHeaderAction
import com.example.heysports.ui.theme.HeySportsTheme

@Composable
fun Team() {
    var hasTeam by rememberSaveable { mutableStateOf(false) }

    TeamScreen(
        hasTeam = hasTeam,
        onToggleState = { hasTeam = !hasTeam }
    )
}

@Composable
internal fun TeamScreen(
    hasTeam: Boolean,
    onToggleState: () -> Unit
) {
    HeySportContainer(
        title = stringResource(R.string.team_my_team),
        subTitle = if (hasTeam) {
            stringResource(R.string.team_count, 2)
        } else {
            R.string.team_manage_desc
        },
        canNavigateBack = false,
        actions = {
            TeamHeaderAction(
                hasTeam = hasTeam,
                onClick = onToggleState
            )
        }
    ) {
        if (hasTeam) {
            HasTeamContent()
        } else {
            EmptyTeamContent()
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 780)
@Composable
private fun TeamPreview() {
    HeySportsTheme {
        TeamScreen(
            hasTeam = true,
            onToggleState = {}
        )
    }
}
