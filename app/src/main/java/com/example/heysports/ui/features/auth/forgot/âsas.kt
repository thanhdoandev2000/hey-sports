package com.example.heysports.ui.features.auth.forgot

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.ArrowBackIosNew
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
private val GreenDark = Color(0xFF1B5E20)
private val GreenPrimary = Color(0xFF2E7D32)
private val GreenMid = Color(0xFF388E3C)
private val GreenLight = Color(0xFF66BB6A)
private val TextPrimary = Color(0xFF0D1B0E)
private val TextSecondary = Color(0xFF546E57)
private val SurfaceWhite = Color(0xFFFAFBFA)
private val DividerColor = Color(0xFFCFD8CF)
private val ErrorColor = Color(0xFFD32F2F)

// ─── Step indicator ───────────────────────────────────────────────────────────
@Composable
private fun StepIndicator(currentStep: Int, totalSteps: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalSteps) { index ->
            val isActive = index == currentStep
            val isPast = index < currentStep
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .width(if (isActive) 28.dp else 14.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        when {
                            isActive -> GreenPrimary
                            isPast -> GreenLight
                            else -> DividerColor
                        }
                    )
            )
        }
    }
}

// ─── Field background ─────────────────────────────────────────────────────────
private fun DrawScope.drawFieldBackground() {
    drawRect(color = Color(0xFF1A3A1C))
    val stripeWidth = size.width / 10f
    for (i in 0..9) {
        val x = i * stripeWidth
        drawRect(
            color = if (i % 2 == 0) Color(0x0AFFFFFF) else Color.Transparent,
            topLeft = Offset(x, 0f),
            size = androidx.compose.ui.geometry.Size(stripeWidth, size.height)
        )
    }
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0x30A5D6A7), Color.Transparent),
            center = Offset(size.width / 2f, size.height * 0.5f),
            radius = size.width * 0.65f
        )
    )
}

// ─── Reusable input ───────────────────────────────────────────────────────────
@Composable
private fun FieldInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = placeholder, color = TextSecondary, fontSize = 15.sp)
            },
            singleLine = true,
            isError = errorMessage != null,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GreenPrimary,
                unfocusedBorderColor = DividerColor,
                errorBorderColor = ErrorColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color(0xFFFFF5F5),
                cursorColor = GreenPrimary,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword && !passwordVisible) KeyboardType.Password else keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = { onImeAction() },
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
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = ErrorColor,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}

// ─── Password strength indicator ─────────────────────────────────────────────
@Composable
private fun PasswordStrength(password: String) {
    val strength = when {
        password.length >= 8 &&
                password.any { it.isDigit() } &&
                password.any { it.isUpperCase() } &&
                password.any { !it.isLetterOrDigit() } -> 3

        password.length >= 8 &&
                (password.any { it.isDigit() } || password.any { it.isUpperCase() }) -> 2

        password.length >= 6 -> 1
        else -> 0
    }
    val label = listOf("", "Yếu", "Trung bình", "Mạnh")[strength]
    val color = listOf(DividerColor, ErrorColor, Color(0xFFF57C00), GreenPrimary)[strength]

    if (password.isNotEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(3.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(if (index < strength) color else DividerColor)
                )
            }
            Text(text = label, fontSize = 11.sp, color = color, fontWeight = FontWeight.Medium)
        }
    }
}

