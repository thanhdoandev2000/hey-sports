package com.example.heysports.ui.components.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.cores.JPOutlineButton
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@Composable
fun GlobalErrorDialog(
    messages: List<String>,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = messages.isNotEmpty(),
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 300)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 300)
        )
    ) {
        CommonDialog(
            onDismiss = onDismiss,
            isDismissOnBackPress = false,
            isDismissOnClickOutside = false
        ) {
            JPText(
                text = stringResource(R.string.errorTitle),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = size_16sp,
                fontWeight = FontWeight.Bold
            )
            JPSpacer(height = size_8dp)
            messages.forEach {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    if (messages.size != 1) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = size_4dp)
                                .height(size_8dp)
                                .width(size_8dp)
                                .clip(CircleShape)
                                .background(PrimaryGreen)
                        )
                        JPSpacer(width = size_4dp)
                    }
                    JPText(
                        text = it,
                        textAlign = if (messages.size == 1) TextAlign.Center else TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            JPOutlineButton(
                onClick = onDismiss,
                height = size_32dp,
                label = R.string.btnLabelOk,
                mTop = size_8dp
            )
        }
    }
}

@Composable
@Preview
@AppPreview
private fun GlobalErrorDialogPreview() {
    GlobalErrorDialog(messages = listOf("Lỗi gì đó"), onDismiss = {})
}