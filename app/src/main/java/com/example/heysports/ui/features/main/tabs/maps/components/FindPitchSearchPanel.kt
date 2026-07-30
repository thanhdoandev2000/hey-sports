package com.example.heysports.ui.features.main.tabs.maps.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Stadium
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.heysports.R
import com.example.heysports.ui.components.app.JPSearchBar
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@Composable
internal fun FindPitchSearchPanel(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDateClick: () -> Unit = {},
    onTimeClick: () -> Unit = {},
    onPitchTypeClick: () -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = PrimaryGreen,
        shape = RoundedCornerShape(
            bottomStart = size_16dp,
            bottomEnd = size_16dp
        ),
        shadowElevation = size_4dp
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(
                    start = size_16dp,
                    top = size_12dp,
                    end = size_16dp,
                    bottom = size_12dp
                )
        ) {
            JPSearchBar(
                textSearch = searchText,
                onTextChange = onSearchTextChange,
                onSearchExecute = onSearch,
                placeholder = R.string.map_find_pitch_search_hint,
                color = Color.White,
                radius = size_10dp,
                modifier = Modifier
                    .height(size_48dp)
                    .fillMaxWidth()
                    .border(
                        width = size_1dp,
                        color = DividerColor,
                        shape = RoundedCornerShape(size_10dp)
                    )
            )

            JPSpacer(height = size_10dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(size_8dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PitchFilterChip(
                    label = stringResource(R.string.map_filter_today),
                    icon = Icons.Outlined.CalendarMonth,
                    modifier = Modifier.weight(1f),
                    onClick = onDateClick
                )
                PitchFilterChip(
                    label = stringResource(R.string.map_filter_default_time),
                    icon = Icons.Outlined.Schedule,
                    modifier = Modifier.weight(1.35f),
                    onClick = onTimeClick
                )
                PitchFilterChip(
                    label = stringResource(R.string.map_filter_pitch_five),
                    icon = Icons.Outlined.Stadium,
                    modifier = Modifier.weight(0.9f),
                    onClick = onPitchTypeClick
                )
                Surface(
                    modifier = Modifier
                        .size(size_48dp)
                        .clickable(onClick = onFilterClick),
                    color = Color.White.copy(alpha = 0.14f),
                    shape = RoundedCornerShape(size_10dp),
                    border = BorderStroke(
                        size_1dp,
                        Color.White.copy(alpha = 0.35f)
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        JPIcon(
                            icon = Icons.Outlined.FilterList,
                            tint = Color.White,
                            size = size_20dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PitchFilterChip(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(size_48dp)
            .clickable(onClick = onClick),
        color = Color.White.copy(alpha = 0.14f),
        shape = RoundedCornerShape(size_10dp),
        border = BorderStroke(
            width = size_1dp,
            color = Color.White.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = size_8dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            JPIcon(
                icon = icon,
                tint = Color.White,
                size = size_20dp
            )
            JPSpacer(width = size_4dp)
            JPText(
                text = label,
                color = Color.White,
                fontSize = size_13sp,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun FindPitchSearchPanelPreview() {
    HeySportsTheme {
        FindPitchSearchPanel(
            searchText = "",
            onSearchTextChange = {},
            onSearch = {}
        )
    }
}
