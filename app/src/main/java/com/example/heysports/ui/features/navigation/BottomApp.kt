package com.example.heysports.ui.features.navigation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.models.enums.EBottomTabs
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.GreenDark
import com.example.heysports.ui.theme.size_20dp
import com.example.heysports.ui.theme.size_24dp
import com.example.heysports.ui.theme.size_26dp
import com.example.heysports.ui.theme.size_28dp
import io.ktor.websocket.Frame
import kotlin.math.roundToInt

@Composable
fun BottomApp(
    modifier: Modifier = Modifier,
    navController: NavController,
    offsetHeightPx: Float
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    NavigationBar(
        windowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = GreenDark,
        tonalElevation = 0.dp,
        modifier = modifier
            .offset { IntOffset(x = 0, y = - offsetHeightPx.roundToInt()) }
            .navigationBarsPadding()
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = size_20dp, topStart = size_20dp))
            .graphicsLayer {
                shape = RoundedCornerShape(topEnd = size_20dp, topStart = size_20dp)
                clip = true
            }
    ) {
        EBottomTabs.entries.forEach { destination ->
            val isSelected =
                currentRoute?.hierarchy?.any { it.hasRoute(destination.route::class) } == true

            val iconSize by animateDpAsState(
                targetValue = if (isSelected) size_26dp else size_24dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "BottomBarIconSize"
            )

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (isSelected) return@NavigationBarItem
                    navController.navigate(route = destination.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.label,
                        modifier = Modifier.size(iconSize)
                    )
                },
                label = {
                    Text(
                        text = destination.label,
                        style = if (isSelected) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF1A5319),
                    selectedTextColor = Color.White,
                    indicatorColor = Color.White,
                    unselectedIconColor = Color.White.copy(alpha = 0.55f),
                    unselectedTextColor = Color.White.copy(alpha = 0.55f)
                )
            )
        }
    }
}

@Preview
@AppPreview
@Composable
private fun BottomAppPreview() {
    BottomApp(navController = rememberNavController(), modifier = Modifier, offsetHeightPx = 0f)
}