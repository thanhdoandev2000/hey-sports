package com.example.heysports.ui.features.auth.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heysports.R
import com.example.heysports.cores.extensions.drawFieldBackground
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.theme.*

@Composable
fun LogoAuth() {
    val infiniteTransition = rememberInfiniteTransition(label = "bg")

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.55f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ), label = "glow"
    )
    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .drawBehind { drawFieldBackground() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                GreenLight.copy(alpha = glowAlpha * 0.25f),
                                Color.Transparent
                            ),
                            center = Offset.Unspecified,
                            radius = 600f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = size_32dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(
                            Brush.radialGradient(
                                listOf(GreenMid, GreenDark)
                            ),
                            CircleShape
                        )
                        .border(3.dp, GreenLight.copy(0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        modifier = Modifier.size(size_120dp),
                        contentDescription = "Logo ứng dụng"
                    )
                }

                Spacer(Modifier.height(size_8dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontStyle = FontStyle.Italic
                            )
                        ) { append("HEY ") }
                        withStyle(
                            style = SpanStyle(
                                color = GreenLight,
                                fontWeight = FontWeight.Black,
                                fontStyle = FontStyle.Italic
                            )
                        ) { append("SPORTS") }
                    },
                    fontSize = size_28sp,
                    letterSpacing = (- 1).sp
                )
            }
        }
    }
}

@Composable
@AppPreview
@Preview
private fun LogoAuthPreview() {
    LogoAuth()
}