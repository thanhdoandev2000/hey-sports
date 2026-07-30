package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.theme.*

@Composable
internal fun RowScope.TeamHeaderAction(
    hasTeam: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Box(
            modifier = Modifier
                .size(size_34dp)
                .border(
                    width = size_1dp,
                    color = Color.White.copy(alpha = 0.82f),
                    shape = RoundedCornerShape(size_8dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            JPIcon(
                icon = if (hasTeam) Icons.Default.Add else Icons.Default.GroupAdd,
                tint = Color.White,
                size = size_20dp
            )

            if (! hasTeam) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 3.dp, y = (- 3).dp)
                        .size(size_8dp)
                        .background(RedColor, CircleShape)
                        .border(size_1dp, PrimaryGreen, CircleShape)
                )
            }
        }
    }
}
