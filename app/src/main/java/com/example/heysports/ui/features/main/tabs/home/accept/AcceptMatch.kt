package com.example.heysports.ui.features.main.tabs.home.accept

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.FormatListBulleted
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.cores.utils.DateTimeUtils
import com.example.heysports.data.models.dto.MatchRequestDto
import com.example.heysports.data.models.dto.TeamOptionDto
import com.example.heysports.data.models.enums.EAges
import com.example.heysports.data.models.enums.EMatchFeeType
import com.example.heysports.data.models.enums.EMatchRule
import com.example.heysports.data.models.enums.EMatchStyle
import com.example.heysports.data.models.enums.ETeamStatus
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.app.UserAvatar
import com.example.heysports.ui.components.cores.JPBottomSheetModal
import com.example.heysports.ui.components.cores.JPButton
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*
import androidx.core.net.toUri
import coil.compose.AsyncImage

@Composable
fun AcceptMatch(
    matchRequestId: Long,
    viewModel: AcceptMatchViewModel,
    onBack: () -> Unit = {},
    onSubmitted: () -> Unit = onBack
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(matchRequestId) {
        viewModel.load(matchRequestId)
    }
    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                AcceptMatchEffect.Submitted -> onSubmitted()
            }
        }
    }

    AcceptMatchScreen(
        uiState = uiState,
        onBack = onBack,
        onSelectTeam = viewModel::selectTeam,
        onSelectIndividual = viewModel::selectIndividual,
        onPhoneChange = viewModel::updatePhoneNumber,
        onMessageChange = viewModel::updateMessage,
        onSubmit = viewModel::submit,
        onCall = {
            uiState.matchRequest?.contactPhone
                ?.takeIf(String::isNotBlank)
                ?.let { phone ->
                    context.startActivity(
                        Intent(Intent.ACTION_DIAL, "tel:$phone".toUri())
                    )
                }
        }
    )
}

@Composable
private fun AcceptMatchScreen(
    uiState: AcceptMatchUiState,
    onBack: () -> Unit = {},
    onSelectTeam: (Long) -> Unit = {},
    onSelectIndividual: () -> Unit = {},
    onPhoneChange: (String) -> Unit = {},
    onMessageChange: (String) -> Unit = {},
    onSubmit: () -> Unit = {},
    onCall: () -> Unit = {}
) {
    val request = uiState.matchRequest
    val selectedTeam = uiState.selectedTeam
    val eligibleTeams = uiState.teams.filter { it.id != request?.teamId }
    var showTeamPicker by rememberSaveable { mutableStateOf(false) }

    HeySportContainer(
        title = stringResource(R.string.acceptMatchTitle),
        subTitle = R.string.acceptMatchSubtitle,
        onNavigateUp = onBack,
        isLoading = uiState.isLoading || uiState.isSubmitting,
        bottomContent = {
            AcceptMatchBottomBar(
                canSubmit = uiState.canSubmit,
                canCall = !request?.contactPhone.isNullOrBlank(),
                onSubmit = onSubmit,
                onCall = onCall
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = size_16dp, vertical = size_10dp)
                .padding(bottom = size_20dp),
            verticalArrangement = Arrangement.spacedBy(size_14dp)
        ) {
            MatchTicketCard(
                hostTeamName = request.ownerDisplayName(),
                guestTeamName = when {
                    uiState.isIndividual -> stringResource(R.string.acceptMatchIndividualShort)
                    else -> selectedTeam?.teamName
                        ?: stringResource(R.string.yourTeamPlaceholder)
                },
                hostInitials = request.ownerDisplayName().toInitials("FC"),
                guestInitials = if (uiState.isIndividual) {
                    "CN"
                } else {
                    selectedTeam?.teamName.toInitials("?")
                },
                time = DateTimeUtils.getTimeDisplay(request?.matchTime),
                date = DateTimeUtils.getTicketDateDisplay(request?.matchTime),
                location = request?.pitchName.orEmpty(),
                statusLabel = stringResource(R.string.matchOpenBadge)
            )
            FactStrip(request)
            OwnerInfoCard(request)
            MatchRequirementsCard(request)
            ReceiverTeamCard(
                teams = eligibleTeams,
                selectedTeam = selectedTeam,
                isIndividual = uiState.isIndividual,
                phoneNumber = uiState.phoneNumber,
                message = uiState.message,
                onChooseTeam = { showTeamPicker = true },
                onPhoneChange = onPhoneChange,
                onMessageChange = onMessageChange
            )
        }
    }

    TeamPickerSheet(
        visible = showTeamPicker,
        teams = eligibleTeams,
        selectedTeamId = uiState.selectedTeamId,
        isIndividual = uiState.isIndividual,
        onDismiss = { showTeamPicker = false },
        onSelectIndividual = {
            onSelectIndividual()
            showTeamPicker = false
        },
        onSelect = { teamId ->
            onSelectTeam(teamId)
            showTeamPicker = false
        }
    )
}

