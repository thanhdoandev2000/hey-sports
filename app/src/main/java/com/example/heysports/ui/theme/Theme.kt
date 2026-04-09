package com.example.heysports.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = HeySportsPrimary,
    onPrimary = Color.White,
    secondary = HeySportsSecondary,
    onSecondary = Color.White,
    tertiary = HeySportsTertiary,
    onTertiary = Color.White,
    background = BackgroundLight,
    onBackground = Color(0xFF0F172A),

    surface = SurfaceLight,
    onSurface = Color(0xFF0F172A),

    error = Color(0xFFEF4444),
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = HeySportsPrimaryDark,
    onPrimary = Color.White,

    secondary = Color(0xFF94A3B8),
    onSecondary = Color(0xFF0F172A),

    tertiary = HeySportsTertiary,
    onTertiary = Color(0xFF0F172A),

    background = BackgroundDark,
    onBackground = Color(0xFFF8FAFC),

    surface = SurfaceDark,
    onSurface = Color(0xFFF8FAFC),

    error = Color(0xFFEF4444),
    onError = Color.White
)

@Composable
fun HeySportsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}