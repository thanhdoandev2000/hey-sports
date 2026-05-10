package com.example.heysports.ui.components.cores

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.heysports.ui.theme.BgColorPage
import com.example.heysports.ui.theme.GreenDark
import com.example.heysports.ui.theme.TextPrimary
import com.example.heysports.ui.theme.TextSecondary
import com.example.heysports.ui.theme.size_10dp
import com.example.heysports.ui.theme.size_12dp
import com.example.heysports.ui.theme.size_12sp
import com.example.heysports.ui.theme.size_14sp
import com.example.heysports.ui.theme.size_18dp
import com.example.heysports.ui.theme.size_20dp
import com.example.heysports.ui.theme.size_4dp
import com.example.heysports.ui.theme.size_8dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JPCardDropdown(
    modifier: Modifier = Modifier,
    label: String,
    value: String? = null,
    options: List<String> = emptyList(),
    icon: ImageVector? = null,
    onValueChange: (String) -> Unit = {}
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier) {
        JPText(text = label, fontSize = size_12sp, color = TextSecondary)
        JPSpacer(height = size_4dp)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            Row(
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
                    .background(BgColorPage, RoundedCornerShape(size_8dp))
                    .border(0.5.dp, Color(0xFFDDDDDD), RoundedCornerShape(size_8dp))
                    .padding(horizontal = size_12dp, vertical = size_10dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                JPText(
                    text = value ?: "Chọn ${label.lowercase()}",
                    fontSize = size_14sp,
                    color = if (value != null) TextPrimary else TextSecondary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = icon ?: Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(size_20dp)
                )
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = Color.White,
                border = BorderStroke(0.5.dp, Color(0xFFDDDDDD)),
                shape = RoundedCornerShape(size_8dp)
            ) {
                options.forEachIndexed { index, option ->
                    val isSelected = option == value
                    if (index != 0) HorizontalDivider(color = Color(0xFFF0F0F0), thickness = 0.5.dp)
                    DropdownMenuItem(
                        text = {
                            JPText(
                                text = option,
                                fontSize = size_14sp,
                                color = if (isSelected) GreenDark else TextPrimary,
                                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
                            )
                        },
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        },
                        modifier = Modifier.background(
                            if (isSelected) GreenDark.copy(alpha = 0.08f) else Color.Transparent
                        ),
                        trailingIcon = {
                            if (isSelected) Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = GreenDark,
                                modifier = Modifier.size(size_18dp)
                            )
                        },
                        contentPadding = PaddingValues(horizontal = size_12dp, vertical = size_4dp)
                    )
                }
            }
        }
    }
}