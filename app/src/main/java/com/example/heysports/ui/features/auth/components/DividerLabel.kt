package com.example.heysports.ui.features.auth.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.app.CustomLine
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.paddingDefault

@Composable
fun DividerLabel(@StringRes label: Int = R.string.authOrLogin) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = paddingDefault),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomLine(modifier = Modifier.weight(1f))
        JPSpacer(width = paddingDefault)
        JPText(text = stringResource(label))
        JPSpacer(width = paddingDefault)
        CustomLine(modifier = Modifier.weight(1f))
    }
}

@Composable
@AppPreview
@Preview
private fun DividerLabelPreview() {
    DividerLabel()
}