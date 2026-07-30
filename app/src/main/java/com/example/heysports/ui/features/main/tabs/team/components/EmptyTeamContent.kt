package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heysports.R
import com.example.heysports.ui.components.cores.JPButton
import com.example.heysports.ui.components.cores.JPOutlineButton
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.navigation.paddingBottomTab
import com.example.heysports.ui.theme.*

@Composable
internal fun EmptyTeamContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(
            start = size_16dp,
            top = size_20dp,
            end = size_16dp,
            bottom = paddingBottomTab + size_24dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(
                painter = painterResource(R.drawable.illustration_empty_team),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),
                contentScale = ContentScale.Fit
            )
        }

        item {
            JPText(
                text = stringResource(R.string.team_empty_title),
                fontSize = size_22sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = TextPrimary
            )
        }

        item {
            JPSpacer(height = size_8dp)
            JPText(
                text = stringResource(R.string.team_empty_desc),
                modifier = Modifier.padding(horizontal = size_12dp),
                fontSize = size_14sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }

        item {
            JPSpacer(height = size_28dp)
            JPButton(
                label = R.string.team_create_new,
                icon = Icons.Default.AddCircleOutline,
                modifier = Modifier.fillMaxWidth(),
                height = size_54dp,
                mTop = size_0
            )
        }

        item {
            JPOutlineButton(
                label = R.string.team_join_by_code,
                icon = Icons.Default.QrCodeScanner,
                modifier = Modifier.fillMaxWidth(),
                height = size_54dp,
                mTop = size_12dp,
                borderColor = PrimaryGreen,
                contentColor = PrimaryGreen,
                onClick = {}
            )
        }

        item {
            JPSpacer(height = size_24dp)
            InvitationCard()
        }
    }
}
