package com.example.heysports.ui.components.cores

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.FocusedBorderThickness
import androidx.compose.material3.OutlinedTextFieldDefaults.UnfocusedBorderThickness
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.extensions.getValue
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.model.app.StyleConfig
import com.example.heysports.ui.theme.paddingSmall
import com.example.heysports.ui.theme.radiusDefault
import com.example.heysports.ui.theme.size_15sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JPInput(
    modifier: Modifier = Modifier,
    value: String? = null,
    isEnabled: Boolean = true,
    error: String? = null,
    config: StyleConfig = StyleConfig(),
    onDone: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    val visualTransformation = if (config.keyboardType == KeyboardType.Password) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }

    val customColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Gray.copy(alpha = 0.3f),
        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
        errorBorderColor = config.errorColor,
        cursorColor = Color.Black,
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        disabledBorderColor = config.disableBgColor.copy(alpha = 0.3f),
        disabledTextColor = Color.Black.copy(alpha = 0.38f),
        disabledLabelColor = Color.Black.copy(alpha = 0.38f),
        disabledPlaceholderColor = Color.DarkGray.copy(alpha = 0.38f),
        disabledContainerColor = config.disableBgColor.copy(alpha = 0.5f)
    )

    Column(modifier = modifier.fillMaxWidth()) {
        JPSpacer(height = config.mTop)
        BasicTextField(
            value = value.getValue(),
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused },
            enabled = isEnabled,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
            maxLines = config.maxLines,
            singleLine = config.maxLines == 1,
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(
                keyboardType = config.keyboardType,
                imeAction = config.imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = {
                    focusManager.clearFocus()
                    onDone()
                }
            ),
            visualTransformation = visualTransformation,
            decorationBox = @Composable { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = value.getValue(),
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = {
                        val newLabel = stringResource(config.label ?: R.string.empty)
                            .replace("*", "").trim().lowercase()
                        val text = stringResource(
                            when {
                                config.placeholder != null -> config.placeholder
                                config.label != null -> R.string.commonEnter
                                else -> R.string.empty
                            },
                            if (config.label != null && config.placeholder == null) newLabel else ""
                        )
                        Text(text = text, style = MaterialTheme.typography.bodyMedium)
                    },
                    label = {
                        Text(
                            text = stringResource(config.label ?: R.string.empty),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = size_15sp,
                            color = if (error != null) MaterialTheme.colorScheme.error
                            else if (isFocused) MaterialTheme.colorScheme.primary
                            else Color.DarkGray
                        )
                    },
                    singleLine = config.maxLines == 1,
                    enabled = isEnabled,
                    isError = error != null,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    colors = customColors,
                    container = {
                        OutlinedTextFieldDefaults.Container(
                            enabled = isEnabled,
                            isError = error != null,
                            interactionSource = interactionSource,
                            colors = customColors,
                            shape = RoundedCornerShape(radiusDefault),
                            focusedBorderThickness = FocusedBorderThickness,
                            unfocusedBorderThickness = UnfocusedBorderThickness,
                        )
                    }
                )
            }
        )
        if (error != null) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = paddingSmall)
            )
        }
    }
}

@Preview
@Composable
@AppPreview
private fun JPInputPreview() {
    JPInput()
}