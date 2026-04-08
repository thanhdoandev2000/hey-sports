package com.example.heysports.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.app.HeaderBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeySportContainer(
    modifier: Modifier = Modifier,
    title: String? = null,
    canNavigateBack: Boolean = true,
    onNavigateUp: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            if (title != null) {
                HeaderBar(
                    title = title,
                    canNavigateBack = canNavigateBack,
                    onNavigateUp = onNavigateUp,
                    actions = actions
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            content()
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
@AppPreview
private fun HeySportContainerPreview() {
    HeySportContainer(
        title = "Trang chủ",
        canNavigateBack = false
    ) {
        Text(text = "Nội dung...", modifier = Modifier.padding(16.dp))
    }
}