// ─── Step 1: Account info ─────────────────────────────────────────────────────
@Composable
private fun StepAccountInfo(
    email: String, onEmailChange: (String) -> Unit, emailError: String?,
    password: String, onPasswordChange: (String) -> Unit, passwordError: String?,
    confirmPassword: String, onConfirmChange: (String) -> Unit, confirmError: String?,
    onNext: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        FieldInput(
            value = email,
            onValueChange = onEmailChange,
            placeholder = "Email *",
            errorMessage = emailError,
            keyboardType = KeyboardType.Email
        )
        Column {
            FieldInput(
                value = password,
                onValueChange = onPasswordChange,
                placeholder = "Mật khẩu *",
                errorMessage = passwordError,
                isPassword = true
            )
            PasswordStrength(password)
        }
        FieldInput(
            value = confirmPassword,
            onValueChange = onConfirmChange,
            placeholder = "Xác nhận mật khẩu *",
            errorMessage = confirmError,
            isPassword = true,
            imeAction = ImeAction.Done,
            onImeAction = onNext
        )

        Spacer(Modifier.height(4.dp))

        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenPrimary, contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(4.dp)
        ) {
            Text("Tiếp theo", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// ─── Step 2: Personal info ────────────────────────────────────────────────────
@Composable
private fun StepPersonalInfo(
    fullName: String, onFullNameChange: (String) -> Unit, nameError: String?,
    phone: String, onPhoneChange: (String) -> Unit, phoneError: String?,
    onRegister: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        FieldInput(
            value = fullName,
            onValueChange = onFullNameChange,
            placeholder = "Họ và tên *",
            errorMessage = nameError,
            keyboardType = KeyboardType.Text
        )
        FieldInput(
            value = phone,
            onValueChange = onPhoneChange,
            placeholder = "Số điện thoại (tùy chọn)",
            errorMessage = phoneError,
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done,
            onImeAction = onRegister
        )

        // Terms
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFE8F5E9))
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text("ℹ️", fontSize = 14.sp)
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Bằng cách đăng ký, bạn đồng ý với ",
                fontSize = 12.sp,
                color = TextSecondary
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Điều khoản dịch vụ", fontSize = 12.sp, color = GreenPrimary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { })
            Text(" & ", fontSize = 12.sp, color = TextSecondary)
            Text(
                "Chính sách bảo mật", fontSize = 12.sp, color = GreenPrimary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { })
        }

        Spacer(Modifier.height(4.dp))

        Button(
            onClick = onRegister,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenPrimary, contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(4.dp)
        ) {
            Text("Tạo tài khoản", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// ─── Main Register Screen ─────────────────────────────────────────────────────
@Composable
fun RegisterScreen(
    onBack: () -> Unit = {},
    onRegister: (email: String, password: String, fullName: String, phone: String) -> Unit = { _, _, _, _ -> },
    onLogin: () -> Unit = {}
) {
    var currentStep by remember { mutableIntStateOf(0) }

    // Step 1 fields
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmError by remember { mutableStateOf<String?>(null) }

    // Step 2 fields
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }

    fun validateStep1(): Boolean {
        var valid = true
        emailError = if (email.isBlank() || !email.contains("@")) {
            valid = false; "Email không hợp lệ"
        } else null
        passwordError = if (password.length < 6) {
            valid = false; "Mật khẩu tối thiểu 6 ký tự"
        } else null
        confirmError = if (confirmPassword != password) {
            valid = false; "Mật khẩu không khớp"
        } else null
        return valid
    }

    fun validateStep2(): Boolean {
        var valid = true
        nameError = if (fullName.isBlank()) {
            valid = false; "Vui lòng nhập họ tên"
        } else null
        phoneError = if (phone.isNotBlank() && !phone.matches(Regex("^[0-9]{10,11}$"))) {
            valid = false; "Số điện thoại không hợp lệ"
        } else null
        return valid
    }

    val infiniteTransition = rememberInfiniteTransition(label = "bg")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.55f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ), label = "glow"
    )

    Box(modifier = Modifier.fillMaxSize()) {

        // ── Hero header ───────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.30f)
                .drawBehind { drawFieldBackground() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                GreenLight.copy(alpha = glowAlpha * 0.2f),
                                Color.Transparent
                            )
                        )
                    )
            )

            // Back button
            IconButton(
                onClick = { if (currentStep > 0) currentStep-- else onBack() },
                modifier = Modifier.padding(top = 48.dp, start = 8.dp)
            ) {
                Icon(
                    Icons.Outlined.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "HEY ", color = Color.White, fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp
                    )
                    Text(
                        "SPORTS", color = GreenLight, fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp
                    )
                }
                Spacer(Modifier.height(10.dp))
                StepIndicator(currentStep = currentStep, totalSteps = 2)
            }
        }

        // ── Card sheet ────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.76f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(SurfaceWhite)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                // Step title
                Text(
                    text = if (currentStep == 0) "Tạo tài khoản" else "Thông tin cá nhân",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = if (currentStep == 0) "Bước 1/2 · Thông tin đăng nhập"
                    else "Bước 2/2 · Hầu như xong rồi!",
                    fontSize = 14.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
                )

                if (currentStep == 0) {
                    StepAccountInfo(
                        email = email,
                        onEmailChange = { email = it; emailError = null },
                        emailError = emailError,
                        password = password,
                        onPasswordChange = { password = it; passwordError = null },
                        passwordError = passwordError,
                        confirmPassword = confirmPassword,
                        onConfirmChange = { confirmPassword = it; confirmError = null },
                        confirmError = confirmError,
                        onNext = { if (validateStep1()) currentStep = 1 }
                    )
                } else {
                    StepPersonalInfo(
                        fullName = fullName,
                        onFullNameChange = { fullName = it; nameError = null },
                        nameError = nameError,
                        phone = phone,
                        onPhoneChange = { phone = it; phoneError = null },
                        phoneError = phoneError,
                        onRegister = {
                            if (validateStep2()) onRegister(email, password, fullName, phone)
                        }
                    )
                }

                Spacer(Modifier.height(20.dp))

                // Login link
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Đã có tài khoản? ", fontSize = 14.sp, color = TextSecondary)
                    Text(
                        text = "Đăng nhập",
                        fontSize = 14.sp,
                        color = GreenPrimary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { onLogin() }
                    )
                }
            }
        }
    }
}

// ─── Preview ──────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen()
    }
}