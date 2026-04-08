package com.example.heysports.ui.components.cores

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.theme.paddingDefault
import com.example.heysports.ui.theme.paddingSmall
import com.example.heysports.ui.theme.radiusDefault
import com.example.heysports.ui.theme.size_50dp

@Composable
fun JPButton(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    isEnabled: Boolean = true,
    bgColor: Color = MaterialTheme.colorScheme.primary,
    bgDisableColor: Color = Color.Gray,
    height: Dp = size_50dp,
    width: Dp = Dp.Unspecified,
    onClick: () -> Unit = {}
) {
    Column {
        JPSpacer(height = paddingDefault)
        Button(
            onClick = onClick,
            colors = ButtonColors(
                containerColor = bgColor,
                contentColor = Color.White,
                disabledContainerColor = bgDisableColor,
                disabledContentColor = Color.White
            ),
            modifier = modifier
                .fillMaxWidth()
                .width(width)
                .height(height),
            enabled = isEnabled,
            shape = RoundedCornerShape(radiusDefault)
        ) {
            JPText(
                text = stringResource(label),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
@Preview
@AppPreview
private fun JPButtonPreview() {
    JPButton(label = R.string.app_name)
}