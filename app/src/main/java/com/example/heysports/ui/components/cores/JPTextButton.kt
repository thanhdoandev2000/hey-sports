package com.example.heysports.ui.components.cores

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun JPTextButton(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    txtColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick, modifier = modifier) {
        JPText(
            text = stringResource(label),
            color = txtColor
        )
    }
}