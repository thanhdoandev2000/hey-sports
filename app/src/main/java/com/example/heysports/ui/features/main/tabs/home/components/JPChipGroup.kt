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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> JPChipGroup(
    items: List<T>,
    modifier: Modifier = Modifier,
    selected: T,
    hozPadding: Dp = size_16dp,
    verPadding: Dp = size_8dp,
    onSelected: (T) -> Unit,
    bgColor: Color = GreenDark,
    textColor: Color = Color.White,
    iconColor: Color = textColor,
    radius: Dp = size_8dp,
    label: (T) -> String,
    icon: ((T) -> ImageVector?) = { null }
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
                    .clip(RoundedCornerShape(radius))
                    .background(if (isSelected) bgColor else Color.Transparent)
                    .border(
                        width = size_1dp,
                        color = if (isSelected) PrimaryGreen else Color(0xFFDDDDDD),
                        shape = RoundedCornerShape(radius)
                    )
                    .clickable { onSelected(item) }
                    .padding(horizontal = hozPadding, vertical = verPadding),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(size_4dp)
                ) {
                    icon(item)?.let {
                        JPIcon(
                            icon = it,
                            tint = if (isSelected) iconColor else TextSecondary,
                            size = size_20dp
                        )
                    }

                    JPText(
                        text = label(item),
                        fontSize = size_13sp,
                        fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                        color = if (isSelected) textColor else TextSecondary
                    )
                }
            }
        }
    }
}