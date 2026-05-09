package com.example.heysports.ui.components.cores

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.example.heysports.R
import com.example.heysports.ui.theme.PrimaryGreen
import com.example.heysports.ui.theme.size_14sp

@Composable
fun JPTextButton(
    modifier: Modifier = Modifier,
    @StringRes label: Int = R.string.empty,
    txtColor: Color = PrimaryGreen,
    fontWeight: FontWeight = FontWeight.Medium,
    onClick: () -> Unit,
    fontSize: TextUnit = size_14sp,
    padding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    content: (@Composable () -> Unit)? = null
) {
    TextButton(onClick = onClick, modifier = modifier, contentPadding = padding) {
        if (content != null) {
            content()
        } else {
            JPText(
                text = stringResource(label),
                color = txtColor,
                fontWeight = fontWeight,
                fontSize = fontSize
            )
        }
    }
}