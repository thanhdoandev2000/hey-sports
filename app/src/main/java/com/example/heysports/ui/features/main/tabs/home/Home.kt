package com.example.heysports.ui.features.main.tabs.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.noPadding
import com.example.heysports.ui.theme.paddingDefault
import com.example.heysports.ui.theme.paddingMedium
import com.example.heysports.ui.theme.paddingSmall
import com.example.heysports.ui.theme.size_110dp
import com.example.heysports.ui.theme.size_13sp
import com.example.heysports.ui.theme.size_15sp
import com.example.heysports.ui.theme.size_16sp

@Composable
fun Home() {
    HomeScreen()
}

@Composable
private fun HomeScreen() {
    HeySportContainer() {
        Row(
            modifier = Modifier
                .padding(top = noPadding, bottom = paddingMedium)
                .height(size_110dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            JPSpacer(width = paddingDefault)
            Card(
                modifier = Modifier.padding(paddingSmall),
                shape = CircleShape
            ) {
                JPText(
                    text = "TD",
                    fontSize = size_16sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(paddingSmall)
                )
            }
            Column(Modifier.weight(1f)) {
                JPText(text = "Xin Chào!", color = Color.White, fontSize = size_13sp)
                JPText(
                    text = "Đoàn Tiến Thành",
                    color = Color.White,
                    fontSize = size_15sp,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "",
                    tint = Color.White
                )
            }
            JPSpacer(width = paddingDefault)
        }
    }
}

@Composable
@Preview
@AppPreview
private fun HomePreview() {
    HomeScreen()
}