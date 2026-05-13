package com.example.heysports.ui.features.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import com.example.heysports.ui.theme.size_12dp

val LocalBottomBarHeight = compositionLocalOf { 0f }
val LocalBottomBarOffset = compositionLocalOf { 0f }

val isBottomBarHidden: Boolean
    @Composable get() {
        val height = LocalBottomBarHeight.current
        val offset = LocalBottomBarOffset.current
        return height > 0f && offset <= - height
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