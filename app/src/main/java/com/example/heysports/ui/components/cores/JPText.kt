package com.example.heysports.ui.components.cores

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.heysports.cores.extensions.getValue
import com.example.heysports.cores.utils.AppPreview

@Composable
fun JPText(
    modifier: Modifier = Modifier,
    text: String? = null,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    color: Color = Color.Unspecified,
    fontFamily: FontFamily = FontFamily.Default,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text.getValue(),
        modifier = modifier,
        style = style.copy(fontSize = fontSize, fontWeight = fontWeight, fontFamily = fontFamily),
        color = color,
        maxLines = maxLines
    )
}

@Composable
@AppPreview
fun JPTextPreview() {
    JPText(
        text = "Thanh Doan text",
        fontSize = 32.sp
    )
}