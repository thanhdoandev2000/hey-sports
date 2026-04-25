package com.example.heysports.ui.components.cores

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.heysports.R

@Composable
fun JPTextButton(
    modifier: Modifier = Modifier,
    @StringRes label: Int = R.string.empty,
    txtColor: Color = MaterialTheme.colorScheme.primary,
    fontWeight: FontWeight = FontWeight.Medium,
    onClick: () -> Unit,
    content: (@Composable () -> Unit)? = null
) {
    TextButton(onClick = onClick, modifier = modifier) {
        if (content != null) {
            content()
        } else {
            JPText(
                text = stringResource(label),
                color = txtColor,
                fontWeight = fontWeight
            )
        }
    }
}