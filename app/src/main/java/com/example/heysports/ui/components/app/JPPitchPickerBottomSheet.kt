package com.example.heysports.ui.components.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Stadium
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.domain.models.PitchSelectionModel
import com.example.heysports.domain.models.SubPitchSelectionModel
import com.example.heysports.ui.components.cores.JPBottomSheetModal
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.navigation.shimmer
import com.example.heysports.ui.theme.*

@Composable
fun JPPitchPickerBottomSheet(
    modifier: Modifier = Modifier,
    visible: Boolean,
    isLoading: Boolean = false,
    pitches: List<PitchSelectionModel>,
    pitchSelected: PitchSelectionModel? = null,
    onDismiss: () -> Unit,
    onPitchSelected: (PitchSelectionModel) -> Unit,
    onApiQueryChange: (String) -> Unit = {}
) {
    if (! visible) return

    var query by rememberSaveable { mutableStateOf("") }
    var expandedPitchId by rememberSaveable { mutableLongStateOf(pitchSelected?.id ?: - 1L) }

    JPBottomSheetModal(
        visible = true,
        onDismiss = onDismiss,
        modifier = modifier,
        containerColor = BgColorPage,
        showDragHandle = false,
        contentPadding = PaddingValues(
            start = size_16dp,
            top = size_14dp,
            end = size_16dp,
            bottom = size_12dp
        ),
        spacer = size_10dp
    ) { dismiss ->
        PitchPickerContent(
            isLoading = isLoading,
            query = query,
            onQueryChange = { query = it },
            pitches = pitches,
            expandedPitchId = expandedPitchId.takeIf { it != - 1L },
            onExpandPitch = { pitch ->
                expandedPitchId = if (expandedPitchId == pitch.id) - 1L else pitch.id
            },
            onPitchSelected = {
                if (it.subPitches.isEmpty()) expandedPitchId = - 1L
                onPitchSelected(it)
            },
            pitchSelected = pitchSelected,
            onSearchExecute = onApiQueryChange
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(size_16dp))
                .background(Color.White)
                .clickable { dismiss() }
                .padding(vertical = size_14dp),
            contentAlignment = Alignment.Center
        ) {
            JPText(
                text = stringResource(R.string.btnCancel),
                fontSize = size_16sp,
                fontWeight = FontWeight.Medium,
                color = RedColor
            )
        }
    }
}

