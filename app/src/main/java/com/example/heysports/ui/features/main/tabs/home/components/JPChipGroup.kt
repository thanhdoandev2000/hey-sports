package com.example.heysports.ui.features.main.tabs.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> JPChipGroup(
    items: List<T>,
    selected: T,
    onSelected: (T) -> Unit,
    label: (T) -> String,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(size_8dp),
        verticalArrangement = Arrangement.spacedBy(size_8dp)
    ) {
        items.forEach { item ->
            val isSelected = item == selected
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (isSelected) GreenDark else Color.Transparent
                    )
                    .border(
                        width = size_1dp,
                        color = if (isSelected) GreenDark else Color(0xFFDDDDDD),
                        shape = RoundedCornerShape(50)
                    )
                    .clickable { onSelected(item) }
                    .padding(horizontal = size_16dp, vertical = size_8dp),
                contentAlignment = Alignment.Center
            ) {
                JPText(
                    text = label(item),
                    fontSize = size_13sp,
                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                    color = if (isSelected) Color.White else TextSecondary
                )
            }
        }
    }
}