@Composable
private fun FactStrip(request: MatchRequestDto?) {
    val fairPlayLabel = request?.rules?.firstOrNull()
        .toRuleLabel()
        ?: stringResource(R.string.fairPlayLabel)

    CardSurface {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = size_14dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FactItem(
                Icons.Outlined.Groups,
                request?.matchFormat.orEmpty().ifBlank { "-" },
                Modifier.weight(1f)
            )
            VerticalDivider()
            FactItem(
                Icons.Outlined.Leaderboard,
                request?.skillLevel.orEmpty().ifBlank { "-" },
                Modifier.weight(1f)
            )
            VerticalDivider()
            FactItem(
                Icons.Outlined.Payments,
                request?.feeType.toFeeLabel() ?: "-",
                Modifier.weight(1f)
            )
            VerticalDivider()
            FactItem(
                Icons.Outlined.VerifiedUser,
                fairPlayLabel,
                Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MatchRequirementsCard(request: MatchRequestDto?) {
    var expanded by rememberSaveable(request?.id) { mutableStateOf(false) }
    val age = request?.ageGroup.toAgeLabel()
    val style = request?.teamStyle.toStyleLabel()
    val teamStatus = request?.teamStatus.toTeamStatusLabel()
    val rules = request?.rules.orEmpty().mapNotNull { it.toRuleLabel() }
    val notes = request?.moreNotes?.takeIf(String::isNotBlank)
    val photos = request?.photoUrls.orEmpty()

    CardSurface {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = ! expanded }
                .padding(size_14dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(size_8dp)
        ) {
            JPIcon(
                icon = Icons.AutoMirrored.Outlined.FormatListBulleted,
                tint = GreenDark,
                size = size_24dp
            )
            JPText(
                text = stringResource(R.string.moreDetail),
                color = TextPrimary,
                fontSize = size_16sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            JPIcon(
                icon = if (expanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                tint = TextSecondary,
                size = size_24dp
            )
        }
        if (expanded) {
            Column(
                modifier = Modifier.padding(
                    start = size_14dp,
                    end = size_14dp,
                    bottom = size_14dp
                ),
                verticalArrangement = Arrangement.spacedBy(size_12dp)
            ) {
                HorizontalDivider(color = DividerColor)
                val hasConditions = age != null || style != null || teamStatus != null ||
                        rules.isNotEmpty() || notes != null || photos.isNotEmpty()
                if (hasConditions) {
                    age?.let {
                        AdditionalDetailRow(
                            Icons.Outlined.Cake,
                            stringResource(R.string.teamAge),
                            it
                        )
                    }
                    style?.let {
                        AdditionalDetailRow(
                            Icons.Outlined.Style,
                            stringResource(R.string.teamStyle),
                            it
                        )
                    }
                    teamStatus?.let {
                        AdditionalDetailRow(
                            Icons.Outlined.Groups2,
                            stringResource(R.string.teamStatus),
                            it
                        )
                    }
                    if (rules.isNotEmpty()) {
                        AdditionalDetailRow(
                            Icons.Outlined.Gavel,
                            stringResource(R.string.matchRuleLabel),
                            rules.joinToString(" · ")
                        )
                    }
                } else {
                    JPText(
                        text = stringResource(R.string.acceptMatchNoAdditionalDetails),
                        color = TextSecondary,
                        fontSize = size_12sp
                    )
                }
                notes?.let {
                    JPText(
                        text = stringResource(R.string.noteMore),
                        color = TextSecondary,
                        fontSize = size_11sp,
                        fontWeight = FontWeight.Medium
                    )
                    JPText(
                        text = it,
                        color = TextPrimary,
                        fontSize = size_13sp,
                        lineHeight = size_20sp
                    )
                }
                if (photos.isNotEmpty()) {
                    HorizontalDivider(color = DividerColor)
                    JPText(
                        text = stringResource(R.string.attachPhotos),
                        color = TextSecondary,
                        fontSize = size_11sp,
                        fontWeight = FontWeight.Medium
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(size_8dp)) {
                        items(photos) { photoUrl ->
                            AsyncImage(
                                model = photoUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(88.dp)
                                    .clip(RoundedCornerShape(size_6dp))
                                    .background(bgPrimaryColor)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AdditionalDetailRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size_10dp)
    ) {
        JPIcon(icon = icon, tint = GreenDark, size = size_20dp)
        JPText(text = label, color = TextSecondary, fontSize = size_12sp)
        JPText(
            text = value,
            color = TextPrimary,
            fontSize = size_12sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

private fun String?.toFeeLabel(): String? = this?.let { raw ->
    EMatchFeeType.entries.firstOrNull { it.name == raw }?.label ?: raw
}

private fun String?.toAgeLabel(): String? = this?.let { raw ->
    EAges.entries.firstOrNull { it.name == raw }?.label ?: raw
}

private fun String?.toStyleLabel(): String? = this?.let { raw ->
    EMatchStyle.entries.firstOrNull { it.name == raw }?.label ?: raw
}

private fun String?.toTeamStatusLabel(): String? = this?.let { raw ->
    ETeamStatus.entries.firstOrNull { it.name == raw }?.label ?: raw
}

private fun String?.toRuleLabel(): String? = this?.let { raw ->
    EMatchRule.entries.firstOrNull { it.name == raw }?.label ?: raw
}

@Composable
private fun FactItem(icon: ImageVector, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = size_4dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(size_8dp)
    ) {
        JPIcon(icon = icon, tint = GreenDark, size = size_24dp)
        JPText(
            text = label,
            color = TextPrimary,
            fontSize = size_12sp,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

@Composable
private fun VerticalDivider() {
    Box(
        Modifier
            .width(size_line)
            .height(size_48dp)
            .background(DividerColor)
    )
}

@Composable
private fun OwnerInfoCard(request: MatchRequestDto?) {
    val contactPhone = request?.contactPhone?.takeIf(String::isNotBlank)
    val ownerMessage = request?.description?.takeIf(String::isNotBlank)
        ?: stringResource(R.string.acceptMatchNoMessage)
    val postedTime = DateTimeUtils.getRelativeTimeDisplay(request?.createdAt)
    val ownerMeta = buildList {
        add(
            if (request?.postedByType == "TEAM") {
                stringResource(R.string.ownerPostedAsTeam)
            } else {
                stringResource(R.string.ownerPostedAsIndividual)
            }
        )
        request?.teamArea?.takeIf(String::isNotBlank)?.let(::add)
        postedTime.takeIf(String::isNotBlank)?.let(::add)
    }.joinToString(" · ")

    CardSurface(contentPadding = size_14dp) {
        SectionTitle(
            icon = Icons.Outlined.Person,
            title = stringResource(R.string.ownerInfoTitle)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(size_8dp)
        ) {
            UserAvatar(
                name = request.ownerDisplayName(),
                imageUrl = request?.teamAvatar ?: request?.userAvatar,
                size = size_36dp,
                borderWidth = size_0
            )
            Column(Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(size_4dp)
                ) {
                    JPText(
                        text = request.ownerDisplayName(),
                        color = TextPrimary,
                        fontSize = size_12sp,
                        fontWeight = FontWeight.Bold
                    )
                    JPIcon(icon = Icons.Outlined.Verified, tint = PrimaryGreen, size = size_14dp)
                }
                JPText(
                    text = ownerMeta,
                    color = TextSecondary,
                    fontSize = size_10sp
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(size_8dp)
        ) {
            JPIcon(
                icon = Icons.AutoMirrored.Outlined.Message,
                tint = GreenDark,
                size = size_18dp
            )
            JPText(
                text = ownerMessage,
                color = TextPrimary,
                fontSize = size_12sp,
                lineHeight = size_18sp,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(size_6dp))
                .background(bgPrimaryColor)
                .padding(horizontal = size_10dp, vertical = size_8dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(size_8dp)
        ) {
            JPIcon(
                icon = Icons.Outlined.Phone,
                tint = if (contactPhone != null) GreenDark else TextSecondary,
                size = size_18dp
            )
            Column(Modifier.weight(1f)) {
                JPText(
                    text = stringResource(R.string.ownerContactPhone),
                    color = TextSecondary,
                    fontSize = size_10sp
                )
                JPText(
                    text = contactPhone
                        ?: stringResource(R.string.ownerContactNotProvided),
                    color = if (contactPhone != null) TextPrimary else TextSecondary,
                    fontSize = size_12sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun ReceiverTeamCard(
    teams: List<TeamOptionDto>,
    selectedTeam: TeamOptionDto?,
    isIndividual: Boolean,
    phoneNumber: String,
    message: String,
    onChooseTeam: () -> Unit,
    onPhoneChange: (String) -> Unit,
    onMessageChange: (String) -> Unit
) {
    CardSurface(contentPadding = size_14dp) {
        SectionTitle(
            icon = Icons.Outlined.Shield,
            title = stringResource(R.string.receiverTeamTitle)
        )
        CompactInputRow(
            icon = if (isIndividual) Icons.Outlined.Person else Icons.Outlined.Shield,
            label = when {
                isIndividual -> stringResource(R.string.acceptMatchAsIndividual)
                else -> selectedTeam?.teamName ?: stringResource(R.string.selectYourTeam)
            },
            trailing = when {
                isIndividual -> stringResource(R.string.acceptMatchIndividualLabel)
                selectedTeam != null -> selectedTeam.level.orEmpty()
                teams.isEmpty() -> stringResource(R.string.acceptMatchNoTeams)
                else -> stringResource(R.string.requiredLabel)
            },
            showChevron = true,
            onClick = onChooseTeam
        )
        PhoneInputRow(
            value = phoneNumber,
            onValueChange = onPhoneChange
        )
        MessageBox(message = message, onMessageChange = onMessageChange)
    }
}

@Composable
private fun CompactInputRow(
    icon: ImageVector,
    label: String,
    trailing: String,
    showChevron: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(size_58dp)
            .clip(RoundedCornerShape(size_6dp))
            .border(size_line, DividerColor, RoundedCornerShape(size_6dp))
            .clickable(onClick = onClick)
            .padding(horizontal = size_12dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size_10dp)
    ) {
        JPIcon(icon = icon, tint = GreenDark, size = size_24dp)
        JPText(
            text = label,
            color = TextPrimary,
            fontSize = size_13sp,
            modifier = Modifier.weight(1f),
            maxLines = 1
        )
        if (trailing.isNotBlank()) {
            JPText(
                text = trailing,
                color = if (showChevron) GreenDark else TextSecondary,
                fontSize = size_11sp,
                fontWeight = if (showChevron) FontWeight.Medium else FontWeight.Normal,
                maxLines = 1
            )
        }
        if (showChevron) {
            JPIcon(
                icon = Icons.Outlined.ChevronRight,
                tint = TextSecondary,
                size = size_20dp
            )
        }
    }
}

@Composable
private fun PhoneInputRow(
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(size_58dp)
            .clip(RoundedCornerShape(size_6dp))
            .border(size_line, DividerColor, RoundedCornerShape(size_6dp))
            .padding(horizontal = size_12dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size_10dp)
    ) {
        JPIcon(icon = Icons.Outlined.Phone, tint = GreenDark, size = size_24dp)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = TextPrimary,
                fontSize = size_13sp
            ),
            modifier = Modifier.weight(1f),
            decorationBox = { innerTextField ->
                if (value.isBlank()) {
                    JPText(
                        text = stringResource(R.string.acceptMatchPhoneHint),
                        color = TextSecondary,
                        fontSize = size_12sp
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
private fun MessageBox(message: String, onMessageChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp)
            .clip(RoundedCornerShape(size_6dp))
            .border(size_line, DividerColor, RoundedCornerShape(size_6dp))
            .padding(size_12dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(size_6dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(size_10dp)
            ) {
                JPIcon(
                    icon = Icons.AutoMirrored.Outlined.Message,
                    tint = GreenDark,
                    size = size_24dp
                )
                JPText(
                    text = stringResource(R.string.acceptMatchMessage),
                    color = TextPrimary,
                    fontSize = size_13sp
                )
            }
            BasicTextField(
                value = message,
                onValueChange = onMessageChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = TextPrimary,
                    fontSize = size_13sp
                ),
                decorationBox = { innerTextField ->
                    if (message.isEmpty()) {
                        JPText(
                            text = stringResource(R.string.acceptMatchMessageHint),
                            color = TextSecondary,
                            fontSize = size_12sp
                        )
                    }
                    innerTextField()
                }
            )
        }
        JPText(
            text = "${message.length}/120",
            color = TextSecondary,
            fontSize = size_11sp,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
private fun TeamPickerSheet(
    visible: Boolean,
    teams: List<TeamOptionDto>,
    selectedTeamId: Long?,
    isIndividual: Boolean,
    onDismiss: () -> Unit,
    onSelectIndividual: () -> Unit,
    onSelect: (Long) -> Unit
) {
    JPBottomSheetModal(
        visible = visible,
        onDismiss = onDismiss,
        containerColor = Color.White,
        showDragHandle = true,
        dismissOnClickOutside = true
    ) {
        SectionTitle(
            icon = Icons.Outlined.Groups,
            title = stringResource(R.string.acceptMatchChooseTeamTitle)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onSelectIndividual)
                .padding(vertical = size_10dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(size_12dp)
        ) {
            Box(
                modifier = Modifier
                    .size(size_42dp)
                    .clip(RoundedCornerShape(size_8dp))
                    .background(LightGreenBackground),
                contentAlignment = Alignment.Center
            ) {
                JPIcon(
                    icon = Icons.Outlined.Person,
                    tint = GreenDark,
                    size = size_24dp
                )
            }
            Column(Modifier.weight(1f)) {
                JPText(
                    text = stringResource(R.string.acceptMatchAsIndividual),
                    color = TextPrimary,
                    fontSize = size_14sp,
                    fontWeight = FontWeight.SemiBold
                )
                JPText(
                    text = stringResource(R.string.acceptMatchIndividualDescription),
                    color = TextSecondary,
                    fontSize = size_11sp
                )
            }
            if (isIndividual) {
                JPIcon(
                    icon = Icons.Outlined.CheckCircle,
                    tint = PrimaryGreen,
                    size = size_24dp
                )
            }
        }
        if (teams.isNotEmpty()) {
            HorizontalDivider(color = DividerColor)
        }
        if (teams.isEmpty()) {
            JPText(
                text = stringResource(R.string.acceptMatchNoTeamsDescription),
                color = TextSecondary,
                fontSize = size_13sp,
                modifier = Modifier.padding(vertical = size_16dp)
            )
        } else {
            teams.forEachIndexed { index, team ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(team.id) }
                        .padding(vertical = size_10dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(size_12dp)
                ) {
                    UserAvatar(
                        name = team.teamName,
                        imageUrl = team.avatar,
                        size = size_42dp,
                        borderWidth = size_0
                    )
                    Column(Modifier.weight(1f)) {
                        JPText(
                            text = team.teamName,
                            color = TextPrimary,
                            fontSize = size_14sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        team.level?.takeIf(String::isNotBlank)?.let { level ->
                            JPText(
                                text = level,
                                color = TextSecondary,
                                fontSize = size_11sp
                            )
                        }
                    }
                    if (team.id == selectedTeamId) {
                        JPIcon(
                            icon = Icons.Outlined.CheckCircle,
                            tint = PrimaryGreen,
                            size = size_24dp
                        )
                    }
                }
                if (index < teams.lastIndex) {
                    HorizontalDivider(color = DividerColor)
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(icon: ImageVector, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size_8dp)
    ) {
        JPIcon(icon = icon, tint = GreenDark, size = size_24dp)
        JPText(
            text = title,
            color = TextPrimary,
            fontSize = size_16sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun CardSurface(
    contentPadding: androidx.compose.ui.unit.Dp = size_0,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size_8dp))
            .background(Color.White)
            .border(size_line, DividerColor.copy(alpha = 0.7f), RoundedCornerShape(size_8dp))
            .padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(size_12dp),
        content = content
    )
}

@Composable
private fun AcceptMatchBottomBar(
    canSubmit: Boolean,
    canCall: Boolean,
    onSubmit: () -> Unit,
    onCall: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .imePadding()
            .navigationBarsPadding()
            .padding(horizontal = size_16dp, vertical = size_10dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(size_10dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .size(size_50dp)
                    .clip(RoundedCornerShape(size_8dp))
                    .border(
                        size_line,
                        if (canCall) GreenDark else TextSecondary,
                        RoundedCornerShape(size_8dp)
                    ),
                enabled = canCall,
                onClick = onCall
            ) {
                JPIcon(
                    icon = Icons.Outlined.Call,
                    tint = if (canCall) GreenDark else TextSecondary
                )
            }
            JPButton(
                modifier = Modifier.weight(1f),
                label = R.string.sendAcceptMatch,
                icon = Icons.Outlined.Handshake,
                isEnabled = canSubmit,
                bgColor = GreenDark,
                mTop = size_0,
                height = size_50dp,
                onClick = onSubmit
            )
        }
        JPSpacer(height = size_8dp)
        JPText(
            text = stringResource(R.string.acceptMatchNotice),
            color = TextSecondary,
            fontSize = size_11sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

private fun MatchRequestDto?.ownerDisplayName(): String {
    return this?.teamName
        ?.takeIf(String::isNotBlank)
        ?: this?.userName?.takeIf(String::isNotBlank)
        ?: "Hey Sports"
}

private fun String?.toInitials(fallback: String): String {
    val words = this?.trim()?.split(Regex("\\s+")).orEmpty().filter(String::isNotBlank)
    return when {
        words.isEmpty() -> fallback
        words.size == 1 -> words.first().take(2).uppercase()
        else -> "${words.first().first()}${words.last().first()}".uppercase()
    }
}

@Composable
@Preview
@AppPreview
private fun AcceptMatchPreview() {
    AcceptMatchScreen(
        uiState = AcceptMatchUiState(
            matchRequest = MatchRequestDto(
                id = 1,
                createdAt = "2026-05-18T09:00:00+00:00",
                postedByType = "TEAM",
                type = "FIND_OPPONENT",
                matchTime = "2026-05-20T12:00:00+00:00",
                description = "Cần tìm đội giao lưu, đã có sân cứng. Ưu tiên đúng giờ và fair play.",
                status = "open",
                skillLevel = "TB - Khá",
                matchFormat = "7 vs 7",
                feeType = "50/50",
                contactPhone = "0946 613 608",
                rules = listOf("Fair play"),
                teamName = "FC Tuyền Sơn",
                teamArea = "Hải Châu",
                userName = "Thanh Đoàn",
                pitchName = "Sân Tuyền Sơn"
            ),
            teams = listOf(TeamOptionDto(2, "FC Hải Châu", level = "Trung bình")),
            selectedTeamId = 2
        )
    )
}
