package com.example.heysports.ui.features.auth.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.data.model.app.StyleConfig
import com.example.heysports.ui.base.HeySportContainer
import com.example.heysports.ui.components.app.CustomButton
import com.example.heysports.ui.components.app.CustomLine
import com.example.heysports.ui.components.cores.JPButton
import com.example.heysports.ui.components.cores.JPCheckBox
import com.example.heysports.ui.components.cores.JPInput
import com.example.heysports.ui.components.cores.JPSpacer
import com.example.heysports.ui.components.cores.JPText
import com.example.heysports.ui.components.cores.JPTextButton
import com.example.heysports.ui.theme.paddingDefault
import com.example.heysports.ui.theme.paddingSmall
import com.example.heysports.ui.theme.size_140dp
import com.example.heysports.ui.theme.size_24dp
import com.example.heysports.ui.theme.size_36sp
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
        onHome = onHome,
        onRegister = onRegister,
        onForgotPassword = onForgotPassword,
        onLoginGoogle = viewModel::loginGoogle,
        onLoginFacebook = viewModel::loginFacebook,
        onUpdateEmail = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onLogin = viewModel::login,
        onChecked = viewModel::updateChecked
    )
}

@Composable
private fun LoginScreen(
    uiState: LoginUiState,
    onHome: () -> Unit = {},
    onRegister: () -> Unit = {},
    onForgotPassword: () -> Unit,
    onLoginGoogle: () -> Unit,
    onLoginFacebook: () -> Unit,
    onUpdateEmail: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLogin: () -> Unit = {},
    onChecked: (Boolean) -> Unit = {}
) {
    HeySportContainer(isLoading = uiState.isLoading) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingDefault)
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                modifier = Modifier.size(size_140dp),
                contentDescription = "Logo ứng dụng"
            )
            JPSpacer(height = size_4dp)
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF111827),
                            fontWeight = FontWeight.ExtraBold,
                            fontStyle = FontStyle.Italic
                        )
                    ) { append("HEY ") }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.Black,
                            fontStyle = FontStyle.Italic
                        )
                    ) { append("SPORTS") }
                },
                fontSize = size_36sp,
                letterSpacing = (-1).sp
            )
            JPSpacer(height = size_24dp)
            JPInput(
                value = uiState.email,
                config = StyleConfig(
                    label = R.string.authEmail,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                error = uiState.emailError?.let { stringResource(it) },
                onValueChange = onUpdateEmail
            )
            JPInput(
                value = uiState.password,
                config = StyleConfig(
                    label = R.string.authPassword,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                onValueChange = onPasswordChange,
                onDone = onLogin
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = paddingDefault),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomLine(modifier = Modifier.weight(1f))
                JPSpacer(width = paddingDefault)
                JPText(text = stringResource(R.string.authOrLogin))
                JPSpacer(width = paddingDefault)
                CustomLine(modifier = Modifier.weight(1f))
            }

            CustomButton(
                onClick = onLoginGoogle,
                mTop = paddingSmall,
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
            Row(Modifier.padding(paddingDefault), verticalAlignment = Alignment.CenterVertically) {
                JPText(text = stringResource(R.string.authNotRegister))
                JPTextButton(label = R.string.authRegisterAccount, onClick = onRegister)
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
        onHome = {},
        onRegister = {},
        onForgotPassword = {},
        onLoginGoogle = {},
        onLoginFacebook = {}
    )
}