package com.example.heysports.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.app.HeaderBar
import com.example.heysports.ui.components.cores.JPDialogLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeySportContainer(
    modifier: Modifier = Modifier,
    title: String? = null,
    canNavigateBack: Boolean = true,
    isEdgeToEdge: Boolean = false,
    isLoading: Boolean = false,
    bgColor: Color = MaterialTheme.colorScheme.background,
    onNavigateUp: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
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
        containerColor = bgColor,
        contentWindowInsets = if (isEdgeToEdge) WindowInsets(0) else ScaffoldDefaults.contentWindowInsets
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .then(if (isEdgeToEdge) Modifier else Modifier.padding(paddingValues))
        ) {
            content(paddingValues)
            JPDialogLoading(isLoading)
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