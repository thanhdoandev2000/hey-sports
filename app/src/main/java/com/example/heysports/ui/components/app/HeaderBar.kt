package com.example.heysports.ui.components.app

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderBar(
    title: String,
    modifier: Modifier = Modifier,
    subTitle: Any? = null,
    canNavigateBack: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavigateUp: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PrimaryGreen,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        title = {
            Column(Modifier
                .wrapContentHeight()
                .fillMaxWidth()) {
                JPText(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = size_16sp,
                    color = Color.White
                )
                subTitle?.let {
                    val subText = when (it) {
                        is Int -> stringResource(it)
                        is String -> it
                        else -> ""
                    }
                    if (subText.isNotEmpty()) {
                        JPText(
                            text = subText,
                            fontWeight = FontWeight.Normal,
                            fontSize = size_12sp,
                            color = Color.White
                        )
                    }
                }
            }
        },
        expandedHeight = size_58dp,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Localized description",
                        modifier = modifier.size(size_20dp),
                        tint = Color.White
                    )
                }
            }
        },
        actions = actions,
        scrollBehavior = scrollBehavior,
    )
}
