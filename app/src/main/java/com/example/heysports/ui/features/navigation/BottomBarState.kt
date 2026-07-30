package com.example.heysports.ui.features.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import com.example.heysports.ui.theme.size_12dp
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

val LocalBottomBarHeight = compositionLocalOf { 0f }
val LocalBottomBarOffset = compositionLocalOf<FloatState> { mutableFloatStateOf(0f) }

val isBottomBarHidden: Boolean
    @Composable get() {
        val height = LocalBottomBarHeight.current
        val offsetState = LocalBottomBarOffset.current
        return remember(height, offsetState) {
            derivedStateOf {
                height > 0f && offsetState.floatValue <= - height
            }
        }.value
    }

val bottomBarHeightDp: Dp
    @Composable get() = with(LocalDensity.current) {
        LocalBottomBarHeight.current.toDp()
    }

val containerSize @Composable get() = LocalWindowInfo.current.containerSize

val screenHeight: Dp
    @Composable get() = with(LocalDensity.current) { containerSize.height.toDp() }

val screenWidth: Dp
    @Composable get() = with(LocalDensity.current) { containerSize.width.toDp() }

val paddingBottomTab: Dp
    @Composable get() = with(LocalDensity.current) { LocalBottomBarHeight.current.toDp() } + size_12dp

val shimmer: Shimmer
    @Composable get() = rememberShimmer(ShimmerBounds.View)
