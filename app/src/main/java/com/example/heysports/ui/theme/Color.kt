package com.example.heysports.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val HeySportsPrimary = Color(0xFF2E7D32)
val HeySportsPrimaryDark = Color(0xFF1B5E20)

val HeySportsSecondary = Color(0xFF1E293B)
val HeySportsTertiary = Color(0xFFF59E0B)

val BackgroundLight = Color(0xFFF8FAFC)
val SurfaceLight = Color(0xFFFFFFFF)

val BackgroundDark = Color(0xFF0F172A)
val SurfaceDark = Color(0xFF1E293B)
val LightGreenBackground = Color(0xFFE8F5E9)

// 2. Dùng màu Primary kết hợp Alpha (Khuyên dùng)
// Giả sử màu xanh chủ đạo của app bạn là màu này:
val PrimaryGreen = Color(0xFF2E7D32)
// Chỉ cần copy màu đó và set alpha (ví dụ 12%)
val HighlightBackground = PrimaryGreen.copy(alpha = 0.12f)