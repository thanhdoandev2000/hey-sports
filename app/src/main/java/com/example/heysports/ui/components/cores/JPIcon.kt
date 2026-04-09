package com.example.heysports.ui.components.cores

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.heysports.cores.utils.AppPreview

@Composable
fun JPIcon(
    icon: ImageVector,
    modifier: Modifier= Modifier,
    contentDescription: String? = null,
    size: Dp = Dp.Unspecified,
    tint: Color = MaterialTheme.colorScheme.onBackground
) {
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier.size(size),
        tint = tint
    )
}

@Preview
@Composable
@AppPreview
private fun JPIconPreview() {

}