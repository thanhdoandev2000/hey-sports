package com.example.heysports.ui.components.app

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.size_16sp
import com.example.heysports.ui.theme.size_20dp
import com.example.heysports.ui.theme.size_58dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderBar(
    title: String,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavigateUp: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        title = {
            JPText(text = title, fontWeight = FontWeight.SemiBold, fontSize = size_16sp)
        },
        expandedHeight = size_58dp,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIosNew,
                        contentDescription = "Localized description",
                        modifier = modifier.size(size_20dp)
                    )
                }
            }
        },
        actions = actions,
        scrollBehavior = scrollBehavior,
    )
}
