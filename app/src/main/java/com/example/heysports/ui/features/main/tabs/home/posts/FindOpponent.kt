package com.example.heysports.ui.features.main.tabs.home.posts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Balance
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.heysports.R
import com.example.heysports.cores.models.StyleConfig
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.models.enums.EMatchType
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.app.CustomLine
import com.example.heysports.ui.components.app.JPAttachPhoto
import com.example.heysports.ui.components.cores.*
import com.example.heysports.ui.features.main.tabs.home.components.JPChipGroup
import com.example.heysports.ui.theme.*

@Composable
fun FindOpponent() {
    FindOpponentScreen()
}

@Composable
private fun FindOpponentScreen() {
    var selectedType by remember { mutableStateOf(EMatchType.FIVE_VS_FIVE) }

    HeySportContainer(title = stringResource(R.string.postOpponentTitle), isEdgeToEdge = false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            JPCard(
                modifier = Modifier.padding(size_12dp),
                containerColor = Color.White,
                space = size_8dp
            ) {
                JPText(
                    text = stringResource(R.string.postOpponentMatchType),
                    fontSize = size_15sp,
                    fontWeight = FontWeight.Medium
                )

                JPChipGroup(
                    items = EMatchType.entries,
                    selected = selectedType,
                    onSelected = { selectedType = it },
                    label = { it.label }
                )
            }
            JPCard(
                modifier = Modifier.padding(size_12dp),
                containerColor = Color.White,
                space = size_8dp
            ) {
                JPText(
                    text = stringResource(R.string.postOpponentMatchDetail),
                    fontSize = size_15sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                JPDropdown(
                    modifier = Modifier,
                    value = null,
                    config = StyleConfig(
                        label = R.string.postOpponentChooseTimeLabel,
                        placeholder = R.string.postOpponentChooseTimeHint,
                        icon = Icons.Outlined.CalendarMonth,
                        isTextPrimaryColor = true,
                        isSelectHiltForLabel = true
                    )
                ) {

                }
                JPDropdown(
                    modifier = Modifier,
                    value = null,
                    config = StyleConfig(
                        label = R.string.postOpponentChooseLocationLabel,
                        placeholder = R.string.postOpponentChooseLocationHint,
                        icon = Icons.Outlined.LocationOn,
                        isTextPrimaryColor = true,
                        mTop = size_8dp,
                        isSelectHiltForLabel = true
                    )
                ) {

                }
                JPDropdown(
                    modifier = Modifier,
                    value = null,
                    config = StyleConfig(
                        label = R.string.postOpponentChooseCostLabel,
                        placeholder = R.string.postOpponentChooseCost,
                        icon = Icons.Outlined.Balance,
                        isTextPrimaryColor = true,
                        mTop = size_8dp,
                        isSelectHiltForLabel = true
                    )
                ) {

                }
            }

            JPCard(
                modifier = Modifier.padding(size_12dp),
                containerColor = Color.White,
                space = size_16dp
            ) {
                JPText(
                    text = stringResource(R.string.postOpponentDescription),
                    fontSize = size_15sp,
                    fontWeight = FontWeight.Medium
                )
                JPInput(
                    modifier = Modifier,
                    value = "",
                    config = StyleConfig(
                        minLines = 3,
                        maxLines = 5,
                        mTop = size_0,
                        placeholder = R.string.postOpponentDescHint,
                        isComment = true
                    )
                ) {}
                CustomLine()
                JPAttachPhoto()
            }

            JPCard(
                modifier = Modifier.padding(size_12dp),
                containerColor = Color.White,
                padding = size_12dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        JPText(
                            text = stringResource(R.string.postOpponentShowMyTeam),
                            fontSize = size_14sp,
                            fontWeight = FontWeight.Medium
                        )
                        JPText(
                            text = stringResource(R.string.postOpponentShowMyTeamDesc),
                            fontSize = size_12sp,
                            color = TextSecondary
                        )
                    }
                    JPSwitch(checked = true, onCheckedChange = {})
                }
            }
        }
    }
}

@AppPreview
@Preview
@Composable
fun FindOpponentPreview() {
    FindOpponent()
}