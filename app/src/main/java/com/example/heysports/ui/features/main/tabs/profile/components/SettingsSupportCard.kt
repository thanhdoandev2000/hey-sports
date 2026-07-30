package com.example.heysports.ui.features.main.tabs.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.heysports.R
import com.example.heysports.cores.extensions.optionalClickable
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.*

@Composable
internal fun SettingsSupportCard(onOpenSettings: (() -> Unit)?) {
    ProfileCard(
        modifier = Modifier.optionalClickable(onOpenSettings),
        contentPadding = PaddingValues(size_16dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(size_14dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = null,
                tint = PrimaryGreen,
                modifier = Modifier.size(size_28dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(size_3dp)
            ) {
                JPText(
                    text = stringResource(R.string.profile_settings_support),
                    color = TextPrimary,
                    fontSize = size_18sp,
                    fontWeight = FontWeight.Bold
                )
                JPText(
                    text = stringResource(R.string.profile_settings_description),
                    color = TextSecondary,
                    fontSize = size_13sp
                )
            }
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = TextPrimary,
                modifier = Modifier.size(size_24dp)
            )
        }
    }
}
