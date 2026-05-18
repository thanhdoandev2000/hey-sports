package com.example.heysports.ui.features.main.tabs.home.posts

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.heysports.R
import com.example.heysports.cores.models.StyleConfig
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.models.enums.*
import com.example.heysports.ui.components.app.CustomLine
import com.example.heysports.ui.components.app.IconTextRow
import com.example.heysports.ui.components.app.JPAttachPhoto
import com.example.heysports.ui.components.cores.*
import com.example.heysports.ui.features.main.tabs.home.components.JPChipGroup
import com.example.heysports.ui.theme.*

@Composable
fun MoreInformationModal(
    uiState: MoreInformationUiState? = null,
    photos: List<String> = emptyList(),
    visible: Boolean = false,
    onApply: (MoreInformationUiState) -> Unit = {},
    onPhotoAdded: (Uri) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    var localUiState by remember(uiState, visible) {
        mutableStateOf(uiState ?: MoreInformationUiState())
    }

    JPBottomSheetModal(
        containerColor = Color.White,
        visible = visible,
        onDismiss = onDismiss,
        showDragHandle = true,
        spacer = size_16dp,
        isScrollable = true,
        contentPadding = PaddingValues(size_10dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(size_6dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            JPIcon(
                icon = Icons.AutoMirrored.Outlined.FormatListBulleted,
                size = size_32dp,
                tint = TextPrimary
            )
            Column(modifier = Modifier.weight(1f)) {
                JPText(
                    text = stringResource(R.string.moreDetail),
                    fontSize = size_16sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = size_18sp
                )
                JPText(
                    text = stringResource(R.string.moreInfoSubTitle),
                    fontSize = size_10sp,
                    color = TextSecondary,
                    lineHeight = size_10sp
                )
            }
            JPIcon(
                icon = Icons.Outlined.Close,
                size = size_28dp,
                tint = TextPrimary,
                modifier = Modifier.clickable { onDismiss() })
        }

        JPCard(
            isWrapContent = true,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            containerColor = Color.White,
            space = size_10dp,
            padding = size_10dp
        ) {
            JPText(
                text = stringResource(R.string.feeMatchTitle),
                fontWeight = FontWeight.SemiBold,
                fontSize = size_15sp
            )
            Row(horizontalArrangement = Arrangement.spacedBy(size_6dp)) {
                EMatchFeeType.entries.forEach {
                    MatchFeeOptionCard(
                        item = it,
                        selected = localUiState.fee == it.name,
                        onClick = { localUiState = localUiState.copy(fee = it.name) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(size_4dp)
            ) {
                JPIcon(icon = Icons.Outlined.Info, size = size_18dp, tint = TextSecondary)
                JPText(
                    text = stringResource(R.string.feeMatchSubLabel),
                    fontSize = size_11sp,
                    color = TextSecondary
                )
            }
        }
        JPCard(
            isWrapContent = true,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            containerColor = Color.White,
            space = size_10dp,
            padding = size_10dp
        ) {
            JPText(
                text = stringResource(R.string.yourTeamInfo),
                fontWeight = FontWeight.SemiBold,
                fontSize = size_15sp
            )
            IconTextRow(
                icon = Icons.Outlined.MilitaryTech,
                iconTint = PrimaryGreen,
                textColor = TextPrimary,
                text = stringResource(R.string.teamLevel),
                fontSize = size_13sp,
                iconSize = size_20dp,
                fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = size_8dp)
            ) {
                JPChipGroup(
                    items = EMatchLevel.entries,
                    label = { it.label },
                    selected = localUiState.teamLevel
                        ?.let { runCatching { EMatchLevel.valueOf(it) }.getOrNull() }
                        ?: EMatchLevel.AVERAGE,
                    verPadding = size_3dp,
                    bgColor = bgPrimaryColor,
                    textColor = TextPrimary,
                    onSelected = { localUiState = localUiState.copy(teamLevel = it.name) }
                )
            }
            IconTextRow(
                icon = Icons.Outlined.Cake,
                iconTint = PrimaryGreen,
                textColor = TextPrimary,
                text = stringResource(R.string.teamAge),
                fontSize = size_13sp,
                iconSize = size_20dp,
                fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = size_8dp)
            ) {
                JPChipGroup(
                    items = EAges.entries,
                    label = { it.label },
                    selected = localUiState.age
                        ?.let { runCatching { EAges.valueOf(it) }.getOrNull() }
                        ?: EAges.U18,
                    verPadding = size_3dp,
                    bgColor = bgPrimaryColor,
                    textColor = TextPrimary,
                    onSelected = { localUiState = localUiState.copy(age = it.name) }
                )
            }
            IconTextRow(
                icon = Icons.Outlined.Style,
                iconTint = PrimaryGreen,
                textColor = TextPrimary,
                text = stringResource(R.string.teamStyle),
                fontSize = size_13sp,
                iconSize = size_20dp,
                fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = size_8dp)
            ) {
                JPChipGroup(
                    items = EMatchStyle.entries,
                    label = { it.label },
                    selected = localUiState.teamStyle
                        ?.let { runCatching { EMatchStyle.valueOf(it) }.getOrNull() }
                        ?: EMatchStyle.CASUAL,
                    verPadding = size_3dp,
                    bgColor = bgPrimaryColor,
                    textColor = TextPrimary,
                    onSelected = { localUiState = localUiState.copy(teamStyle = it.name) }
                )
            }
            IconTextRow(
                icon = Icons.Outlined.Groups2,
                iconTint = PrimaryGreen,
                textColor = TextPrimary,
                text = stringResource(R.string.teamStatus),
                fontSize = size_13sp,
                iconSize = size_20dp,
                fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = size_8dp)
            ) {
                JPChipGroup(
                    items = ETeamStatus.entries,
                    label = { it.label },
                    selected = localUiState.teamStatus
                        ?.let { runCatching { ETeamStatus.valueOf(it) }.getOrNull() }
                        ?: ETeamStatus.FULL_SQUAD,
                    verPadding = size_3dp,
                    bgColor = bgPrimaryColor,
                    textColor = TextPrimary,
                    onSelected = { localUiState = localUiState.copy(teamStatus = it.name) }
                )
            }
        }

        JPCard(
            isWrapContent = true,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            containerColor = Color.White,
            space = size_10dp,
            padding = size_10dp
        ) {
            JPText(
                text = stringResource(R.string.ruleAndNote),
                fontWeight = FontWeight.SemiBold,
                fontSize = size_15sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(size_2dp)
            ) {
                JPText(
                    text = stringResource(R.string.matchRuleLabel),
                    fontWeight = FontWeight.Medium,
                    fontSize = size_13sp
                )
                JPText(
                    text = stringResource(R.string.optionalLabel),
                    color = TextSecondary,
                    fontSize = size_12sp
                )
            }

            JPChipGroup(
                items = EMatchRule.entries,
                label = { it.label },
                selected = localUiState.rule
                    ?.firstOrNull()
                    ?.let { runCatching { EMatchRule.valueOf(it) }.getOrNull() }
                    ?: EMatchRule.FRIENDLY_MATCH,
                verPadding = size_6dp,
                bgColor = bgPrimaryColor,
                textColor = TextPrimary,
                iconColor = PrimaryGreen,
                onSelected = { localUiState = localUiState.copy(rule = listOf(it.name)) },
                icon = { it.icon }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(size_2dp)
            ) {
                JPText(
                    text = stringResource(R.string.noteMore),
                    fontWeight = FontWeight.Medium,
                    fontSize = size_13sp
                )
                JPText(
                    text = stringResource(R.string.optionalLabel),
                    color = TextSecondary,
                    fontSize = size_12sp
                )
            }
            JPInput(
                value = localUiState.moreNotes,
                config = StyleConfig(
                    minLines = 3,
                    maxLines = 5,
                    mTop = size_0,
                    placeholder = R.string.postOpponentDescHint,
                    isComment = true
                ),
                onValueChange = { localUiState = localUiState.copy(moreNotes = it) }
            )

            CustomLine()
            JPAttachPhoto(items = photos) {
                onPhotoAdded(it)
            }
        }
        JPButton(
            label = R.string.applyButton,
            mTop = size_8dp,
            onClick = {
                onApply(localUiState)
                onDismiss()
            }
        )
        JPSpacer(height = size_10dp)
    }
}

@Composable
@AppPreview
@Preview
private fun MoreInformationModalPreview() {
    MoreInformationModal()
}


@Composable
fun MatchFeeOptionCard(
    item: EMatchFeeType,
    modifier: Modifier,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) GreenDark else TextSecondary.copy(0.35f)
    val backgroundColor = Color.White
    val titleColor = if (selected) GreenDark else TextPrimary
    Column(
        modifier = modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(size_10dp))
            .background(backgroundColor)
            .border(size_line, borderColor, RoundedCornerShape(size_10dp))
            .clickable(onClick = onClick)
            .padding(horizontal = size_4dp, vertical = size_8dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = null,
            tint = if (selected) GreenDark else GreenDark.copy(0.5f),
            modifier = Modifier.size(size_26dp)
        )
        JPSpacer(size_4dp)
        JPText(
            text = item.label,
            fontSize = size_12sp,
            fontWeight = FontWeight.SemiBold,
            color = titleColor,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        JPText(
            text = item.subLabel,
            fontSize = size_10sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = size_12sp,
            maxLines = 2
        )
    }
}
