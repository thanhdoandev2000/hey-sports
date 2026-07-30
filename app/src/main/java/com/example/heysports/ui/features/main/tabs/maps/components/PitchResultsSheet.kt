package com.example.heysports.ui.features.main.tabs.maps.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heysports.R
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.main.tabs.maps.PitchUiModel
import com.example.heysports.ui.features.navigation.paddingBottomTab
import com.example.heysports.ui.theme.*
import com.google.android.gms.maps.model.LatLng

@Composable
internal fun PitchResultsSheet(
    pitches: List<PitchUiModel>,
    onPitchClick: (PitchUiModel) -> Unit,
    modifier: Modifier = Modifier,
    resultCount: Int = pitches.size
) {
    Column(modifier = modifier.fillMaxWidth()) {
        PitchResultsHeader(resultCount = resultCount)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                start = size_16dp,
                end = size_16dp,
                bottom = paddingBottomTab + size_16dp
            ),
            verticalArrangement = Arrangement.spacedBy(size_12dp)
        ) {
            items(
                items = pitches,
                key = PitchUiModel::id
            ) { pitch ->
                PitchCard(
                    pitch = pitch,
                    onClick = { onPitchClick(pitch) }
                )
            }
        }
    }
}

@Composable
private fun PitchResultsHeader(
    resultCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = size_16dp,
                top = size_4dp,
                end = size_16dp,
                bottom = size_12dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            JPText(
                text = stringResource(R.string.map_nearby_pitches),
                color = TextPrimary,
                fontSize = size_18sp,
                fontWeight = FontWeight.Bold
            )
            JPText(
                text = stringResource(R.string.map_matching_pitch_count, resultCount),
                color = TextSecondary,
                fontSize = size_12sp
            )
        }

        PitchViewToggle()
    }
}

@Composable
private fun PitchViewToggle() {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(size_8dp),
        border = BorderStroke(size_1dp, DividerColor)
    ) {
        Row {
            PitchViewToggleItem(
                icon = Icons.AutoMirrored.Outlined.FormatListBulleted,
                selected = true
            )
            PitchViewToggleItem(
                icon = Icons.Outlined.Map,
                selected = false
            )
        }
    }
}

@Composable
private fun PitchViewToggleItem(
    icon: ImageVector,
    selected: Boolean
) {
    Surface(
        modifier = Modifier
            .size(size_40dp)
            .clickable { },
        color = if (selected) LightGreenBackground else Color.White,
        shape = RoundedCornerShape(size_8dp),
        border = if (selected) {
            BorderStroke(size_1dp, PrimaryGreen)
        } else {
            null
        }
    ) {
        Box(contentAlignment = Alignment.Center) {
            JPIcon(
                icon = icon,
                tint = if (selected) PrimaryGreen else TextSecondary,
                size = size_20dp
            )
        }
    }
}

@Composable
internal fun PitchCard(
    pitch: PitchUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(138.dp)
            .clickable(onClick = onClick),
        color = Color.White,
        shape = RoundedCornerShape(size_12dp),
        border = BorderStroke(size_1dp, DividerColor),
        shadowElevation = size_1dp
    ) {
        Row(modifier = Modifier.padding(size_8dp)) {
            Image(
                painter = painterResource(pitch.photoRes),
                contentDescription = pitch.name,
                modifier = Modifier
                    .width(106.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(size_10dp)),
                contentScale = ContentScale.Crop
            )

            JPSpacer(width = size_10dp)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                JPText(
                    text = pitch.name,
                    color = TextPrimary,
                    fontSize = size_15sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    lineHeight = 18.sp
                )

                JPSpacer(height = 2.dp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    JPIcon(
                        icon = Icons.Outlined.Star,
                        tint = HeySportsTertiary,
                        size = size_14dp
                    )
                    JPSpacer(width = size_4dp)
                    JPText(
                        text = pitch.rating,
                        color = TextPrimary,
                        fontSize = size_12sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 14.sp
                    )
                    JPSpacer(width = size_4dp)
                    JPText(
                        text = "(${pitch.reviewCount})",
                        color = TextSecondary,
                        fontSize = size_11sp,
                        lineHeight = 14.sp
                    )
                }

                JPText(
                    text = pitch.distanceAndArea,
                    color = TextSecondary,
                    fontSize = size_11sp,
                    maxLines = 1,
                    lineHeight = 14.sp
                )

                JPSpacer(height = 2.dp)
                Row(horizontalArrangement = Arrangement.spacedBy(size_6dp)) {
                    pitch.pitchTypes.forEach { type ->
                        PitchTypeChip(type = type)
                    }
                }

                JPSpacer(height = 2.dp)
                JPText(
                    text = pitch.availability,
                    color = PrimaryGreen,
                    fontSize = size_11sp,
                    maxLines = 1,
                    lineHeight = 14.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        JPText(
                            text = stringResource(R.string.map_price_from),
                            color = TextPrimary,
                            fontSize = size_9sp,
                            lineHeight = 12.sp
                        )
                        JPSpacer(width = size_4dp)
                        JPText(
                            text = pitch.price,
                            color = PrimaryGreen,
                            fontSize = size_13sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 15.sp
                        )
                        JPText(
                            text = stringResource(R.string.map_price_per_hour),
                            color = TextPrimary,
                            fontSize = size_9sp,
                            lineHeight = 12.sp
                        )
                    }

                    Surface(
                        modifier = Modifier
                            .height(32.dp)
                            .clickable(onClick = onClick),
                        color = Color.White,
                        shape = RoundedCornerShape(size_8dp),
                        border = BorderStroke(size_1dp, PrimaryGreen)
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = size_8dp),
                            contentAlignment = Alignment.Center
                        ) {
                            JPText(
                                text = stringResource(R.string.map_view_pitch),
                                color = PrimaryGreen,
                                fontSize = size_11sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PitchTypeChip(type: String) {
    Surface(
        color = LightGreenBackground,
        shape = RoundedCornerShape(size_4dp)
    ) {
        JPText(
            text = type,
            color = PrimaryGreen,
            fontSize = size_9sp,
            modifier = Modifier.padding(
                horizontal = size_6dp,
                vertical = 2.dp
            ),
            lineHeight = 12.sp
        )
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun PitchResultsSheetPreview() {
    HeySportsTheme {
        PitchResultsSheet(
            pitches = listOf(
                PitchUiModel(
                    id = 1,
                    name = "Sân bóng Tuyên Sơn",
                    rating = "4.8",
                    reviewCount = 128,
                    distanceAndArea = "1,2 km • Hải Châu",
                    pitchTypes = listOf("Sân 5", "Sân 7"),
                    availability = "Còn 3 khung giờ",
                    price = "120.000đ",
                    photoRes = R.drawable.pitch_tuyen_son,
                    position = LatLng(16.047079, 108.206230)
                )
            ),
            onPitchClick = {}
        )
    }
}
