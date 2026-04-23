package com.example.heysports.data.model.app

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import com.example.heysports.ui.theme.paddingDefault
import kotlin.Int.Companion.MAX_VALUE

data class StyleConfig(
    @param:StringRes val placeholder: Int? = null,
    @param:StringRes val label: Int? = null,
    val isEnableLabel: Boolean = true,
    val maxLines: Int = 1,
    val width: Dp = Dp.Unspecified,
    val height: Dp = Dp.Unspecified,
    val bgColor: Color = Color.Unspecified,
    val disableBgColor: Color = Color.LightGray,
    val errorColor: Color = Color.Red,
    val mTop: Dp = paddingDefault,
    val isCenterContent: Boolean = false,
    val keyboardType: KeyboardType = KeyboardType.Unspecified,
    val imeAction: ImeAction = ImeAction.Next,
    val icon: ImageVector? = null
)