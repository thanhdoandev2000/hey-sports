package com.example.heysports.ui.components.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.utils.DateTimeUtils
import com.example.heysports.cores.utils.DateTimeUtils.addDays
import com.example.heysports.cores.utils.DateTimeUtils.toDom
import com.example.heysports.cores.utils.DateTimeUtils.toDow
import com.example.heysports.cores.utils.DateTimeUtils.toMon
import com.example.heysports.cores.utils.DateTimeUtils.toSummaryDate
import com.example.heysports.data.models.enums.ETimeOption
import com.example.heysports.ui.components.cores.JPBottomSheetModal
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.navigation.screenWidth
import com.example.heysports.ui.theme.*
import java.util.Calendar

private data class DateOption(
    val calendar: Calendar,
    val dow: String,
    val dom: String,
    val mon: String,
    val hasEvent: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JPDateTimePickerSheet(
    visible: Boolean = true,
    onDismiss: () -> Unit,
    onConfirm: (Calendar) -> Unit
) {
    val today = remember { DateTimeUtils.getCurrentDate() }
    val dates = remember {
        (0 .. 13).map { offset ->
            val cal = today.addDays(offset)
            DateOption(
                calendar = cal,
                dow = cal.toDow(),
                dom = cal.toDom(),
                mon = cal.toMon(),
                hasEvent = false
            )
        }
    }

    val times = ETimeOption.entries

    var selectedDateIdx by remember { mutableIntStateOf(1) }
    var selectedTimeIdx by remember { mutableIntStateOf(9) }

    val selectedDate = dates[selectedDateIdx]
    val selectedTime = times[selectedTimeIdx]
    val summaryText = "${selectedDate.calendar.toSummaryDate()} · ${selectedTime.label}"

    JPBottomSheetModal(onDismiss = onDismiss, visible = visible, showDragHandle = true) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(bottom = size_12dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = size_16dp)) {
                JPText(
                    text = stringResource(R.string.selectDateTime),
                    fontSize = size_22sp,
                    fontWeight = FontWeight.Bold
                )
                JPText(
                    text = stringResource(R.string.selectDateTimeSubtitle),
                    fontSize = size_13sp,
                    color = TextSecondary
                )
            }

            Spacer(Modifier.height(size_16dp))
            JPText(
                text = stringResource(R.string.selectDate),
                fontSize = size_13sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = size_16dp)
            )
            Spacer(Modifier.height(size_8dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(size_8dp),
                contentPadding = PaddingValues(horizontal = size_16dp)
            ) {
                itemsIndexed(dates) { idx, item ->
                    DateCard(
                        item = item,
                        selected = idx == selectedDateIdx,
                        onClick = { selectedDateIdx = idx }
                    )
                }
            }
            Spacer(Modifier.height(size_16dp))
            JPText(
                text = stringResource(R.string.selectTime),
                fontSize = size_13sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                modifier = Modifier.padding(horizontal = size_16dp)
            )
            Spacer(Modifier.height(size_10dp))
            TimeFlowRow(
                times = times,
                selectedIdx = selectedTimeIdx,
                onSelect = { selectedTimeIdx = it },
                modifier = Modifier.padding(horizontal = size_16dp)
            )

            Spacer(Modifier.height(size_16dp))
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = size_16dp),
                color = Color(0xFFE8E8E4),
                thickness = 1.dp
            )
            Spacer(Modifier.height(size_16dp))

            WeatherCard(modifier = Modifier.padding(horizontal = size_16dp))
            Spacer(Modifier.height(size_10dp))
            SummaryPill(
                text = summaryText,
                modifier = Modifier.padding(horizontal = size_16dp)
            )
            Spacer(Modifier.height(size_16dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = size_16dp),
                horizontalArrangement = Arrangement.spacedBy(size_10dp)
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    border = BorderStroke(1.5.dp, Color(0xFFDDDDDD)),
                    shape = RoundedCornerShape(size_14dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = TextSecondary
                    )
                ) {
                    JPText(
                        text = stringResource(R.string.btnCancel),
                        color = TextSecondary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Button(
                    onClick = {
                        val result = (selectedDate.calendar.clone() as Calendar).apply {
                            set(Calendar.HOUR_OF_DAY, selectedTime.hour)
                            set(Calendar.MINUTE, selectedTime.minute)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }
                        onConfirm(result)
                    },
                    modifier = Modifier
                        .weight(2f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenDark),
                    shape = RoundedCornerShape(size_14dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(size_18dp)
                    )
                    Spacer(Modifier.width(size_6dp))
                    JPText(
                        text = stringResource(R.string.btnConfirm),
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun DateCard(
    item: DateOption,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (selected) GreenDark else Color.White
    val borderColor = if (selected) GreenDark else Color(0xFFE8E8E4)
    val dowColor = if (selected) Color.White.copy(alpha = 0.5f) else Color(0xFFB0B0B0)
    val domColor = if (selected) Color.White else TextPrimary
    val monColor = if (selected) Color.White.copy(alpha = 0.4f) else Color(0xFFCCCCCC)
    val itemWidth = (screenWidth - size_60dp) / 6
    Box(
        modifier = Modifier
            .width(itemWidth)
            .clip(RoundedCornerShape(size_16dp))
            .background(bgColor)
            .border(size_line, borderColor, RoundedCornerShape(size_16dp))
            .clickable { onClick() }
            .padding(vertical = size_8dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            JPText(
                text = item.dow,
                fontSize = size_10sp,
                fontWeight = FontWeight.Bold,
                color = dowColor
            )
            JPText(
                text = item.dom,
                fontSize = size_21sp,
                fontWeight = FontWeight.Bold,
                color = domColor
            )
            JPText(text = item.mon, fontSize = size_10sp, color = monColor)
        }
        if (item.hasEvent) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(size_8dp)
                    .size(size_6dp)
                    .background(Color.Red, RoundedCornerShape(50))
            )
        }
    }
}

// ── TimeFlowRow ────────────────────────────────────────────────────────────────

@Composable
private fun TimeFlowRow(
    times: List<ETimeOption>,
    selectedIdx: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val rows = times.chunked(4)
    var globalIdx = 0

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(size_8dp)) {
        rows.forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(size_8dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { item ->
                    val idx = globalIdx ++
                    TimeChip(
                        item = item,
                        selected = idx == selectedIdx,
                        onClick = { onSelect(idx) },
                        modifier = Modifier.weight(1f)
                    )
                }
                repeat(4 - rowItems.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun TimeChip(
    item: ETimeOption,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (selected) GreenDark else Color.White
    val borderColor = if (selected) GreenDark else Color(0xFFE8E8E4)
    val timeColor = if (selected) Color.White else TextPrimary
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(size_12dp))
            .background(bgColor)
            .border(size_line, borderColor, RoundedCornerShape(size_12dp))
            .clickable { onClick() }
            .padding(vertical = size_10dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        JPText(
            text = item.label,
            fontSize = size_14sp,
            fontWeight = FontWeight.Bold,
            color = timeColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun WeatherCard(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size_16dp))
            .background(Color(0xFFE8F4EC))
            .border(size_1dp, Color(0xFFC6E0CE), RoundedCornerShape(size_16dp))
            .padding(horizontal = size_14dp, vertical = size_13dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size_10dp)
    ) {
        JPText(text = "🌤", fontSize = size_20sp)
        Column(modifier = Modifier.weight(1f)) {
            JPText(
                text = "Thời tiết đẹp để đá bóng",
                fontSize = size_13sp,
                fontWeight = FontWeight.Bold,
                color = GreenDark
            )
            Spacer(Modifier.height(2.dp))
            JPText(
                text = "Khoảng 27°C · Ít mưa · Gió nhẹ",
                fontSize = size_12sp,
                color = Color(0xFF5A9070)
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(GreenDark)
                .padding(horizontal = 11.dp, vertical = 5.dp)
        ) {
            JPText(
                text = "✓ Tốt",
                fontSize = size_11sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun SummaryPill(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size_14dp))
            .background(Color.White)
            .border(size_1dp, Color(0xFFE8E8E4), RoundedCornerShape(size_14dp))
            .padding(horizontal = size_14dp, vertical = size_12dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size_10dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.CalendarMonth,
            contentDescription = null,
            tint = Color(0xFF888888),
            modifier = Modifier.size(size_16dp)
        )
        JPText(
            text = text,
            fontSize = size_14sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
        )
        JPText(text = "— đã chọn", fontSize = size_14sp, color = TextSecondary)
    }
}