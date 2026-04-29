package com.example.heysports.ui.components.app

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.size_6dp
import com.example.heysports.ui.theme.size_12dp
import com.example.heysports.ui.theme.size_15sp
import com.example.heysports.ui.theme.size_1dp
import kotlinx.coroutines.delay

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    textSearch: String,
    @StringRes placeholder: Int? = null,
    onTextChange: (String) -> Unit,
    onSearchExecute: (text: String) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(textSearch) {
        if (textSearch.isNotBlank()) {
            delay(600L)
            onSearchExecute(textSearch)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                size_1dp,
                Color.Gray.copy(0.5f),
                RoundedCornerShape(size_6dp)
            )
            .padding(horizontal = size_12dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        JPIcon(
            icon = Icons.Outlined.Search,
            contentDescription = "Search Icon",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(modifier = Modifier.weight(1f)) {
            if (textSearch.isEmpty()) {
                JPText(
                    text = stringResource(placeholder ?: R.string.mapSearchHint),
                    style = TextStyle(color = Color.Gray, fontSize = size_15sp)
                )
            }

            BasicTextField(
                value = textSearch,
                onValueChange = onTextChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = size_15sp
                ),
                cursorBrush = SolidColor(Color.Black),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchExecute(textSearch)
                        focusManager.clearFocus()
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}