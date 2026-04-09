package com.example.heysports.ui.components.cores

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.theme.paddingDefault
import com.example.heysports.ui.theme.radiusDefault
import com.example.heysports.ui.theme.size_2dp

@Composable
fun JPCard(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
    contentColor: Color = contentColorFor(containerColor),
    padding: Dp = paddingDefault,
    radius: Dp = radiusDefault,
    content: @Composable () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(radius),
        elevation = CardDefaults.cardElevation(defaultElevation = size_2dp)
    ) {
        Column(Modifier.padding(padding)) {
            content()
        }
    }
}

@Composable
@Preview
@AppPreview
private fun JPCardPreview() {
    JPCard {}
}