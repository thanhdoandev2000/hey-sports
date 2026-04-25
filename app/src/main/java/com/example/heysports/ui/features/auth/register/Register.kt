package com.example.heysports.ui.features.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.Diversity2
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.models.app.FieldState
import com.example.heysports.data.models.app.StyleConfig
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.cores.*
import com.example.heysports.ui.features.auth.components.DividerLabel
import com.example.heysports.ui.features.auth.components.LogoAuth
import com.example.heysports.ui.theme.*

@Composable
fun Register(
    viewModel: RegisterViewModel,
    onHome: () -> Unit,
    onLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            if (it is RegisterUiEffect.NavigateToHome) {
                onHome()
            }
        }
    }

    RegisterScreen(
        uiState = uiState,
        onRegister = viewModel::registerAccount,
        onUpdateField = viewModel::updateField,
        onLogin = onLogin,
        onHome = onHome
    )
}

@Composable
private fun RegisterScreen(
    uiState: RegisterUiState = RegisterUiState(),
    onRegister: () -> Unit = {},
    onUpdateField: (RegisterUiEffect, Boolean) -> Unit = { _, _ -> },
    onLogin: () -> Unit = {},
    onHome: () -> Unit = {}
) {
    var currentStep by remember { mutableIntStateOf(0) }

    fun onClickRegister() {
        if (currentStep == 0) {
            if (uiState.run { ! email.showError && ! password.showError && ! passwordConfirm.showError }) {
                currentStep += 1
            } else {
                onUpdateField(RegisterUiEffect.Email(uiState.email.value), true)
                onUpdateField(RegisterUiEffect.Password(uiState.password.value), true)
                onUpdateField(RegisterUiEffect.ConfirmPassword(uiState.passwordConfirm.value), true)
            }
        } else {
            onRegister()
        }
    }

    HeySportContainer(isLoading = uiState.isLoading, isEdgeToEdge = true) {
        Box(Modifier.fillMaxSize()) {
            LogoAuth()
            IconButton(
                onClick = { if (currentStep != 0) currentStep -- else onLogin() },
                modifier = Modifier.padding(top = size_40dp, start = paddingSmall)
            ) {
                JPIcon(
                    icon = Icons.Outlined.ArrowBackIosNew,
                    tint = Color.White,
                    modifier = Modifier.size(size_20dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.73f)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = size_24dp, topEnd = size_24dp))
                    .background(SurfaceWhite)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = paddingDefault)
                        .verticalScroll(rememberScrollState())
                        .imePadding()
                ) {
                    JPSpacer(height = size_32dp)
                    JPText(
                        text = stringResource(if (currentStep == 0) R.string.authCreateAccount else R.string.authPersonInfo),
                        fontWeight = FontWeight.Bold,
                        fontSize = size_20sp
                    )
                    JPText(
                        text = stringResource(if (currentStep == 0) R.string.authLoginInfo else R.string.authFinishCreateAccount),
                        color = TextSecondary,
                        fontSize = size_13sp
                    )
                    JPSpacer(height = paddingSmall)
                    if (currentStep == 0) {
                        AccountLogin(
                            email = uiState.email,
                            password = uiState.password,
                            passwordConfirm = uiState.passwordConfirm,
                            onUpdateField = onUpdateField,
                            onDone = { currentStep += 1 }
                        )
                    } else {
                        AccountInfo(
                            fullName = uiState.fullName,
                            phoneNumber = uiState.phoneNumber,
                            onUpdateField = onUpdateField,
                            onDone = onRegister
                        )
                    }

                    JPSpacer(height = paddingSmall)
                    JPButton(
                        label = if (currentStep == 0) R.string.next else R.string.authCreateAccount,
                        mTop = size_24dp,
                        onClick = { onClickRegister() }
                    )

                    DividerLabel()
                    JPTextButton(onClick = onHome, modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.weight(1f))
                        JPIcon(icon = Icons.Default.Diversity2, tint = TextSecondary)
                        JPSpacer(width = paddingSmall)
                        JPText(
                            text = stringResource(R.string.authWithoutRegister),
                            style = MaterialTheme.typography.titleMedium,
                            color = TextSecondary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        JPIcon(
                            icon = Icons.AutoMirrored.Outlined.ArrowForward,
                            tint = TextSecondary,
                            size = paddingDefault
                        )
                    }
                    JPText(
                        text = stringResource(R.string.authLimitFeatures),
                        fontSize = size_12sp,
                        color = TextSecondary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Row(
                        Modifier
                            .padding(paddingDefault)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        JPText(text = stringResource(R.string.authReadyAccount))
                        JPTextButton(label = R.string.authLogin, onClick = onLogin)
                    }
                    JPSpacer(height = paddingDefault)
                }
            }
        }
    }
}

