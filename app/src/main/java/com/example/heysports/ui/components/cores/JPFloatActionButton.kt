package com.example.heysports.ui.components.cores

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import com.example.heysports.ui.features.navigation.LocalBottomBarOffset
import com.example.heysports.ui.features.navigation.isBottomBarHidden
import com.example.heysports.ui.features.navigation.paddingBottomTab
import com.example.heysports.ui.theme.GreenDark
import com.example.heysports.ui.theme.size_16dp
import com.example.heysports.ui.theme.size_24dp
import kotlin.math.roundToInt

@Composable
fun JPFloatActionButton(modifier: Modifier, onClick: () -> Unit) {
    val bottomBarOffset = LocalBottomBarOffset.current

    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .then(if (isBottomBarHidden) Modifier.navigationBarsPadding() else Modifier)
            .offset { IntOffset(x = 0, y = - bottomBarOffset.roundToInt()) }
            .padding(
                bottom = paddingBottomTab,
                end = size_16dp
            ),
        containerColor = GreenDark,
        contentColor = Color.White,
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier.size(size_24dp)
        )
    }
}