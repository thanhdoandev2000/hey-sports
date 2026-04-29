package com.example.heysports.ui.components.cores

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import com.example.heysports.ui.theme.PrimaryGreen
import com.example.heysports.ui.theme.size_8dp
import com.example.heysports.ui.theme.size_13dp
import com.example.heysports.ui.theme.size_1dp
import com.example.heysports.ui.theme.size_20dp
import com.example.heysports.ui.theme.size_4dp

@Composable
fun JPCheckBox(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    checkedColor: Color = PrimaryGreen,
    textColor: Color = Color.Black
) {
    val borderColor = Color(0xFF2D6A2D)
    val bgAnim by animateColorAsState(
        targetValue = if (checked) checkedColor else Color.Transparent,
        animationSpec = tween(150)
    )
    val scaleAnim by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(
                role = Role.Checkbox,
                onClick = { onCheckedChange(! checked) }
            )
            .padding(vertical = size_8dp, horizontal = size_4dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(size_20dp)
                .clip(RoundedCornerShape(size_4dp))
                .border(
                    width = size_1dp,
                    color = borderColor,
                    shape = RoundedCornerShape(size_4dp)
                )
                .background(bgAnim)
        ) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(size_13dp)
                    .scale(scaleAnim)
            )
        }

        Spacer(modifier = Modifier.width(size_8dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}