@Composable
private fun AccountLogin(
    email: FieldState<String>,
    password: FieldState<String>,
    passwordConfirm: FieldState<String>,
    onUpdateField: (RegisterUiEffect, Boolean) -> Unit,
    onDone: () -> Unit
) {
    Column {
        JPInput(
            value = email.value,
            config = StyleConfig(
                label = R.string.authEmail,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            error = email.error?.let { stringResource(it) },
            onValueChange = { onUpdateField(RegisterUiEffect.Email(it), false) },
            onFocusLost = { onUpdateField(RegisterUiEffect.Email(email.value), true) }
        )
        Column {
            JPInput(
                value = password.value,
                config = StyleConfig(
                    label = R.string.authPassword,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                onValueChange = { onUpdateField(RegisterUiEffect.Password(it), false) },
                onFocusLost = { onUpdateField(RegisterUiEffect.Password(password.value), true) },
                error = password.error?.let { stringResource(it) },
            )
            PasswordStrength(password.value)
        }
        JPInput(
            value = passwordConfirm.value,
            config = StyleConfig(
                label = R.string.authConfirmPassword,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            onValueChange = { onUpdateField(RegisterUiEffect.ConfirmPassword(it), false) },
            onFocusLost = {
                onUpdateField(
                    RegisterUiEffect.ConfirmPassword(passwordConfirm.value),
                    true
                )
            },
            onDone = onDone,
            error = passwordConfirm.error?.let { stringResource(it) },
        )
    }
}

@Composable
private fun AccountInfo(
    fullName: FieldState<String> = FieldState(""),
    phoneNumber: FieldState<String> = FieldState(""),
    onUpdateField: (RegisterUiEffect, Boolean) -> Unit,
    onDone: () -> Unit
) {
    Column {
        JPInput(
            value = fullName.value,
            config = StyleConfig(
                label = R.string.authFullName,
                keyboardType = KeyboardType.Unspecified,
                imeAction = ImeAction.Next
            ),
            error = fullName.error?.let { stringResource(it) },
            onValueChange = { onUpdateField(RegisterUiEffect.UserName(it), false) },
            onFocusLost = { onUpdateField(RegisterUiEffect.UserName(fullName.value), true) }
        )
        JPInput(
            value = phoneNumber.value,
            config = StyleConfig(
                label = R.string.authPhone,
                placeholder = R.string.hintPhone,
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            error = phoneNumber.error?.let { stringResource(it) },
            onValueChange = { onUpdateField(RegisterUiEffect.PhoneNumber(it), false) },
            onFocusLost = { onUpdateField(RegisterUiEffect.PhoneNumber(phoneNumber.value), true) },
            onDone = onDone
        )
        JPSpacer(height = paddingDefault)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(size_10dp))
                .background(Color(0xFFE8F5E9))
                .padding(size_12dp),
            verticalAlignment = Alignment.Top
        ) {
            JPText(text = "ℹ️", fontSize = size_14sp)
            Spacer(Modifier.width(paddingSmall))
            JPText(
                text = stringResource(R.string.authAgreePolicy),
                fontSize = size_12sp,
                color = TextSecondary
            )
        }
        JPSpacer(height = size_12dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            JPText(
                text = stringResource(R.string.authTerms),
                fontSize = size_12sp,
                color = PrimaryGreen,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { })
            JPText(
                text = stringResource(R.string.authAnd),
                fontSize = size_12sp,
                color = TextSecondary,
                modifier = Modifier.padding(horizontal = size_2dp)
            )
            JPText(
                text = stringResource(R.string.authSecurity),
                fontSize = size_12sp,
                color = PrimaryGreen,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { })
        }
    }
}

@Composable
private fun PasswordStrength(password: String) {
    val strength = when {
        password.length >= 8 &&
                password.any { it.isDigit() } &&
                password.any { it.isUpperCase() } &&
                password.any { ! it.isLetterOrDigit() } -> 3

        password.length >= 8 &&
                (password.any { it.isDigit() } || password.any { it.isUpperCase() }) -> 2

        password.length >= 6 -> 1
        else -> 0
    }
    val label = listOf(
        R.string.empty,
        R.string.authPasswordWeak,
        R.string.authPasswordNormal,
        R.string.authPasswordStrong
    )[strength]
    val color = listOf(DividerColor, Color.Red, Color(0xFFF57C00), PrimaryGreen)[strength]

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
            Text(
                text = stringResource(label),
                fontSize = 11.sp,
                color = color,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
@Preview
@AppPreview
private fun RegisterPreview() {
    RegisterScreen()
}