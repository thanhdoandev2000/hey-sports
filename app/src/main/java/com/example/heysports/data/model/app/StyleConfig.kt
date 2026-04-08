package com.example.heysports.data.model.app

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import kotlin.Int.Companion.MAX_VALUE

data class StyleConfig(
    @param:StringRes val placeholder: Int? = null,
    @param:StringRes val label: Int? = null,
    val maxLines: Int = MAX_VALUE,
    val width: Dp = Dp.Unspecified,
    val height: Dp = Dp.Unspecified,
    val bgColor: Color = Color.Unspecified,
    val disableBgColor: Color = Color.LightGray,
    val errorColor: Color = Color.Red,
    val keyboardType: KeyboardType = KeyboardType.Unspecified,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
)