package com.example.heysports.ui.features.main.tabs.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.heysports.ui.theme.*

@Composable
internal fun ProfileCard(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(size_16dp),
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(size_12dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = BorderStroke(size_line, DividerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = size_0)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(size_14dp),
            content = content
        )
    }
}
