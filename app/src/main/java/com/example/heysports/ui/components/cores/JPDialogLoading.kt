package com.example.heysports.ui.components.cores

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.heysports.ui.theme.size_16dp
import com.example.heysports.ui.theme.size_8dp
import com.example.heysports.ui.theme.size_110dp
import com.example.heysports.ui.theme.size_14sp
import com.example.heysports.ui.theme.size_36dp
import com.example.heysports.ui.theme.size_4dp

@Composable
fun JPDialogLoading(
    isShowing: Boolean,
    message: String = "Đang xử lý..."
) {
    if (isShowing) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(size_110dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(size_16dp)
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(size_36dp),
                        strokeWidth = size_4dp
                    )

                    Spacer(modifier = Modifier.height(size_8dp))

                    JPText(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = size_14sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun JPDialogLoadingPreview() {
    JPDialogLoading(isShowing = true)
}