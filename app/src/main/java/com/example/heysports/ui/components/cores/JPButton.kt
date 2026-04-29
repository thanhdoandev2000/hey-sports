package com.example.heysports.ui.components.cores

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.theme.PrimaryGreen
import com.example.heysports.ui.theme.size_16dp
import com.example.heysports.ui.theme.size_6dp
import com.example.heysports.ui.theme.size_15sp
import com.example.heysports.ui.theme.size_50dp

@Composable
fun JPButton(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    isEnabled: Boolean = true,
    bgColor: Color = PrimaryGreen,
    textColor: Color = Color.White,
    bgDisableColor: Color = Color.Gray,
    height: Dp = size_50dp,
    width: Dp = Dp.Unspecified,
    border: BorderStroke? = null,
    mTop: Dp = size_16dp,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit = {}
) {
    Column(modifier = modifier.wrapContentSize()) {
        JPSpacer(height = mTop)
        Button(
            onClick = onClick,
            colors = ButtonColors(
                containerColor = bgColor,
                contentColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = bgDisableColor,
                disabledContentColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
                .fillMaxWidth()
                .width(width)
                .height(height),
            enabled = isEnabled,
            contentPadding = contentPadding,
            border = border,
            shape = RoundedCornerShape(size_6dp)
        ) {
            JPText(
                text = stringResource(label),
                style = MaterialTheme.typography.titleMedium,
                color = textColor,
                fontSize = size_15sp,
                fontWeight = FontWeight.SemiBold
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