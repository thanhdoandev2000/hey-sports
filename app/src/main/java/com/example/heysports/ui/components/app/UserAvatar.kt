package com.example.heysports.ui.components.app

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.heysports.ui.theme.GreenDark
import com.example.heysports.ui.theme.size_1dp
import com.example.heysports.ui.theme.size_2dp
import com.example.heysports.ui.theme.size_4dp
import com.example.heysports.ui.theme.size_50dp
import kotlin.math.absoluteValue

@Composable
fun UserAvatar(
    name: String,
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    size: Dp = size_50dp,
    backgroundColor: Color? = null,
    textColor: Color? = null,
    borderWidth: Dp = size_4dp
) {
    val initials = remember(name) { getInitials(name) }
    val fontSize = (size.value * 0.4f).sp
    val finalBackgroundColor = backgroundColor ?: remember(name) { generateAvatarColor(name) }
    val finalTextColor =
        textColor ?: remember(finalBackgroundColor) { getContrastColor(finalBackgroundColor) }
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(finalBackgroundColor)
            .border(borderWidth, GreenDark, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (! imageUrl.isNullOrEmpty()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Avatar of $name",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Text(
                text = initials,
                color = finalTextColor,
                fontSize = fontSize,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

private fun getInitials(name: String): String {
    if (name.isBlank()) return ""

    val words = name.trim().split("\\s+".toRegex())

    return if (words.size == 1) {
        words[0].take(2).uppercase()
    } else {
        "${words.first().first()}${words.last().first()}".uppercase()
    }
}

private fun generateAvatarColor(name: String): Color {
    if (name.isBlank()) return Color.Gray
    val hash = name.hashCode()
    val hue = (hash % 360).absoluteValue.toFloat()
    return Color.hsv(hue = hue, saturation = 0.6f, value = 0.8f)
}

private fun getContrastColor(backgroundColor: Color): Color {
    return if (backgroundColor.luminance() > 0.5f) {
        Color.Black
    } else {
        Color.White
    }
}