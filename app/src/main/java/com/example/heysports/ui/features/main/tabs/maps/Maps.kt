package com.example.heysports.ui.features.main.tabs.maps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.models.app.StyleConfig
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.app.CustomSearchBar
import com.example.heysports.ui.components.cores.JPDialogLoading
import com.example.heysports.ui.components.cores.JPDropdown
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.size_16dp
import com.example.heysports.ui.theme.size_8dp
import com.example.heysports.ui.theme.size_16sp
import com.example.heysports.ui.theme.size_32dp
import com.example.heysports.ui.theme.size_42dp
import com.example.heysports.ui.theme.size_48dp
import com.example.heysports.ui.theme.size_4dp
import com.example.heysports.ui.theme.size_50dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun Maps() {
    MapsScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MapsScreen() {
    var searchText by remember { mutableStateOf("") }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(16.047079, 108.206230), 12f)
    }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    HeySportContainer(isEdgeToEdge = true) {
        JPDialogLoading(isShowing = false)
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            modifier = Modifier.background(Color.White),
            sheetDragHandle = {
                Surface(
                    modifier = Modifier
                        .padding(vertical = size_8dp)
                        .semantics { contentDescription = "" },
                    color = Color.Black,
                    shape = MaterialTheme.shapes.extraLarge,
                ) {
                    Box(Modifier.size(width = size_32dp, height = size_4dp))
                }
            },
            sheetContent = {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.35f)
                ) {
                    JPText(
                        text = stringResource(R.string.mapFootballFields),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = size_16sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            sheetPeekHeight = size_50dp,
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    contentPadding = PaddingValues(bottom = paddingValues.calculateBottomPadding())
                ) {
                    // Map content
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(
                                bottomStart = size_16dp,
                                bottomEnd = size_16dp
                            )
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(
                                bottomStart = size_16dp,
                                bottomEnd = size_16dp
                            )
                        )
                        .statusBarsPadding()
                        .padding(size_16dp)
                ) {
                    CustomSearchBar(
                        textSearch = "",
                        onTextChange = { },
                        placeholder = R.string.mapSearchHint,
                        onSearchExecute = {},
                        modifier = Modifier.height(size_48dp)
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        JPDropdown(
                            config = StyleConfig(
                                mTop = size_8dp,
                                height = size_42dp,
                                label = R.string.mapTime,
                                isEnableLabel = false,
                                isCenterContent = true
                            ),
                            value = null,
                            modifier = Modifier.weight(1f)
                        ) { }
                        JPSpacer(width = size_16dp)
                        JPDropdown(
                            config = StyleConfig(
                                mTop = size_8dp,
                                height = size_42dp,
                                label = R.string.mapLevel,
                                isEnableLabel = false,
                                isCenterContent = true
                            ),
                            value = null,
                            options = listOf("Thanh Doan", "Tien Thanh", "asaasas"),
                            modifier = Modifier.weight(1f)
                        ) { }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
@AppPreview
private fun MapsPreview() {
    MapsScreen()
}