package com.example.heysports.ui.components.app

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Money
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.cores.JPCard
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@Composable
fun JPChipIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String = "",
    color: Color = Color.White,
    contentColor: Color = TextSecondary,
    iconColor: Color = GreenDark,
    isViewOnly: Boolean = true,
    radius: Dp = size_4dp,
    textSize: TextUnit = size_13sp,
    iconSize: Dp = size_20dp,
    evaluation: Dp = size_0,
    isCenter: Boolean = true,
    borderColor: Color = TextSecondary.copy(0.35f),
    onClick: () -> Unit = {}
) {
    JPCard(
        modifier = modifier
            .clickable(!isViewOnly) { onClick() }
            .wrapContentSize(),
        containerColor = color,
        radius = radius,
        isWrapContent = true,
        verPadding = size_4dp,
        hozPadding = size_6dp,
        border = size_line,
        borderColor = borderColor,
        evaluation = evaluation,
        isCenter = isCenter
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(size_4dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            JPIcon(icon = icon, tint = iconColor, size = iconSize)
            JPText(text = label, fontSize = textSize, color = contentColor)
        }
    }
}

@Composable
@AppPreview
@Preview
private fun JPChipIconPreview() {
    JPChipIcon(
        icon = Icons.Outlined.Money,
        onClick = {}
    )
}