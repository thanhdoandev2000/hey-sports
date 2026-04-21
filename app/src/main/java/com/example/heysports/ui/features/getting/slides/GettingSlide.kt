package com.example.heysports.ui.features.getting.slides

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.getting.GettingSlide
import com.example.heysports.ui.theme.paddingMedium
import com.example.heysports.ui.theme.paddingSmall
import com.example.heysports.ui.theme.size_140dp
import com.example.heysports.ui.theme.size_14sp
import com.example.heysports.ui.theme.size_20sp
import com.example.heysports.ui.theme.size_4dp
import com.example.heysports.ui.theme.size_80dp

@Composable
fun GettingSlide(slide: GettingSlide) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(paddingMedium),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(size_140dp)
                .clip(CircleShape)
                .background(Color(0xFFE8F5E9)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = slide.icon,
                contentDescription = null,
                modifier = Modifier.size(size_80dp),
                tint = Color(0xFF388E3C)
            )
        }
        JPSpacer(height = paddingSmall)
        JPText(
            text = stringResource(slide.title),
            fontSize = size_20sp,
            fontWeight = FontWeight.Bold
        )
        JPSpacer(height = size_4dp)
        JPText(
            fontSize = size_14sp,
            textAlign = TextAlign.Center,
            text = stringResource(slide.description)
        )
    }
}

@Composable
@Preview
@AppPreview
private fun GettingSlidePreview() {
    GettingSlide(
        GettingSlide(
            title = R.string.gettingTitle,
            description = R.string.gettingFindMatches,
            icon = Icons.Outlined.SportsSoccer
        )
    )
}