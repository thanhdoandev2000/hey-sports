package com.example.heysports.ui.features.main.tabs.profile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@Composable
internal fun ProfileAttribute(
    modifier: Modifier,
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size_8dp)
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .border(size_line, DividerColor, RoundedCornerShape(size_8dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PrimaryGreen,
                modifier = Modifier.size(size_24dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            JPText(
                text = label,
                color = TextSecondary,
                fontSize = size_12sp,
                maxLines = 1
            )
            JPText(
                text = value,
                color = TextPrimary,
                fontSize = size_14sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
