package com.example.heysports.ui.components.cores

import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.theme.*

@Composable
fun JPOutlineButton(
    modifier: Modifier = Modifier,
    @StringRes label: Int = R.string.app_name,
    @StringRes imgRes: Int? = null,
    icon: ImageVector? = null,
    iconSize: Dp = size_24dp,
    bgColor: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    content: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    borderColor: Color = PrimaryGreen,
    contentColor: Color = TextPrimary,
    textSize: TextUnit = size_15sp,
    height: Dp = size_50dp,
    mTop: Dp = size_16dp,
    @FloatRange(from = 0.0, fromInclusive = false) weight: Float = 1f,
    fontWeight: FontWeight = FontWeight.SemiBold,
    onClick: () -> Unit
) {
    Column {
        JPSpacer(height = mTop)
        OutlinedButton(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier
                .fillMaxWidth(weight)
                .height(height),
            shape = RoundedCornerShape(size_6dp),
            contentPadding = PaddingValues(size_0),
            colors = bgColor,
            border = BorderStroke(size_line, borderColor)
        ) {
            if (content == null) {
                if (imgRes != null) {
                    Image(
                        painter = painterResource(imgRes),
                        modifier = Modifier.size(iconSize),
                        contentDescription = "icon $label}"
                    )
                    JPSpacer(width = size_8dp)
                }
                if (icon != null) {
                    JPIcon(
                        icon = icon,
                        size = iconSize,
                        contentDescription = "icon $label}",
                        tint = contentColor
                    )
                    JPSpacer(width = size_8dp)
                }
                JPText(
                    text = stringResource(label),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = textSize,
                    fontWeight = fontWeight,
                    color = contentColor
                )
            } else {
                content()
            }
        }
    }
}

@Composable
@Preview
@AppPreview
fun JPOutlineButtonPreview() {
    JPOutlineButton {

    }
}