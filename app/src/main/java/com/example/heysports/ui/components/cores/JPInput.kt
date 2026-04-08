package com.example.heysports.ui.components.cores

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.extensions.getValue
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.model.app.StyleConfig

@Composable
fun JPInput(
    modifier: Modifier = Modifier,
    value: String? = null,
    isEnabled: Boolean = true,
    error: String? = null,
    config: StyleConfig = StyleConfig(),
    onValueChange: (String) -> Unit = {},
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(Modifier.height(14.dp))
        OutlinedTextField(
            value = value.getValue(),
            modifier = Modifier
                .width(config.width)
                .fillMaxWidth()
                .height(config.height),
            label = {
                Text(
                    text = stringResource(config.label ?: R.string.empty),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            },
            onValueChange = onValueChange,
            placeholder = {
                val newLabel =
                    stringResource(config.label ?: R.string.empty).replace("*", "").trim()
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
            maxLines = config.maxLines,
            singleLine = config.maxLines == 1,
            enabled = isEnabled,
            isError = error != null,
            keyboardOptions = config.keyboardOptions.copy(keyboardType = config.keyboardType),
            keyboardActions = config.keyboardActions,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                disabledBorderColor = config.disableBgColor,
                errorBorderColor = config.errorColor,
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
    JPInput()
}