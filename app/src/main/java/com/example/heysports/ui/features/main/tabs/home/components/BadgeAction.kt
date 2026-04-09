package com.example.heysports.ui.features.main.tabs.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.theme.size_2dp

@Composable
fun FireBadge(count: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size_2dp)
    ) {
        JPIcon(icon = Icons.Outlined.LocalFireDepartment)
        Text(text = count.toString())
    }
}

@Composable
fun CommentBadge(count: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size_2dp)
    ) {
        JPIcon(icon = Icons.Outlined.ChatBubbleOutline)
        Text(text = count.toString())
    }
}