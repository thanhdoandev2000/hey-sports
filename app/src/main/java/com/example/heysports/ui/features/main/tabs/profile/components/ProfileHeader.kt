package com.example.heysports.ui.features.main.tabs.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.heysports.R
import com.example.heysports.cores.extensions.optionalClickable
import com.example.heysports.domain.models.UserInfo
import com.example.heysports.ui.components.app.UserAvatar
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.main.tabs.profile.PlayerProfileSummary
import com.example.heysports.ui.theme.*

@Composable
internal fun ProfileHeader(
    user: UserInfo?,
    profile: PlayerProfileSummary,
    isVerified: Boolean,
    onOpenSettings: (() -> Unit)?
) {
    val memberSince = profile.memberSinceYear?.let {
        stringResource(R.string.profile_member_since, it)
    }
    val subtitle = listOfNotNull(profile.area, memberSince)
        .joinToString(separator = " • ")
        .ifBlank { stringResource(R.string.profile_member_label) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(235.dp)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(GreenDark, PrimaryGreen, GreenDark)
                )
            )
    ) {
        FootballFieldPattern()
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(top = size_12dp, end = size_16dp)
                .size(size_48dp)
                .border(size_1dp, Color.White.copy(alpha = 0.9f), RoundedCornerShape(size_10dp))
                .optionalClickable(onOpenSettings),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = onOpenSettings?.let {
                    stringResource(R.string.profile_settings_content_description)
                },
                tint = Color.White,
                modifier = Modifier.size(size_28dp)
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .fillMaxWidth()
                .padding(
                    start = size_16dp,
                    end = size_16dp,
                    top = size_24dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(size_16dp)
        ) {
            UserAvatar(
                name = user?.name,
                imageUrl = user?.avatar,
                size = size_80dp,
                borderWidth = size_3dp,
                borderColor = Color.White,
                backgroundColor = Color(0xFFDDEEDF),
                textColor = PrimaryGreen,
                contentDescription = stringResource(
                    R.string.profile_avatar_content_description,
                    user?.name
                        ?.takeIf(String::isNotBlank)
                        ?: stringResource(R.string.profile_default_name)
                )
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(size_4dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(size_6dp)
                ) {
                    JPText(
                        modifier = Modifier.weight(1f, fill = false),
                        text = user?.name
                            ?.takeIf(String::isNotBlank)
                            ?: stringResource(R.string.profile_default_name),
                        color = Color.White,
                        fontSize = size_22sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                    if (isVerified) {
                        Icon(
                            imageVector = Icons.Outlined.Verified,
                            contentDescription = stringResource(
                                R.string.profile_verified_content_description
                            ),
                            tint = Color.White,
                            modifier = Modifier.size(size_20dp)
                        )
                    }
                }
                JPText(
                    text = subtitle,
                    color = Color.White.copy(alpha = 0.88f),
                    fontSize = size_14sp,
                    maxLines = 1
                )
            }
        }
    }
}
