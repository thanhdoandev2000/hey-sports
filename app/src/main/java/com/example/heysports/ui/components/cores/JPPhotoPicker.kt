package com.example.heysports.ui.components.cores

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.theme.*

@Composable
fun JPPhotoPicker(
    modifier: Modifier = Modifier,
    onClickListener: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(size_100dp)
            .width(size_100dp)
            .clip(RoundedCornerShape(size_8dp))
            .background(Color.White)
            .border(
                width = size_line,
                color = Color(0xFFDDDDDD),
                shape = RoundedCornerShape(size_8dp)
            )
            .clickable { onClickListener() },
        contentAlignment = Alignment.Center
    ) {
        JPIcon(
            icon = Icons.Rounded.PhotoLibrary,
            tint = TextSecondary,
            size = size_32dp
        )
    }
}

@Composable
@Preview
@AppPreview
fun JPPhotoPickerPreview() {
    JPPhotoPicker()
}