package com.example.heysports.ui.components.cores

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.FocusedBorderThickness
import androidx.compose.material3.OutlinedTextFieldDefaults.UnfocusedBorderThickness
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.extensions.getValue
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.model.app.StyleConfig
import com.example.heysports.ui.theme.radiusDefault

@Composable
fun JPInput(
    modifier: Modifier = Modifier,
    value: String? = null,
    isEnabled: Boolean = true,
    error: String? = null,
    config: StyleConfig = StyleConfig(),
    onValueChange: (String) -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier.fillMaxWidth()) {
        JPSpacer(height = config.mTop)
        BasicTextField(
            value = value.getValue(),
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(config.height),
            enabled = isEnabled,
            readOnly = false,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black
            ),
            maxLines = 1,
            singleLine = true,
            interactionSource = interactionSource,
            keyboardOptions = config.keyboardOptions.copy(keyboardType = config.keyboardType),
            keyboardActions = config.keyboardActions,
            decorationBox = @Composable { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = value.getValue(),
                    visualTransformation = VisualTransformation.None,
                    innerTextField = innerTextField,
                    placeholder = {
                        val newLabel =
                            stringResource(config.label ?: R.string.empty).replace("*", "")
                                .trim()
                                .lowercase()
                        val text = stringResource(
                            when {
                                config.placeholder != null -> config.placeholder
                                config.label != null -> R.string.commonSelect
                                else -> R.string.empty
                            },
                            if (config.label != null && config.placeholder == null) newLabel else ""
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.DarkGray
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(config.label ?: R.string.empty),
                            style = if (value.isNullOrBlank()) MaterialTheme.typography.titleSmall else MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = if (value.isNullOrBlank()) Color.DarkGray else MaterialTheme.colorScheme.primary
                        )
                    },
                    singleLine = config.maxLines == 1,
                    enabled = isEnabled,
                    isError = error != null,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    ),
                    trailingIcon = {},
                    colors = OutlinedTextFieldDefaults.colors(),
                    container = {
                        OutlinedTextFieldDefaults.Container(
                            enabled = isEnabled,
                            isError = error != null,
                            interactionSource = interactionSource,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                                errorBorderColor = config.errorColor,
                                cursorColor = Color.Black,
                                disabledTextColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledBorderColor = config.disableBgColor.copy(alpha = 0.3f),
                                disabledLabelColor = Color.Black.copy(alpha = 0.38f),
                                disabledPlaceholderColor = Color.DarkGray.copy(alpha = 0.38f),
                                disabledContainerColor = config.disableBgColor.copy(alpha = 0.5f),
                                disabledTrailingIconColor = Color.Gray.copy(alpha = 0.38f)
                            ),
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
                color = MaterialTheme.colorScheme.error
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