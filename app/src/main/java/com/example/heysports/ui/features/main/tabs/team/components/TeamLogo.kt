package com.example.heysports.ui.features.main.tabs.team.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.heysports.R
import com.example.heysports.ui.theme.DividerColor
import com.example.heysports.ui.theme.size_1dp
import com.example.heysports.ui.theme.size_4dp

@Composable
internal fun TeamLogo(
    size: Dp,
    borderColor: Color = DividerColor
) {
    Surface(
        modifier = Modifier.size(size),
        color = Color.White,
        shape = CircleShape,
        border = BorderStroke(size_1dp, borderColor)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(size_4dp),
            contentScale = ContentScale.Fit
        )
    }
}
