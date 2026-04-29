package com.example.heysports.ui.features.auth.login

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.models.app.StyleConfig
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.cores.*
import com.example.heysports.ui.features.auth.components.DividerLabel
import com.example.heysports.ui.features.auth.components.LogoAuth
import com.example.heysports.ui.theme.*

@Composable
fun Login(
    viewModel: LoginViewModel,
    onRegister: () -> Unit,
    onForgotPassword: () -> Unit,
    onHome: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val activityFacebook = LocalActivity.current as? ComponentActivity

    LoginScreen(
        uiState = uiState,
        onRegister = onRegister,
        onForgotPassword = onForgotPassword,
        onLoginGoogle = { activity?.let(viewModel::loginGoogle) },
        onLoginFacebook = { activityFacebook?.let(viewModel::loginFacebook) },
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
                        .padding(horizontal = size_16dp)
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
                    JPSpacer(height = size_8dp)
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
                    JPOutlineButton(
                        onClick = onLoginGoogle,
                        mTop = size_4dp,
                        iconSize = size_32dp,
                        imgRes = R.drawable.ic_google,
                        label = R.string.authLoginWithGoogle
                    )
                    JPOutlineButton(
                        onClick = onLoginFacebook,
                        iconSize = size_32dp,
                        imgRes = R.drawable.ic_facebook,
                        label = R.string.authLoginWithFacebook
                    )
                    DividerLabel()
                    JPTextButton(onClick = withoutLogin, modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.weight(1f))
                        JPIcon(icon = Icons.Default.Diversity2, tint = TextSecondary)
                        JPSpacer(width = size_8dp)
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
                            size = size_16dp
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
                            .padding(size_16dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        JPText(text = stringResource(R.string.authNotRegister))
                        JPTextButton(label = R.string.authRegisterAccount, onClick = onRegister)
                    }
                    JPSpacer(height = size_16dp)
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