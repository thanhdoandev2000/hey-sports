package com.example.heysports.ui.components.cores

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.heysports.ui.theme.PrimaryGreen

@Composable
fun JPSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = PrimaryGreen,
            checkedBorderColor = Color.Transparent,
            uncheckedThumbColor = Color.White,
            uncheckedTrackColor = Color.Gray.copy(alpha = 0.4f),
            uncheckedBorderColor = Color.Transparent,
            disabledCheckedTrackColor = PrimaryGreen.copy(alpha = 0.38f),
            disabledUncheckedTrackColor = Color.Gray.copy(alpha = 0.2f),
        )
    )
}