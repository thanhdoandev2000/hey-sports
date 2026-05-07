package com.example.heysports.ui.features.main.tabs.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.heysports.cores.utils.DateTimeUtils
import com.example.heysports.data.models.enums.ETimeType
import com.example.heysports.ui.components.app.SpaceContent
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.CardShape
import com.example.heysports.ui.theme.size_12dp
import com.example.heysports.ui.theme.size_20sp
import com.example.heysports.ui.theme.size_4dp
import kotlinx.coroutines.delay

@Composable
fun CountdownTimer(matchTime: String?) {
    var diff by remember { mutableLongStateOf(0L) }
    val isToday = DateTimeUtils.isToday(matchTime.toString())

    LaunchedEffect(matchTime) {
        val matchDate = matchTime?.let { DateTimeUtils.parseMatchTime(it) }
            ?: return@LaunchedEffect

        while (true) {
            diff = matchDate.time - System.currentTimeMillis()
            if (diff <= 0) {
                diff = 0L; break
            }
            delay(1000L)
        }
    }

    Row(Modifier.wrapContentSize()) {
        TimerChip(diff = diff, type = if (isToday) ETimeType.TIME else ETimeType.DATE)
        SpaceContent()
        TimerChip(diff = diff, type = if (isToday) ETimeType.MINUTE else ETimeType.TIME)
        SpaceContent()
        TimerChip(diff = diff, type = if (isToday) ETimeType.SECOND else ETimeType.MINUTE)
    }
}


@Composable
private fun TimerChip(
    modifier: Modifier = Modifier,
    diff: Long = 0L,
    type: ETimeType = ETimeType.DATE,
) {
    val time = DateTimeUtils.getTimerValue(diff, type)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CardShape,
            color = Color.White.copy(alpha = 0.15f),
            modifier = Modifier.wrapContentSize()
        ) {
            JPText(
                text = time,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = size_20sp,
                modifier = Modifier.padding(size_12dp)
            )
        }
        JPSpacer(height = size_4dp)
        JPText(text = type.type, color = Color.White)
    }
}