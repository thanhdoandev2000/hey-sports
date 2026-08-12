package com.example.heysports.ui.components.cores

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.Container
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.example.heysports.cores.models.StyleConfig
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JPInput(
    modifier: Modifier = Modifier,
    value: String? = null,
    isEnabled: Boolean = true,
    error: String? = null,
    config: StyleConfig = StyleConfig(),
    onDone: () -> Unit = {},
    onFocusLost: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val visualTransformation =
        if (config.keyboardType == KeyboardType.Password && ! passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }


    val labelColor = when {
        error != null -> MaterialTheme.colorScheme.error
        value != null && ! config.isTextPrimaryColor -> GreenDark
        value != null || config.isSelectHiltForLabel -> TextPrimary
        else -> TextSecondary
    }

    val customColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = GreenDark,
        unfocusedBorderColor = Color.Gray,
        errorBorderColor = config.errorColor,
        cursorColor = GreenDark,
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary,
        disabledBorderColor = config.disableBgColor,
        disabledTextColor = TextPrimary.copy(alpha = 0.38f),
        disabledLabelColor = TextPrimary.copy(alpha = 0.38f),
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
                .onFocusChanged {
                    if (! it.isFocused && isFocused) onFocusLost()
                    isFocused = it.isFocused
                },
            enabled = isEnabled,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextPrimary),
            maxLines = config.maxLines,
            minLines = config.minLines,
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
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            fontSize = size_13sp
                        )
                    },
                    label = {
                        if (config.isComment && config.placeholder != null && config.label == null && (value.isNullOrBlank() && ! isFocused)) {
                            Text(
                                text = stringResource(config.placeholder),
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        } else {
                            Text(
                                text = stringResource(
                                    if (config.isSelectHiltForLabel && value.isNullOrBlank() && ! isFocused) {
                                        config.placeholder ?: R.string.empty
                                    } else {
                                        config.label ?: R.string.empty
                                    }
                                ),
                                fontWeight = FontWeight.Medium,
                                fontSize = size_15sp,
                                color = labelColor
                            )
                        }

                    },
                    singleLine = config.maxLines == 1,
                    enabled = isEnabled,
                    isError = error != null,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    colors = customColors,
                    trailingIcon = {
                        if (config.keyboardType == KeyboardType.Password) {
                            JPIcon(
                                modifier = Modifier.clickable {
                                    passwordVisible = ! passwordVisible
                                },
                                size = size_24dp,
                                icon = if (passwordVisible) Icons.Rounded.Visibility
                                else Icons.Rounded.VisibilityOff,
                                tint = if (passwordVisible) PrimaryGreen else Color.Gray
                            )
                        }
                        if (config.icon != null) {
                            JPIcon(icon = config.icon, tint = Color.Gray)
                        }
                    },
                    container = {
                        Container(
                            enabled = isEnabled,
                            isError = error != null,
                            interactionSource = interactionSource,
                            colors = customColors,
                            shape = RoundedCornerShape(HeySportsRadius.Small),
                            focusedBorderThickness = size_1dp,
                            unfocusedBorderThickness = size_line,
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
                modifier = Modifier.padding(start = size_8dp)
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