@Composable
private fun PitchPickerContent(
    isLoading: Boolean = false,
    query: String,
    onQueryChange: (String) -> Unit,
    pitches: List<PitchSelectionModel>,
    expandedPitchId: Long?,
    pitchSelected: PitchSelectionModel?,
    onExpandPitch: (PitchSelectionModel) -> Unit,
    onPitchSelected: (PitchSelectionModel) -> Unit,
    onSearchExecute: (String) -> Unit
) {
    val isInitialLoading = isLoading && pitches.isEmpty()
    val isSearching = isLoading && pitches.isNotEmpty()

    Column(modifier = Modifier.padding(top = size_16dp)) {
        JPText(
            text = stringResource(R.string.choosePitch),
            fontSize = size_18sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary

        )
        JPText(
            text = stringResource(R.string.choosePitchAndSub),
            fontSize = size_13sp,
            color = TextSecondary
        )
    }

    JPSearchBar(
        textSearch = query,
        placeholder = R.string.mapSearchHint,
        color = Color.White,
        onSearchExecute = onSearchExecute,
        onTextChange = onQueryChange
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size_16dp))
            .background(Color.White)
    ) {
        if (isSearching) {
            SearchingIndicator()
            HorizontalDivider(color = Color(0xFFE8ECE8), thickness = size_line)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 430.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when {
                isInitialLoading -> PitchSkeletonList()
                pitches.isEmpty() -> EmptyPitchPickerResult()
                else -> {
                    pitches.forEachIndexed { index, pitch ->
                        val isExpanded = expandedPitchId == pitch.id
                        val isPitchSelected = pitchSelected?.id == pitch.id
                        if (index != 0) {
                            HorizontalDivider(color = Color(0xFFE8ECE8), thickness = size_line)
                        }
                        PitchRow(
                            pitch = pitch,
                            selected = isPitchSelected,
                            expanded = isExpanded,
                            subPitchName = pitchSelected?.subPitchSelected?.pitchName,
                            enabled = ! isSearching,
                            onClick = {
                                if (pitch.subPitches.isNotEmpty())
                                    onExpandPitch(pitch) else onPitchSelected(pitch)
                            }
                        )
                        if (isExpanded) {
                            pitch.subPitches.forEach { subPitch ->
                                SubPitchRow(
                                    subPitch = subPitch,
                                    selected = pitchSelected?.subPitchSelected?.id == subPitch.id,
                                    enabled = ! isSearching,
                                    onClick = {
                                        onPitchSelected(
                                            pitch.copy(
                                                subPitchSelected = subPitch,
                                                subPitches = listOf()
                                            )
                                        )
                                    }
                                )
                                HorizontalDivider(
                                    color = Color(0xFFE8ECE8),
                                    thickness = size_line,
                                    modifier = Modifier.padding(start = 62.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchingIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = size_16dp, vertical = size_10dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size_10dp)
    ) {
        CircularProgressIndicator(
            color = PrimaryGreen,
            modifier = Modifier.size(size_18dp),
            strokeWidth = size_3dp
        )
        JPText(
            text = "Đang cập nhật danh sách sân...",
            fontSize = size_12sp,
            color = TextSecondary
        )
    }
}

@Composable
private fun PitchSkeletonList() {
    repeat(4) { index ->
        if (index != 0) {
            HorizontalDivider(color = Color(0xFFE8ECE8), thickness = size_line)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = size_16dp, vertical = size_12dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShimmerBox(
                isLoading = true,
                modifier = Modifier.size(size_40dp),
                shimmer = shimmer
            ) {}
            Spacer(modifier = Modifier.width(size_12dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(size_8dp)
            ) {
                ShimmerBox(
                    isLoading = true,
                    modifier = Modifier
                        .fillMaxWidth(0.62f)
                        .height(size_14dp),
                    shimmer = shimmer
                ) {}
                ShimmerBox(
                    isLoading = true,
                    modifier = Modifier
                        .fillMaxWidth(0.42f)
                        .height(size_10dp),
                    shimmer = shimmer
                ) {}
            }
        }
    }
}

@Composable
private fun PitchRow(
    pitch: PitchSelectionModel,
    selected: Boolean,
    expanded: Boolean,
    subPitchName: String?,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (selected) Color(0xFFF8FCF8) else Color.White)
            .clickable(enabled = enabled) { onClick() }
            .padding(horizontal = size_16dp, vertical = size_12dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(size_40dp)
                .clip(RoundedCornerShape(size_10dp))
                .background(if (selected) LightGreenBackground else Color(0xFFF0F0F0)),
            contentAlignment = Alignment.Center
        ) {
            JPIcon(
                icon = Icons.Outlined.Stadium,
                tint = if (selected) GreenDark else TextSecondary.copy(alpha = 0.6f),
                size = size_22sp.value.dp
            )
        }
        Spacer(modifier = Modifier.width(size_12dp))
        Column(modifier = Modifier.weight(1f)) {
            JPText(
                text = pitch.name,
                fontSize = size_15sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                maxLines = 1
            )
            JPText(
                text = pitch.address,
                fontSize = size_12sp,
                color = TextSecondary,
                maxLines = 1
            )
            if (selected && subPitchName != null) {
                JPText(
                    text = stringResource(R.string.chooseSubPitchSelected, subPitchName),
                    fontSize = size_12sp,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryGreen,
                    maxLines = 1
                )
            }
        }

        JPIcon(
            icon = if (expanded) Icons.Outlined.KeyboardArrowDown else Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            tint = if (selected) GreenDark else TextSecondary.copy(alpha = 0.55f),
            size = size_24dp
        )
    }
}

@Composable
private fun SubPitchRow(
    subPitch: SubPitchSelectionModel,
    selected: Boolean,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (selected) LightGreenBackground else Color.White)
            .clickable(enabled = enabled && subPitch.isAvailable) { onClick() }
            .padding(start = size_32dp, end = size_16dp, top = size_10dp, bottom = size_10dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(size_10dp)
                .background(
                    color = when {
                        selected -> PrimaryGreen
                        else -> TextSecondary.copy(alpha = 0.22f)
                    },
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(size_14dp))
        Column(modifier = Modifier.weight(1f)) {
            JPText(
                text = subPitch.pitchName,
                fontSize = size_14sp,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                color = if (subPitch.isAvailable) TextPrimary else TextSecondary.copy(alpha = 0.65f)
            )
            JPText(
                text = stringResource(R.string.chooseSubPitchDesc, subPitch.type),
                fontSize = size_11sp,
                color = TextSecondary.copy(alpha = if (subPitch.isAvailable) 1f else 0.55f)
            )
        }
        if (selected) {
            JPIcon(
                icon = Icons.Outlined.Check,
                tint = GreenDark,
                size = size_18dp
            )
        }
    }
}

@Composable
private fun EmptyPitchPickerResult() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = size_16dp, vertical = size_24dp),
        contentAlignment = Alignment.Center
    ) {
        JPText(
            text = "Không tìm thấy sân phù hợp",
            fontSize = size_14sp,
            color = TextSecondary
        )
    }
}

@Preview
@Composable
@AppPreview
private fun JPPitchPickerBottomSheetPreview() {
    JPPitchPickerBottomSheet(
        visible = true,
        pitches = samplePitchOptions,
        onDismiss = {},
        onPitchSelected = {}
    )
}

private val samplePitchOptions = listOf(
    PitchSelectionModel(
        id = 1L,
        name = "Sân bóng Tuyên Sơn",
        address = "Số 1 Vũ Thạnh, Hải Châu",
        photo = null,
        subPitches = listOf(
            SubPitchSelectionModel(11L, "Sân 5A", "Sân 5 người", isAvailable = true),
            SubPitchSelectionModel(12L, "Sân 5B", "Sân 5 người", isAvailable = false),
            SubPitchSelectionModel(13L, "Sân 7A", "Sân 7 người", isAvailable = false)
        )
    ),
    PitchSelectionModel(
        id = 2L,
        name = "Sân Chuyên Việt",
        address = "Ngũ Hành Sơn",
        photo = null,
        subPitches = listOf(
            SubPitchSelectionModel(21L, "Sân 5A", "Sân 5 người", isAvailable = true),
            SubPitchSelectionModel(22L, "Sân 7A", "Sân 7 người", isAvailable = true)
        )
    ),
    PitchSelectionModel(
        id = 3L,
        name = "Sân Hòa Xuân",
        address = "Cẩm Lệ",
        photo = null,
        subPitches = listOf(
            SubPitchSelectionModel(31L, "Sân 5A", "Sân 5 người", isAvailable = true)
        )
    )
)
