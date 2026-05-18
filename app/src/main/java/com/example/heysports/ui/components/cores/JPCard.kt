package com.example.heysports.ui.components.cores

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.theme.TextSecondary
import com.example.heysports.ui.theme.size_0
import com.example.heysports.ui.theme.size_16dp
import com.example.heysports.ui.theme.size_6dp
import com.example.heysports.ui.theme.size_2dp
import com.example.heysports.ui.theme.size_line

@Composable
fun JPCard(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
    contentColor: Color = contentColorFor(containerColor),
    padding: Dp = size_16dp,
    verPadding: Dp? = null,
    hozPadding: Dp? = null,
    radius: Dp = size_6dp,
    space: Dp = size_0,
    evaluation: Dp = size_2dp,
    isCenter: Boolean = false,
    isWrapContent: Boolean = false,
    border: Dp = size_0,
    borderColor: Color = Color.Transparent,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = modifier
            .then(if (isWrapContent) modifier else modifier.fillMaxWidth())
            .background(containerColor, shape = RoundedCornerShape(radius))
            .clickable(onClick != null) {
                onClick?.invoke()
            },
        shape = RoundedCornerShape(radius),
        border = BorderStroke(width = border, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = evaluation)
    ) {
        Column(
            Modifier
                .then(if (isWrapContent) Modifier.wrapContentSize() else Modifier.fillMaxSize())
                .then(
                    if (verPadding != null && hozPadding != null) Modifier.padding(
                        horizontal = hozPadding,
                        vertical = verPadding
                    ) else Modifier.padding(padding)
                ),
            verticalArrangement = if (isCenter) Arrangement.Center else Arrangement.spacedBy(space),
            horizontalAlignment = if (isCenter) Alignment.CenterHorizontally else Alignment.Start
        ) {
            content()
        }
    }
}

@Composable
@Preview
@AppPreview
private fun JPCardPreview() {
    JPCard {}
}