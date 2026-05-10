package com.example.heysports.ui.components.app

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.cores.JPPhotoPicker
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.size_10dp
import com.example.heysports.ui.theme.size_12dp
import com.example.heysports.ui.theme.size_15sp
import com.example.heysports.ui.theme.size_16dp

@Composable
fun JPAttachPhoto(
    modifier: Modifier = Modifier,
    mTop: Dp = size_16dp,
    items: List<String?> = listOf(null, null, null, null),
    label: Int = R.string.attachPhotos
) {
    Column(modifier = modifier) {
        JPSpacer(height = mTop)
        JPText(text = stringResource(label), fontSize = size_15sp, fontWeight = FontWeight.Medium)
        JPSpacer(size_12dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(size_10dp)
        ) {
            JPPhotoPicker { }
            items.forEach {
                JPPhotoPicker { }
            }
        }
    }
}

@Composable
@AppPreview
@Preview
fun JPAttachPhotoPreview() {
    JPAttachPhoto()
}