package com.example.heysports.ui.features.main.tabs.home.accept

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.theme.GreenDark
import com.example.heysports.ui.theme.PrimaryGreen
import com.example.heysports.ui.theme.size_10dp
import com.example.heysports.ui.theme.size_10sp
import com.example.heysports.ui.theme.size_12dp
import com.example.heysports.ui.theme.size_13sp
import com.example.heysports.ui.theme.size_1dp
import com.example.heysports.ui.theme.size_20sp
import com.example.heysports.ui.theme.size_2dp
import com.example.heysports.ui.theme.size_30dp
import com.example.heysports.ui.theme.size_34dp
import com.example.heysports.ui.theme.size_4dp
import com.example.heysports.ui.theme.size_58dp
import com.example.heysports.ui.theme.size_6dp
import com.example.heysports.ui.theme.size_8dp

@Composable
fun MatchTicketCard(
    modifier: Modifier = Modifier,
    hostTeamName: String = "FC Tuyền Sơn",
    guestTeamName: String = "Đội của bạn",
    hostInitials: String = "FC",
    guestInitials: String = "?",
    time: String = "19:00",
    date: String = "T4, 07/05",
    location: String = "Sân Tuyền Sơn",
    statusLabel: String = "KÈO ĐANG MỞ"
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(TicketShape(cornerRadius = size_10dp, cutRadius = size_12dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        PrimaryGreen,
                        GreenDark
                    )
                )
            )
            .border(
                width = size_1dp,
                color = Color.White.copy(alpha = 0.25f),
                shape = TicketShape(cornerRadius = size_10dp, cutRadius = size_12dp)
            )
    ) {
        FootballFieldLines()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StatusPill(statusLabel)
            Spacer(Modifier.height(size_12dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamBlock(
                    title = hostTeamName,
                    centerText = hostInitials,
                    dashed = false,
                    modifier = Modifier.weight(1f)
                )

                VsBlock()

                TeamBlock(
                    title = guestTeamName,
                    centerText = guestInitials,
                    dashed = true,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(size_8dp))
            Text(
                text = time,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(size_4dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.78f),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(size_4dp))
                Text(
                    text = listOf(date, location).filter(String::isNotBlank).joinToString("  |  "),
                    color = Color.White.copy(alpha = 0.78f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
            }
        }
    }
}

class TicketShape(
    private val cornerRadius: Dp,
    private val cutRadius: Dp
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val corner = with(density) { cornerRadius.toPx() }
        val cut = with(density) { cutRadius.toPx() }

        val path = Path().apply {
            moveTo(corner, 0f)
            lineTo(size.width - corner, 0f)

            quadraticTo(size.width, 0f, size.width, corner)

            lineTo(size.width, size.height / 2 - cut)
            arcTo(
                rect = Rect(
                    left = size.width - cut,
                    top = size.height / 2 - cut,
                    right = size.width + cut,
                    bottom = size.height / 2 + cut
                ),
                startAngleDegrees = - 90f,
                sweepAngleDegrees = - 180f,
                forceMoveTo = false
            )

            lineTo(size.width, size.height - corner)
            quadraticTo(size.width, size.height, size.width - corner, size.height)

            lineTo(corner, size.height)
            quadraticTo(0f, size.height, 0f, size.height - corner)

            lineTo(0f, size.height / 2 + cut)
            arcTo(
                rect = Rect(
                    left = - cut,
                    top = size.height / 2 - cut,
                    right = cut,
                    bottom = size.height / 2 + cut
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = - 180f,
                forceMoveTo = false
            )

            lineTo(0f, corner)
            quadraticTo(0f, 0f, corner, 0f)
            close()
        }

        return Outline.Generic(path)
    }
}

@Composable
private fun StatusPill(label: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(size_6dp))
            .background(Color.White.copy(alpha = 0.08f))
            .border(
                size_1dp,
                Color.White.copy(alpha = 0.12f),
                RoundedCornerShape(size_6dp)
            )
            .padding(horizontal = size_10dp, vertical = size_4dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(size_8dp)
                .clip(CircleShape)
                .background(Color(0xFF8DF0A3))
        )
        Spacer(Modifier.width(size_6dp))
        JPText(
            text = label,
            color = Color(0xFFB9F6C8),
            fontSize = size_10sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TeamBlock(
    title: String,
    centerText: String,
    dashed: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DashedCircle(
            text = centerText,
            dashed = dashed
        )
        Spacer(Modifier.height(size_6dp))
        JPText(
            text = title,
            color = Color.White,
            fontSize = size_13sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
    }
}

@Composable
private fun VsBlock(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "VS",
            color = Color(0xFF0A5A2E),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DashedCircle(
    text: String,
    dashed: Boolean
) {
    Box(
        modifier = Modifier.size(size_58dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                color = Color.White.copy(alpha = 0.9f),
                radius = size.minDimension / 2 - size_2dp.toPx(),
                style = Stroke(
                    width = size_2dp.toPx(),
                    pathEffect = if (dashed) {
                        PathEffect.dashPathEffect(floatArrayOf(8f, 8f))
                    } else null
                )
            )
        }
        JPText(
            text = text,
            color = Color.White,
            fontSize = size_20sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun BoxScope.FootballFieldLines() {
    Canvas(
        modifier = Modifier
            .matchParentSize()
            .padding(size_8dp)
    ) {
        val lineColor = Color.White.copy(alpha = 0.08f)
        val stroke = Stroke(width = size_1dp.toPx())

        drawRect(
            color = lineColor,
            style = stroke
        )

        drawLine(
            color = lineColor,
            start = Offset(size.width / 2, 0f),
            end = Offset(size.width / 2, size.height),
            strokeWidth = size_1dp.toPx()
        )

        drawCircle(
            color = lineColor,
            radius = size_30dp.toPx(),
            center = Offset(size.width / 2, size.height / 2),
            style = stroke
        )

        drawRect(
            color = lineColor,
            topLeft = Offset(0f, size.height * 0.28f),
            size = Size(size_34dp.toPx(), size.height * 0.44f),
            style = stroke
        )

        drawRect(
            color = lineColor,
            topLeft = Offset(size.width - size_34dp.toPx(), size.height * 0.28f),
            size = Size(size_34dp.toPx(), size.height * 0.44f),
            style = stroke
        )
    }
}
