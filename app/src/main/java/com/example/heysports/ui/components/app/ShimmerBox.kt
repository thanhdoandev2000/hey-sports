package com.example.heysports.ui.components.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.heysports.ui.theme.size_4dp
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerBox(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    shimmer: Shimmer,
    content: @Composable () -> Unit
) {
    if (isLoading) {
        Box(
            modifier = modifier
                .shimmer(shimmer)
                .background(Color.LightGray, RoundedCornerShape(size_4dp))
        ) {}
    } else {
        content()
    }
}