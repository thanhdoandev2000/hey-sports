package com.example.heysports.ui.features.main.tabs.maps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.model.app.StyleConfig
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.app.CustomSearchBar
import com.example.heysports.ui.components.cores.JPDropdown
import com.example.heysports.ui.components.cores.JPInput
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.theme.paddingDefault
import com.example.heysports.ui.theme.paddingSmall
import com.example.heysports.ui.theme.size_40dp
import com.example.heysports.ui.theme.size_42dp
import com.example.heysports.ui.theme.size_48dp

@Composable
fun Maps() {
    MapsScreen()
}

@Composable
private fun MapsScreen() {
    var searchText by remember { mutableStateOf("") }

    HeySportContainer {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .statusBarsPadding()
                .padding(paddingDefault)
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
                        mTop = paddingSmall,
                        height = size_42dp,
                        label = R.string.mapTime,
                        isEnableLabel = false,
                        isCenterContent = true
                    ),
                    value = null,
                    modifier = Modifier.weight(1f)
                ) { }
                JPSpacer(width = paddingDefault)
                JPDropdown(
                    config = StyleConfig(
                        mTop = paddingSmall,
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
        Column(Modifier.padding(paddingDefault)) {
            JPInput(
                config = StyleConfig(label = R.string.mapTime),
                value = searchText,
                onValueChange = { searchText = it })
            JPDropdown(
                value = null,
                config = StyleConfig(label = R.string.mapLevel),
                options = listOf(
                    "Thanh Doan a ss s as sa s aas as  s as  assa sa sa sa sa  as sa s as as s sa sa sa sa sa sas a sa sa sa sa sa sa sa sa sa as s aas ",
                    "Đoàn Tiến Thành",
                    "Tien Thanh",
                    "asaasas"
                )
            ) { }
        }
    }
}

@Preview
@AppPreview
@Composable
private fun MapsPreview() {
    MapsScreen()
}