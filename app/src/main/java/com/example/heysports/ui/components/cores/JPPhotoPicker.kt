package com.example.heysports.ui.components.cores

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.app.ImagePickerBottomSheet
import com.example.heysports.ui.components.app.ShimmerBox
import com.example.heysports.ui.theme.*
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JPPhotoPicker(
    modifier: Modifier = Modifier,
    imageUri: String? = null,
    onImageSelected: (Uri?) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .height(size_100dp)
            .width(size_100dp)
            .clip(RoundedCornerShape(size_8dp))
            .background(Color.White)
            .then(
                if (imageUri == null) Modifier
                    .border(
                        size_line,
                        Color(0xFFDDDDDD),
                        RoundedCornerShape(size_8dp)
                    )
                    .clickable { showSheet = true } else Modifier),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            SubcomposeAsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                loading = {
                    ShimmerBox(
                        isLoading = true,
                        modifier = Modifier.fillMaxSize(),
                        shimmer = rememberShimmer(ShimmerBounds.View)
                    ) {
                    }
                }
            )
        } else {
            JPIcon(
                icon = Icons.Rounded.PhotoLibrary,
                tint = TextSecondary,
                size = size_32dp
            )
        }
    }

    if (showSheet) {
        ImagePickerBottomSheet(
            onDismiss = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    showSheet = false
                }
            },
            onImageSelected = onImageSelected
        )
    }
}

@Composable
@Preview
@AppPreview
fun JPPhotoPickerPreview() {
    JPPhotoPicker()
}