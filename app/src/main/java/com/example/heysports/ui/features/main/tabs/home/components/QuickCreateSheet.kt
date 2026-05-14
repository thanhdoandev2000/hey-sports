package com.example.heysports.ui.features.main.tabs.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.heysports.R
import com.example.heysports.domain.models.PostModel
import com.example.heysports.ui.components.app.ActionItem
import com.example.heysports.ui.components.app.CustomLine
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@Composable
fun QuickCreateSheet(
    items: List<PostModel>,
    onDismiss: () -> Unit,
    onItemClick: (PostModel) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        QuickCreateHeader(onDismiss = onDismiss)
        items.forEachIndexed { index, model ->
            ActionItem(model) { onItemClick(model) }
            if (index != items.lastIndex) CustomLine(color = Color.LightGray.copy(0.5f))
        }
    }
}

@Composable
private fun QuickCreateHeader(onDismiss: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                JPText(
                    text = stringResource(R.string.quickCreateTitle),
                    color = TextPrimary,
                    fontSize = size_24sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(size_6dp))
                JPIcon(
                    icon = Icons.Outlined.Bolt,
                    tint = PrimaryGreen,
                    size = size_20dp
                )
            }
            JPText(
                text = stringResource(R.string.quickCreateSubtitle),
                color = TextSecondary,
                fontSize = size_13sp
            )
        }

        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .size(size_40dp)
                .background(Color.White, CircleShape)
        ) {
            JPIcon(
                icon = Icons.Default.Close,
                tint = TextPrimary,
                size = size_20dp
            )
        }
    }
}