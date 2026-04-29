package com.example.heysports.ui.components.app

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.size_8dp
import com.example.heysports.ui.theme.size_12dp
import com.example.heysports.ui.theme.size_20sp

@Composable
fun SpaceContent(
    content: String = ":",
    padding: Dp = size_8dp
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        JPSpacer(width = padding)
        JPText(
            text = content,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = size_20sp,
            modifier = Modifier.padding(size_12dp)
        )
        JPSpacer(width = padding)
    }
}