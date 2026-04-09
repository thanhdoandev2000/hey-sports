package com.example.heysports.ui.features.main.tabs.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.heysports.ui.components.app.UserAvatar
import com.example.heysports.ui.components.cores.JPCard
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.features.main.tabs.home.NewsFeed
import com.example.heysports.ui.theme.paddingSmall
import com.example.heysports.ui.theme.size_12sp
import com.example.heysports.ui.theme.size_13sp
import com.example.heysports.ui.theme.size_15sp

@Composable
fun NewsFeed(news: NewsFeed) {
    JPCard(containerColor = Color.White, contentColor = Color.Black) {
        Row(horizontalArrangement = Arrangement.spacedBy(paddingSmall)) {
            Column { UserAvatar("${news.user.firstName} ${news.user.lastName}") }
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    JPText(
                        text = "${news.user.firstName} ${news.user.lastName}",
                        fontSize = size_15sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(paddingSmall),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FireBadge(news.like)
                        CommentBadge(news.comment)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    JPText(
                        text = news.time,
                        modifier = Modifier.weight(1f),
                        color = Color.Gray,
                        maxLines = 1,
                        fontSize = size_13sp
                    )
                    JPText(
                        text = "\uD83D\uDD25", modifier = Modifier.weight(0.3f),
                        fontSize = size_13sp
                    )
                    JPText(
                        text = news.status,
                        modifier = Modifier.weight(1.7f),
                        maxLines = 1,
                        fontSize = size_13sp
                    )
                }
                JPText(
                    text = news.content,
                    color = Color.DarkGray,
                    fontSize = size_12sp
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    if (news.image != null) {
                        AsyncImage(
                            model = news.image,
                            contentDescription = "Avatar of",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.Image,
                            contentDescription = "Post Image Placeholder",
                            modifier = Modifier.size(48.dp),
                            tint = Color(0xFF9E9E9E)
                        )
                    }
                }
            }
        }
    }
}