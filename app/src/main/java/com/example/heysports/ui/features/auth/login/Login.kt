package com.example.heysports.ui.features.auth.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.Diversity2
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.model.app.StyleConfig
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.app.CustomButton
import com.example.heysports.ui.components.cores.JPButton
import com.example.heysports.ui.components.cores.JPCheckBox
import com.example.heysports.ui.components.cores.JPIcon
import com.example.heysports.ui.components.cores.JPInput
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.components.cores.JPTextButton
import com.example.heysports.ui.features.auth.components.DividerLabel
import com.example.heysports.ui.features.auth.components.LogoAuth
import com.example.heysports.ui.theme.SurfaceWhite
import com.example.heysports.ui.theme.TextSecondary
import com.example.heysports.ui.theme.paddingDefault
import com.example.heysports.ui.theme.paddingSmall
import com.example.heysports.ui.theme.size_12sp
import com.example.heysports.ui.theme.size_13sp
import com.example.heysports.ui.theme.size_20dp
import com.example.heysports.ui.theme.size_20sp
import com.example.heysports.ui.theme.size_24dp
import com.example.heysports.ui.theme.size_4dp
import com.example.heysports.ui.theme.size_line

@Composable
fun Login(
    viewModel: LoginViewModel,
    onRegister: () -> Unit,
    onForgotPassword: () -> Unit,
    onHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        uiState = uiState,
        onRegister = onRegister,
        onForgotPassword = onForgotPassword,
        onLoginGoogle = viewModel::loginGoogle,
        onLoginFacebook = viewModel::loginFacebook,
        onUpdateEmail = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onLogin = viewModel::login,
        onChecked = viewModel::updateChecked
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is LoginUiEffect.NavigateToHome -> onHome()
            }
        }
    }
}

@Composable
private fun LoginScreen(
    uiState: LoginUiState,
    onRegister: () -> Unit = {},
    onForgotPassword: () -> Unit,
    onLoginGoogle: () -> Unit,
    onLoginFacebook: () -> Unit,
    onUpdateEmail: (String, Boolean) -> Unit = { _, _ -> },
    onPasswordChange: (String) -> Unit = {},
    onLogin: () -> Unit = {},
    onChecked: (Boolean) -> Unit = {},
    withoutLogin: () -> Unit = {}
) {
    HeySportContainer(isLoading = uiState.isLoading, isEdgeToEdge = true) {
        Box(Modifier.fillMaxSize()) {
            LogoAuth()
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
                    JPSpacer(height = size_20dp)
                    JPText(
                        text = stringResource(R.string.authLogin),
                        fontWeight = FontWeight.Bold,
                        fontSize = size_20sp
                    )
                    JPText(
                        text = stringResource(R.string.authWelcome),
                        color = TextSecondary,
                        fontSize = size_13sp
                    )
                    JPInput(
                        value = uiState.email.value,
                        config = StyleConfig(
                            label = R.string.authEmail,
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        error = uiState.email.error?.let { stringResource(it) },
                        onValueChange = { onUpdateEmail(it, false) },
                        onFocusLost = { onUpdateEmail(uiState.email.value, true) }
                    )
                    JPInput(
                        value = uiState.password.value,
                        config = StyleConfig(
                            label = R.string.authPassword,
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = onPasswordChange,
                        onDone = onLogin,
                        error = uiState.password.error?.let { stringResource(it) },
                    )
                    JPSpacer(height = paddingSmall)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        JPCheckBox(
                            text = stringResource(id = R.string.authRememberMe),
                            checked = uiState.checked,
                            onCheckedChange = onChecked
                        )
                        JPTextButton(label = R.string.forgotPassword, onClick = onForgotPassword)
                    }
                    JPButton(label = R.string.authLogin, mTop = size_24dp, onClick = onLogin)
                    DividerLabel()
                    CustomButton(
                        onClick = onLoginGoogle,
                        mTop = size_4dp,
                        imgIcon = R.drawable.ic_google,
                        label = R.string.authLoginWithGoogle,
                        bgColor = Color.Transparent,
                        border = BorderStroke(size_line, color = Color.Gray)
                    )

                    CustomButton(
                        onClick = onLoginFacebook,
                        bgColor = Color.Transparent,
                        border = BorderStroke(size_line, color = Color.Gray),
                        imgIcon = R.drawable.ic_facebook,
                        label = R.string.authLoginWithFacebook
                    )
                    DividerLabel()
                    JPTextButton(onClick = withoutLogin, modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.weight(1f))
                        JPIcon(icon = Icons.Default.Diversity2, tint = TextSecondary)
                        JPSpacer(width = paddingSmall)
                        JPText(
                            text = stringResource(R.string.authWithoutLogin),
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
                        JPText(text = stringResource(R.string.authNotRegister))
                        JPTextButton(label = R.string.authRegisterAccount, onClick = onRegister)
                    }
                    JPSpacer(height = paddingDefault)
                }
            }
        }
    }
}

@Composable
@AppPreview
@Preview
private fun LoginPreview() {
    LoginScreen(
        uiState = LoginUiState(),
        onRegister = {},
        onForgotPassword = {},
        onLoginGoogle = {},
        onLoginFacebook = {}
    )
}