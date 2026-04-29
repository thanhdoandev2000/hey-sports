package com.example.heysports.ui.components.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.heysports.ui.theme.size_16dp

@Composable
fun CommonDialog(
    onDismiss: () -> Unit,
    isDismissOnBackPress: Boolean = true,
    isDismissOnClickOutside: Boolean = true,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = isDismissOnBackPress,
            dismissOnClickOutside = isDismissOnClickOutside,
            usePlatformDefaultWidth = true,
        )
    ) {
        Column(
            Modifier
                .wrapContentSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(size_16dp)
                )
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color.Black.copy(alpha = 0.05f),
                    ambientColor = Color.Transparent
                )
                .padding(size_16dp)
        ) {
            content()
        }
    }
}