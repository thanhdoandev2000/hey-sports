package com.example.heysports.ui.components.cores

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.extensions.getValue
import com.example.heysports.cores.models.StyleConfig
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.theme.*

data class JPDropdownSheetState<T>(
    val visible: Boolean,
    val items: List<T>,
    val value: String?,
    val selectedItem: T?,
    val dismiss: () -> Unit,
    val select: (T) -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> JPDropdown(
    modifier: Modifier = Modifier,
    value: String? = null,
    selectedItem: T? = null,
    items: List<T> = emptyList(),
    isEnabled: Boolean = true,
    error: String? = null,
    config: StyleConfig = StyleConfig(),
    onSelected: (T) -> Unit = {},
    sheetContent: @Composable (JPDropdownSheetState<T>) -> Unit = {}
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier.fillMaxWidth()) {
        JPSpacer(height = config.mTop)

        Box(
            modifier = Modifier
                .width(config.width)
                .fillMaxWidth()
                .height(config.height)
        ) {
            BasicTextField(
                value = value.getValue(),
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .focusProperties { canFocus = false },
                enabled = isEnabled,
                readOnly = true,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                interactionSource = interactionSource,
                decorationBox = { innerTextField ->
                    OutlinedTextFieldDefaults.DecorationBox(
                        value = value.getValue(),
                        visualTransformation = VisualTransformation.None,
                        innerTextField = innerTextField,
                        placeholder = {
                            val newLabel =
                                stringResource(config.label ?: R.string.empty)
                                    .replace("*", "")
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
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.DarkGray,
                                textAlign = if (config.isCenterContent) TextAlign.Center else TextAlign.Start
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(
                                    if (config.isSelectHiltForLabel && value.isNullOrBlank()) {
                                        config.placeholder ?: R.string.empty
                                    } else {
                                        config.label ?: R.string.empty
                                    }
                                ),
                                fontWeight = FontWeight.Medium,
                                fontSize = size_15sp,
                                color = if (error != null) {
                                    MaterialTheme.colorScheme.error
                                } else if (value != null && ! config.isTextPrimaryColor) {
                                    MaterialTheme.colorScheme.primary
                                } else if (value != null) {
                                    TextPrimary
                                } else {
                                    TextSecondary
                                }
                            )
                        },
                        singleLine = true,
                        enabled = isEnabled,
                        isError = error != null,
                        interactionSource = interactionSource,
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 8.dp
                        ),
                        trailingIcon = {
                            if (config.icon != null) {
                                JPIcon(
                                    icon = config.icon,
                                    tint = if (isEnabled) {
                                        TextSecondary
                                    } else {
                                        Color.Gray.copy(alpha = 0.38f)
                                    },
                                    size = size_24dp
                                )
                            } else if (isEnabled) {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            }
                        },
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
                                shape = RoundedCornerShape(size_6dp),
                                focusedBorderThickness = size_1dp,
                                unfocusedBorderThickness = size_line,
                            )
                        }
                    )
                }
            )

            if (isEnabled) {
                Spacer(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Transparent)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                expanded = true
                            }
                        )
                )
            }
        }

        sheetContent(
            JPDropdownSheetState(
                visible = expanded,
                items = items,
                value = value,
                selectedItem = selectedItem,
                dismiss = { expanded = false },
                select = { item ->
                    onSelected(item)
                    expanded = false
                }
            )
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
    JPDropdown(
        items = listOf("Lựa chọn 1", "Lựa chọn 2", "Lựa chọn 3"),
        value = "Lựa chọn 1"
    )
}
