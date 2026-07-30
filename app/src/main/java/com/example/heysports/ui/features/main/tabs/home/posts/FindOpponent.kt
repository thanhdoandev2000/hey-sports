package com.example.heysports.ui.features.main.tabs.home.posts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.heysports.R
import com.example.heysports.cores.models.StyleConfig
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.cores.utils.DateTimeUtils.convertMatchTimeString
import com.example.heysports.data.models.enums.EDropdownType
import com.example.heysports.data.models.enums.EMatchType
import com.example.heysports.domain.models.PitchSelectionModel
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.app.JPChipIcon
import com.example.heysports.ui.components.app.JPDateTimePickerSheet
import com.example.heysports.ui.components.app.JPPitchPickerBottomSheet
import com.example.heysports.ui.components.cores.*
import com.example.heysports.ui.features.main.tabs.home.components.JPChipGroup
import com.example.heysports.ui.theme.*

@Composable
fun FindOpponent(
    viewModel: MatchRequestViewModel,
    onBack: () -> Unit = {}
) {
    var isVisibleMore by rememberSaveable { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pitches by viewModel.pitches.collectAsStateWithLifecycle()

    FindOpponentScreen(
        uiState,
        pitches = pitches,
        onGetPitches = viewModel::getPitches,
        onSubmit = viewModel::createMatchRequest,
        updateUiState = viewModel::updateUiState,
        onVisibleMore = { isVisibleMore = true },
        onBack = onBack
    )

    MoreInformationModal(
        uiState = uiState.moreInfo,
        photos = uiState.photos,
        visible = isVisibleMore,
        onApply = { viewModel.updateUiState(MatchRequestEffect.OnUpdateMoreInfo(it)) },
        onPhotoAdded = { viewModel.updateUiState(MatchRequestEffect.OnPhotoAdded(it)) },
        onDismiss = { isVisibleMore = false }
    )
}

@Composable
private fun FindOpponentScreen(
    uiState: MatchRequestUiState = MatchRequestUiState(false),
    pitches: SelectionModel<PitchSelectionModel>? = null,
    onGetPitches: (search: String) -> Unit = {},
    onSubmit: () -> Unit = {},
    updateUiState: (effect: MatchRequestEffect) -> Unit = {},
    onVisibleMore: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    HeySportContainer(
        title = stringResource(R.string.postOpponentTitle),
        subTitle = R.string.createMatchInShortTime,
        isEdgeToEdge = false,
        isLoading = uiState.isLoading,
        actions = {
            JPOutlineButton(
                onClick = {},
                mTop = size_0,
                height = size_36dp,
                isWrapContent = true,
                pHoz = size_4dp,
                borderColor = Color.White,
                content = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(size_4dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        JPIcon(
                            icon = Icons.Outlined.Visibility,
                            tint = Color.White,
                            size = size_18dp
                        )
                        JPText(
                            text = stringResource(R.string.viewBefore),
                            color = Color.White,
                            fontSize = size_12sp
                        )
                    }
                })
        },
        onNavigateUp = onBack,
        bottomContent = {
            JPButton(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(size_0)
                    .padding(horizontal = size_16dp, vertical = size_0),
                onClick = onSubmit,
                label = R.string.postNow,
                mTop = size_0,
                icon = Icons.Outlined.Campaign
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = size_16dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(size_16dp)
        ) {
            JPCard(
                modifier = Modifier.padding(top = size_16dp),
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
                    selected = uiState.matchType,
                    onSelected = { updateUiState(MatchRequestEffect.OnMatchTypeChange(it)) },
                    label = { it.label },
                    icon = { it.icon }
                )
            }
            JPCard(
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
                    value = convertMatchTimeString(uiState.startTime),
                    config = StyleConfig(
                        label = R.string.postOpponentChooseTimeLabel,
                        placeholder = R.string.postOpponentChooseTimeHint,
                        icon = Icons.Outlined.CalendarMonth,
                        isTextPrimaryColor = true,
                        isSelectHiltForLabel = true,
                        type = EDropdownType.DATE_TIME
                    ),
                    items = listOf(),
                    onSelected = {
                        updateUiState(MatchRequestEffect.OnDateChange(it))
                    },
                    sheetContent = { state ->
                        JPDateTimePickerSheet(
                            value = uiState.startTime,
                            visible = state.visible,
                            onDismiss = state.dismiss,
                            onConfirm = state.select
                        )
                    }
                )
                JPDropdown(
                    modifier = Modifier,
                    value = uiState.pitch?.displayName,
                    selectedItem = uiState.pitch,
                    items = pitches?.items.orEmpty(),
                    config = StyleConfig(
                        label = R.string.postOpponentChooseLocationLabel,
                        placeholder = R.string.postOpponentChooseLocationHint,
                        icon = Icons.Outlined.LocationOn,
                        isTextPrimaryColor = true,
                        mTop = size_8dp,
                        isSelectHiltForLabel = true
                    ),
                    onSelected = {
                        updateUiState(MatchRequestEffect.OnLocationChange(it))
                    },
                    sheetContent = { state ->
                        JPPitchPickerBottomSheet(
                            isLoading = pitches?.isLoading == true,
                            visible = state.visible,
                            pitches = state.items,
                            pitchSelected = state.selectedItem,
                            onDismiss = state.dismiss,
                            onPitchSelected = state.select,
                            onApiQueryChange = onGetPitches
                        )
                    }
                )
                JPInput(
                    modifier = Modifier,
                    value = uiState.phoneNumber,
                    config = StyleConfig(
                        label = R.string.contactInfo,
                        placeholder = R.string.contactInfoHint,
                        icon = Icons.Outlined.PhoneIphone,
                        isTextPrimaryColor = true,
                        mTop = size_8dp,
                        isSelectHiltForLabel = true,
                        keyboardType = KeyboardType.Phone
                    ),
                    onValueChange = { updateUiState(MatchRequestEffect.OnUpdatePhoneNumber(it)) }
                )
            }
            JPCard(
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
                    value = uiState.description,
                    config = StyleConfig(
                        minLines = 3,
                        maxLines = 5,
                        mTop = size_0,
                        placeholder = R.string.postOpponentDescHint,
                        isComment = true
                    ),
                    onValueChange = { updateUiState(MatchRequestEffect.OnDescriptionChange(it)) }
                )
            }

            JPCard(
                containerColor = Color.White,
                space = size_6dp,
                onClick = { onVisibleMore() }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(size_2dp)
                        ) {
                            JPText(
                                text = stringResource(R.string.moreDetail),
                                fontSize = size_15sp,
                                fontWeight = FontWeight.Medium
                            )
                            JPText(
                                text = stringResource(R.string.moreDetailDesc),
                                fontSize = size_12sp,
                                color = TextSecondary
                            )
                        }
                        JPSpacer(height = size_8dp)
                        FlowRow(
                            modifier = Modifier.wrapContentHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalArrangement = Arrangement.spacedBy(size_6dp),
                            maxLines = 2
                        ) {
                            JPChipIcon(
                                icon = Icons.Outlined.Balance,
                                label = stringResource(R.string.feeMatch)
                            )
                            JPChipIcon(
                                icon = Icons.Outlined.PrivacyTip,
                                label = stringResource(R.string.matchRule)
                            )
                            JPChipIcon(
                                icon = Icons.Outlined.MilitaryTech,
                                label = stringResource(R.string.matchLevel)
                            )
                            JPChipIcon(
                                icon = Icons.Outlined.Cake,
                                label = stringResource(R.string.matchAge)
                            )
                        }
                    }
                    JPIcon(icon = Icons.Default.ChevronRight, tint = TextSecondary)
                }
            }
            JPSpacer(height = size_0)
        }
    }
}

@AppPreview
@Preview
@Composable
fun FindOpponentPreview() {
    FindOpponentScreen()
}
