package com.example.heysports.ui.components.app

import com.example.heysports.domain.models.PostModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@Composable
fun ActionItem(
    item: PostModel,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = size_16dp, vertical = size_10dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size_12dp)

    ) {
        Box(
            modifier = Modifier
                .size(size_40dp)
                .background(
                    color = item.color.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(size_10dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            JPIcon(
                icon = item.icon,
                tint = item.color,
                size = size_20dp
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            JPText(
                text = stringResource(item.title),
                fontWeight = FontWeight.Medium,
                fontSize = size_14sp,
                color = Color.Black
            )
            JPText(
                text = stringResource(item.content),
                fontSize = size_12sp,
                color = Color.Gray
            )
        }
        JPIcon(
            icon = Icons.Default.ChevronRight,
            tint = Color.LightGray,
            size = size_18dp
        )
    }
}