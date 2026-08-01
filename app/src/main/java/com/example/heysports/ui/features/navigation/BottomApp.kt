package com.example.heysports.ui.features.navigation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.models.enums.EBottomTabs
import com.example.heysports.ui.theme.PrimaryGreen
import com.example.heysports.ui.theme.size_14sp
import com.example.heysports.ui.theme.size_16dp
import com.example.heysports.ui.theme.size_24dp
import com.example.heysports.ui.theme.size_72dp

@Composable
fun BottomApp(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val barShape = RoundedCornerShape(topEnd = size_16dp, topStart = size_16dp)

    NavigationBar(
        windowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = PrimaryGreen,
        tonalElevation = 0.dp,
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .height(size_72dp)
            .clip(barShape)
    ) {
        EBottomTabs.entries.forEach { destination ->
            val isSelected =
                currentRoute?.hierarchy?.any { it.hasRoute(destination.route::class) } == true

            val iconSize by animateDpAsState(
                targetValue = if (isSelected) size_24dp else 22.dp,
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
                        style = if (isSelected) {
                            MaterialTheme.typography.titleMedium.copy(
                                fontSize = size_14sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        } else {
                            MaterialTheme.typography.bodySmall
                        }
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryGreen,
                    selectedTextColor = Color.White,
                    indicatorColor = Color.White,
                    unselectedIconColor = Color.White.copy(alpha = 0.68f),
                    unselectedTextColor = Color.White.copy(alpha = 0.68f)
                )
            )
        }
    }
}

@Preview
@AppPreview
@Composable
private fun BottomAppPreview() {
    BottomApp(navController = rememberNavController(), modifier = Modifier)
}
