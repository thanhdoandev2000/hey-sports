package com.example.heysports.ui.features.main.tabs.maps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.features.main.tabs.maps.components.FindPitchSearchPanel
import com.example.heysports.ui.features.main.tabs.maps.components.PitchMapMarker
import com.example.heysports.ui.features.main.tabs.maps.components.PitchResultsSheet
import com.example.heysports.ui.theme.BgColorPage
import com.example.heysports.ui.theme.DividerColor
import com.example.heysports.ui.theme.HeySportsTheme
import com.example.heysports.ui.theme.PrimaryGreen
import com.example.heysports.ui.theme.size_1dp
import com.example.heysports.ui.theme.size_4dp
import com.example.heysports.ui.theme.size_8dp
import com.example.heysports.ui.theme.size_16dp
import com.example.heysports.ui.theme.size_20dp
import com.example.heysports.ui.theme.size_48dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState

@Composable
fun Maps() {
    MapsScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MapsScreen() {
    var searchText by rememberSaveable { mutableStateOf("") }
    var selectedPitchId by rememberSaveable { mutableLongStateOf(1L) }
    val pitches = samplePitches()
    val filteredPitches = pitches.filter { pitch ->
        searchText.isBlank() ||
            pitch.name.contains(searchText.trim(), ignoreCase = true) ||
            pitch.distanceAndArea.contains(searchText.trim(), ignoreCase = true)
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(16.047079, 108.206230),
            13.4f
        )
    }
    val bottomSheetState = rememberBottomSheetScaffoldState()

    HeySportContainer(isEdgeToEdge = true) {
        BottomSheetScaffold(
            scaffoldState = bottomSheetState,
            containerColor = BgColorPage,
            sheetContainerColor = Color.White,
            sheetContentColor = Color.Black,
            sheetShape = RoundedCornerShape(
                topStart = size_20dp,
                topEnd = size_20dp
            ),
            sheetShadowElevation = size_8dp,
            sheetPeekHeight = 470.dp,
            sheetDragHandle = {
                Surface(
                    modifier = Modifier.padding(vertical = size_8dp),
                    color = DividerColor,
                    shape = CircleShape
                ) {
                    Box(
                        modifier = Modifier.size(
                            width = 40.dp,
                            height = size_4dp
                        )
                    )
                }
            },
            sheetContent = {
                PitchResultsSheet(
                    pitches = filteredPitches,
                    resultCount = if (searchText.isBlank()) {
                        12
                    } else {
                        filteredPitches.size
                    },
                    onPitchClick = { pitch ->
                        selectedPitchId = pitch.id
                    }
                )
            }
        ) { sheetPadding ->
            Box(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    contentPadding = PaddingValues(
                        bottom = sheetPadding.calculateBottomPadding()
                    ),
                    uiSettings = MapUiSettings(
                        compassEnabled = false,
                        indoorLevelPickerEnabled = false,
                        mapToolbarEnabled = false,
                        myLocationButtonEnabled = false,
                        rotationGesturesEnabled = true,
                        scrollGesturesEnabled = true,
                        scrollGesturesEnabledDuringRotateOrZoom = true,
                        tiltGesturesEnabled = false,
                        zoomControlsEnabled = false,
                        zoomGesturesEnabled = true
                    )
                ) {
                    filteredPitches.forEach { pitch ->
                        val isSelected = selectedPitchId == pitch.id

                        MarkerComposable(
                            pitch.id,
                            isSelected,
                            state = rememberUpdatedMarkerState(pitch.position),
                            contentDescription = pitch.name,
                            anchor = Offset(0.5f, 1f),
                            zIndex = if (isSelected) 2f else 1f,
                            onClick = {
                                selectedPitchId = pitch.id
                                true
                            }
                        ) {
                            PitchMapMarker(selected = isSelected)
                        }
                    }
                }

                FindPitchSearchPanel(
                    searchText = searchText,
                    onSearchTextChange = { searchText = it },
                    onSearch = {},
                    modifier = Modifier.align(Alignment.TopCenter)
                )

                CurrentLocationButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(
                            end = size_16dp,
                            bottom = sheetPadding.calculateBottomPadding() + size_16dp
                        )
                )
            }
        }
    }
}

@Composable
private fun CurrentLocationButton(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.size(size_48dp),
        color = Color.White,
        shape = CircleShape,
        border = BorderStroke(size_1dp, DividerColor),
        shadowElevation = size_4dp,
        onClick = {}
    ) {
        Box(contentAlignment = Alignment.Center) {
            JPIcon(
                icon = Icons.Outlined.MyLocation,
                tint = PrimaryGreen,
                size = 24.dp
            )
        }
    }
}

@Composable
private fun samplePitches(): List<PitchUiModel> {
    return listOf(
        PitchUiModel(
            id = 1L,
            name = stringResource(R.string.map_sample_tuyen_son_name),
            rating = "4.8",
            reviewCount = 128,
            distanceAndArea = stringResource(R.string.map_sample_tuyen_son_distance),
            pitchTypes = listOf(
                stringResource(R.string.map_pitch_type_five),
                stringResource(R.string.map_pitch_type_seven)
            ),
            availability = stringResource(R.string.map_sample_tuyen_son_availability),
            price = "120.000đ",
            photoRes = R.drawable.pitch_tuyen_son,
            position = LatLng(16.047079, 108.206230)
        ),
        PitchUiModel(
            id = 2L,
            name = stringResource(R.string.map_sample_trung_vuong_name),
            rating = "4.6",
            reviewCount = 86,
            distanceAndArea = stringResource(R.string.map_sample_trung_vuong_distance),
            pitchTypes = listOf(stringResource(R.string.map_pitch_type_five)),
            availability = stringResource(R.string.map_sample_trung_vuong_availability),
            price = "150.000đ",
            photoRes = R.drawable.pitch_trung_vuong,
            position = LatLng(16.067286, 108.211842)
        ),
        PitchUiModel(
            id = 3L,
            name = stringResource(R.string.map_sample_chuyen_viet_name),
            rating = "4.7",
            reviewCount = 64,
            distanceAndArea = stringResource(R.string.map_sample_chuyen_viet_distance),
            pitchTypes = listOf(stringResource(R.string.map_pitch_type_seven)),
            availability = stringResource(R.string.map_sample_chuyen_viet_availability),
            price = "180.000đ",
            photoRes = R.drawable.pitch_tuyen_son,
            position = LatLng(16.038752, 108.220166)
        )
    )
}

@Composable
@Preview(showBackground = true, widthDp = 390, heightDp = 780)
@AppPreview
private fun MapsPreview() {
    HeySportsTheme {
        MapsScreen()
    }
}
