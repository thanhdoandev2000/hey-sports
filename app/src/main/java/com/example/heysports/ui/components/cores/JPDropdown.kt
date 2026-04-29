package com.example.heysports.ui.components.cores

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.FocusedBorderThickness
import androidx.compose.material3.OutlinedTextFieldDefaults.UnfocusedBorderThickness
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.models.app.StyleConfig
import com.example.heysports.ui.components.app.CustomLine
import com.example.heysports.ui.theme.LightGreenBackground
import com.example.heysports.ui.theme.size_6dp
import com.example.heysports.ui.theme.size_10dp
import com.example.heysports.ui.theme.size_14sp
import com.example.heysports.ui.theme.size_15sp
import com.example.heysports.ui.theme.size_4dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JPDropdown(
    modifier: Modifier = Modifier,
    value: String? = null,
    options: List<String> = emptyList(),
    isEnabled: Boolean = true,
    error: String? = null,
    config: StyleConfig = StyleConfig(),
    onValueChange: (String) -> Unit = {},
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier.fillMaxWidth()) {
        JPSpacer(height = config.mTop)

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {}
        ) {
            Box(
                modifier = Modifier
                    .menuAnchor(
                        type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                        enabled = isEnabled
                    )
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
                                    modifier = Modifier.fillMaxWidth(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.DarkGray,
                                    textAlign = if (config.isCenterContent) TextAlign.Center else TextAlign.Start
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(config.label ?: R.string.empty),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = size_15sp,
                                    color = if (error != null) MaterialTheme.colorScheme.error
                                    else if (value != null) MaterialTheme.colorScheme.primary
                                    else Color.DarkGray
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
                                if (isEnabled) ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
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
                                    focusedBorderThickness = FocusedBorderThickness,
                                    unfocusedBorderThickness = UnfocusedBorderThickness,
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
                                onClick = { expanded = ! expanded }
                            )
                    )
                }
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = Color.White,
                border = BorderStroke(1.dp, color = Color.Gray.copy(alpha = 0.5f)),
                modifier = Modifier.exposedDropdownSize(),
                shape = RoundedCornerShape(size_6dp)
            ) {
                options.forEachIndexed { index, string ->
                    val isSelected = string == value
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        if (index != 0) CustomLine()
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = string,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = size_14sp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                    maxLines = 2
                                )
                            },
                            onClick = {
                                onValueChange(string)
                                expanded = false
                            },
                            modifier = Modifier.background(
                                if (isSelected) LightGreenBackground else Color.Transparent
                            ),
                            trailingIcon = {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = {},
                                    modifier = Modifier.padding(0.dp),
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = MaterialTheme.colorScheme.primary,
                                        unselectedColor = Color.Gray
                                    )
                                )
                            },
                            contentPadding = PaddingValues(
                                top = size_4dp,
                                bottom = size_4dp,
                                end = size_4dp,
                                start = size_10dp
                            )
                        )
                    }
                }
            }
        }

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
        options = listOf("Lựa chọn 1", "Lựa chọn 2", "Lựa chọn 3"),
        value = "Lựa chọn 1"
    )
}