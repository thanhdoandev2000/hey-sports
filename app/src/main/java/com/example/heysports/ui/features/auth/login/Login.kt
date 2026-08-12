package com.example.heysports.ui.features.auth.login

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Diversity2
import androidx.compose.material.icons.rounded.Diversity2
import androidx.compose.material.icons.rounded.SportsSoccer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.heysports.R
import com.example.heysports.cores.models.StyleConfig
import com.example.heysports.cores.utils.AppPreview
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
                    .fillMaxHeight(0.795f)
                    .align(Alignment.BottomCenter)
                    .clip(
                        RoundedCornerShape(
                            topStart = HeySportsRadius.Large,
                            topEnd = HeySportsRadius.Large
                        )
                    )
                    .background(SurfaceWhite)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = size_16dp)
                        .verticalScroll(rememberScrollState())
                        .imePadding()
                ) {
                    JPSpacer(height = size_18dp)
                    JPText(
                        text = stringResource(R.string.authLogin),
                        fontWeight = FontWeight.Bold,
                        fontSize = size_22sp
                    )
                    JPSpacer(height = size_2dp)
                    JPText(
                        text = stringResource(R.string.authWelcome),
                        color = TextSecondary,
                        fontSize = size_14sp
                    )
                    JPSpacer(height = size_6dp)
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

                    DividerLabel(label = R.string.authContinueWith)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(size_12dp)
                    ) {
                        JPOutlineButton(
                            onClick = onLoginGoogle,
                            mTop = size_0,
                            iconSize = size_24dp,
                            imgRes = R.drawable.ic_google,
                            label = R.string.authGoogle,
                            borderColor = DividerColor,
                            containerModifier = Modifier.weight(1f)
                        )
                        JPOutlineButton(
                            onClick = onLoginFacebook,
                            mTop = size_0,
                            iconSize = size_24dp,
                            imgRes = R.drawable.ic_facebook,
                            label = R.string.authFacebook,
                            borderColor = DividerColor,
                            containerModifier = Modifier.weight(1f)
                        )
                    }

                    JPSpacer(height = size_24dp)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(HeySportsRadius.Medium))
                            .background(LightGreenBackground) // Light green background
                            .clickable { withoutLogin() }
                            .padding(horizontal = size_16dp, vertical = size_10dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        JPIcon(
                            icon = Icons.Rounded.Diversity2,
                            tint = GreenDark,
                            size = size_24dp
                        )
                        JPSpacer(width = size_16dp)
                        Column(modifier = Modifier.weight(1f)) {
                            JPText(
                                text = stringResource(R.string.authExploreAppFirst),
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            JPText(
                                text = stringResource(R.string.authLimitFeatures),
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                        JPIcon(
                            icon = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            tint = GreenDark,
                            size = size_20dp
                        )
                    }

                    Row(
                        Modifier
                            .padding(top = size_24dp)
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