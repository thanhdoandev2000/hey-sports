package com.example.heysports.ui.components.app

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.components.helpers.rememberImagePickerLauncher
import com.example.heysports.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePickerBottomSheet(
    onDismiss: () -> Unit,
    onImageSelected: (Uri?) -> Unit
) {
    val picker = rememberImagePickerLauncher(
        onImageSelected = {
            onImageSelected(it)
            onDismiss()
        }
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.Transparent,
        dragHandle = null,
        contentWindowInsets = { WindowInsets(0) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = size_16dp)
                .navigationBarsPadding()
                .padding(bottom = size_16dp),
            verticalArrangement = Arrangement.spacedBy(size_8dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(size_20dp))
                    .background(Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .width(size_36dp)
                        .height(size_3dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = size_12dp)
                        .background(Color(0xFFDDDDDD), RoundedCornerShape(50))
                )

                JPSpacer(height = size_16dp)

                JPText(
                    text = "Chọn ảnh từ",
                    fontSize = size_13sp,
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = size_16dp)
                )

                JPSpacer(height = size_12dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawLine(
                                color = Color(0xFFEEEEEE),
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = 0.5.dp.toPx()
                            )
                        }
                        .clickable { picker.launchCamera() }
                        .padding(horizontal = size_16dp, vertical = size_14dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(size_14dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(size_42dp)
                            .background(Color(0xFFE8F5E9), RoundedCornerShape(size_12dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CameraAlt,
                            contentDescription = null,
                            tint = GreenDark,
                            modifier = Modifier.size(size_24dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        JPText(
                            text = "Chụp ảnh",
                            fontSize = size_15sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary
                        )
                        JPText(
                            text = "Mở camera và chụp ngay",
                            fontSize = size_12sp,
                            color = TextSecondary
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(size_18dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawLine(
                                color = Color(0xFFEEEEEE),
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = 0.5.dp.toPx()
                            )
                        }
                        .clickable { picker.launchGallery() }
                        .padding(horizontal = size_16dp, vertical = size_14dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(size_14dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(size_42dp)
                            .background(Color(0xFFE3F2FD), RoundedCornerShape(size_12dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PhotoLibrary,
                            contentDescription = null,
                            tint = Color(0xFF1565C0),
                            modifier = Modifier.size(size_24dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        JPText(
                            text = "Thư viện ảnh",
                            fontSize = size_15sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary
                        )
                        JPText(
                            text = "Chọn từ ảnh có sẵn",
                            fontSize = size_12sp,
                            color = TextSecondary
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(size_18dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(size_16dp))
                    .background(Color.White)
                    .clickable { onDismiss() }
                    .padding(vertical = size_14dp),
                contentAlignment = Alignment.Center
            ) {
                JPText(
                    text = "Huỷ",
                    fontSize = size_15sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFE24B4A)
                )
            }
        }
    }
}