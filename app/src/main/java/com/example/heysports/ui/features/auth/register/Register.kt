package com.example.heysports.ui.features.auth.register
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

// ─── Brand Colors ─────────────────────────────────────────────────────────────
private val GreenDark   = Color(0xFF1B5E20)
private val GreenPrimary= Color(0xFF2E7D32)
private val GreenMid    = Color(0xFF388E3C)
private val GreenLight  = Color(0xFF66BB6A)
private val GreenGhost  = Color(0xFFE8F5E9)
private val TextPrimary = Color(0xFF0D1B0E)
private val TextSecondary = Color(0xFF546E57)
private val SurfaceWhite = Color(0xFFFAFBFA)
private val DividerColor = Color(0xFFCFD8CF)

// ─── Grass field background painter ──────────────────────────────────────────
private fun DrawScope.drawFieldBackground() {
    // Deep green base
    drawRect(color = Color(0xFF1A3A1C))

    // Subtle pitch stripes
    val stripeWidth = size.width / 10f
    for (i in 0..9) {
        val x = i * stripeWidth
        drawRect(
            color = if (i % 2 == 0) Color(0x0AFFFFFF) else Color.Transparent,
            topLeft = Offset(x, 0f),
            size = androidx.compose.ui.geometry.Size(stripeWidth, size.height)
        )
    }

    // Radial glow from centre
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0x30A5D6A7), Color.Transparent),
            center = Offset(size.width / 2f, size.height * 0.28f),
            radius = size.width * 0.65f
        )
    )
}

// ─── Reusable field-style text field ─────────────────────────────────────────
@Composable
private fun FieldInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = placeholder,
                color = TextSecondary,
                fontSize = 15.sp
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GreenPrimary,
            unfocusedBorderColor = DividerColor,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = GreenPrimary,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword && !passwordVisible) KeyboardType.Password else keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus(); onImeAction() },
            onNext = { onImeAction() }
        ),
        visualTransformation = if (isPassword && !passwordVisible)
            PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff,
                        contentDescription = null,
                        tint = TextSecondary
                    )
                }
            }
        } else null,
        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 15.sp)
    )
}

// ─── Social button ────────────────────────────────────────────────────────────
@Composable
private fun SocialButton(
    label: String,
    iconRes: Int? = null,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
        border = BorderStroke(1.dp, DividerColor),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Replace with actual icon:
            // if (iconRes != null) Image(painterResource(iconRes), null, Modifier.size(22.dp))
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .background(Color(0xFFEEEEEE), CircleShape)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = label,
                color = TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ─── Divider with label ───────────────────────────────────────────────────────
@Composable
private fun OrDivider(label: String = "Hoặc") {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = DividerColor)
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 14.dp),
            color = TextSecondary,
            fontSize = 13.sp
        )
        HorizontalDivider(modifier = Modifier.weight(1f), color = DividerColor)
    }
}

// ─── Main Screen ──────────────────────────────────────────────────────────────
@Composable
fun RegisterScreen(
    onLogin: (email: String, password: String) -> Unit = { _, _ -> },
    onGoogleLogin: () -> Unit = {},
    onFacebookLogin: () -> Unit = {},
    onGuest: () -> Unit = {},
    onForgotPassword: () -> Unit = {},
    onRegister: () -> Unit = {}
) {
    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "bg")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.55f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ), label = "glow"
    )

    Box(modifier = Modifier.fillMaxSize()) {

        // ── Hero header (field background) ────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.38f)
                .drawBehind { drawFieldBackground() }
        ) {
            // Animated glow overlay
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
                    .padding(top = 56.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Logo badge
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .background(
                            Brush.radialGradient(
                                listOf(GreenMid, GreenDark)
                            ),
                            CircleShape
                        )
                        .border(3.dp, GreenLight.copy(0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    // Replace with: Image(painterResource(R.drawable.ic_launcher_foreground), ...)
                    Text("⚽", fontSize = 36.sp)
                }

                Spacer(Modifier.height(14.dp))

                // Brand name
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "HEY ",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "SPORTS",
                        color = GreenLight,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        // ── Card sheet ────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.72f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(SurfaceWhite)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                // Title
                Text(
                    text = "Đăng nhập",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Chào mừng bạn trở lại!",
                    fontSize = 14.sp,
                    color = TextSecondary,
                    modifier = Modifier.offset(y = (-8).dp)
                )

                Spacer(Modifier.height(2.dp))

                // Email field
                FieldInput(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Email *",
                    keyboardType = KeyboardType.Email
                )

                // Password field
                FieldInput(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Mật khẩu *",
                    isPassword = true,
                    imeAction = ImeAction.Done,
                    onImeAction = { onLogin(email, password) }
                )

                // Remember me + Forgot password
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = GreenPrimary,
                                uncheckedColor = DividerColor
                            ),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Nhớ đăng nhập", fontSize = 13.sp, color = TextSecondary)
                    }
                    TextButton(onClick = onForgotPassword, contentPadding = PaddingValues(0.dp)) {
                        Text("Quên mật khẩu?", fontSize = 13.sp, color = GreenPrimary)
                    }
                }

                Spacer(Modifier.height(4.dp))

                // Primary CTA
                Button(
                    onClick = { onLogin(email, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(4.dp)
                ) {
                    Text(
                        text = "Đăng Nhập",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                OrDivider()

                // Social logins
                SocialButton(label = "Tiếp tục với Google", onClick = onGoogleLogin)
                SocialButton(label = "Tiếp tục với Facebook", onClick = onFacebookLogin)

                OrDivider()

                // Guest mode — clearly secondary
                TextButton(
                    onClick = onGuest,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Tiếp tục không cần đăng nhập  →",
                        fontSize = 14.sp,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
                Text(
                    text = "Một số tính năng sẽ bị giới hạn",
                    fontSize = 12.sp,
                    color = DividerColor,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(8.dp))

                // Register link
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Chưa có tài khoản? ", fontSize = 14.sp, color = TextSecondary)
                    Text(
                        text = "Đăng Ký ngay!",
                        fontSize = 14.sp,
                        color = GreenPrimary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { onRegister() }
                    )
                }
            }
        }
    }
}

// ─── Preview ──────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        RegisterScreen()
    }
}
