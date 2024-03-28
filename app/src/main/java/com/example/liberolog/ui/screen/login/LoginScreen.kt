package com.example.liberolog.ui.screen.login

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.liberolog.R
import com.example.liberolog.ui.state.LoginState
import com.example.liberolog.utils.NavigationItem
import com.example.liberolog.viewmodel.LoginViewModel

/**
 * This is the main login screen of the application.
 * It contains a column layout with the main contents of the screen.
 *
 * @param padding The padding to be applied to the layout.
 * @param viewModel The ViewModel associated with this screen.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun LoginScreen(
    padding: PaddingValues,
    navigationTo: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current
    val loginState by viewModel.loginState.collectAsState()

    when (loginState) {
        is LoginState.LoadingState -> {
            Log.d("LoginScreen", "loginState: $loginState")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier,
                    strokeWidth = ProgressIndicatorDefaults.CircularStrokeWidth,
                )
            }
        }
        is LoginState.SuccessState -> {
            Log.d("LoginScreen", "login success")
            navigationTo(NavigationItem.HOME.route)
        }
        else -> {
            Log.d("LoginScreen", "loginState: $loginState")
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { focusManager.clearFocus() },
                        ),
            ) {
                MainContents(
                    email = viewModel.model.email,
                    onChangeEmail = viewModel::onEmailChange,
                    password = viewModel.model.password,
                    onChangePassword = viewModel::onPasswordChange,
                    login = viewModel::login,
                    navigationTo,
                    loginState is LoginState.ErrorState,
                )
            }
        }
    }
}

/**
 * This function contains the main contents of the login screen.
 * It includes the email and password fields, and the login button.
 *
 * @param viewModel The ViewModel associated with this screen.
 */
@Composable
fun MainContents(
    email: String,
    onChangeEmail: (String) -> Unit,
    password: String,
    onChangePassword: (String) -> Unit,
    login: () -> Unit,
    navigationTo: (String) -> Unit,
    isErrorFlag: Boolean,
) {
    var passwordVisibility by remember { mutableStateOf(true) }
    Column(
        modifier =
            Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp),
    ) {
        Column(modifier = Modifier.padding(bottom = 20.dp)) {
            Text(
                text = stringResource(R.string.login_email),
                style =
                    TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    ),
                color = if (isErrorFlag) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
            )
            BasicTextField(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = if (isErrorFlag) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(5.dp),
                        )
                        .focusRequester(FocusRequester()),
                value = email,
                onValueChange = { onChangeEmail(it) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                decorationBox = { innerTextField ->
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .padding(start = 10.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        innerTextField()
                    }
                },
            )
            if (isErrorFlag) {
                Row(modifier = Modifier.padding(top = 5.dp).height(20.dp)) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(14.dp).align(Alignment.CenterVertically),
                    )
                    Text(
                        text = stringResource(R.string.login_error_email_incorrect),
                        style =
                            TextStyle(
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.error,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            ),
                        modifier = Modifier.padding(start = 5.dp),
                    )
                }
            }
        }
        Column(modifier = Modifier.padding(bottom = 20.dp)) {
            Text(
                text = stringResource(R.string.login_password),
                style =
                    TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        color = if (isErrorFlag) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                    ),
                modifier = Modifier.padding(bottom = 10.dp),
            )
            BasicTextField(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = if (isErrorFlag) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(5.dp),
                        )
                        .focusRequester(FocusRequester()),
                value = password,
                onValueChange = { onChangePassword(it) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation =
                    if (passwordVisibility) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                decorationBox = { innerTextField ->
                    Box(
                        modifier =
                            Modifier
                                .height(30.dp)
                                .padding(start = 10.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        innerTextField()
                        IconButton(
                            onClick = { passwordVisibility = !passwordVisibility },
                            modifier = Modifier.align(Alignment.CenterEnd),
                        ) {
                            Icon(
                                imageVector =
                                    if (passwordVisibility) {
                                        Icons.Filled.VisibilityOff
                                    } else {
                                        Icons.Filled.Visibility
                                    },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                },
            )
            if (isErrorFlag) {
                Row(modifier = Modifier.padding(top = 5.dp).height(20.dp)) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(14.dp).align(Alignment.CenterVertically),
                    )
                    Text(
                        text = stringResource(R.string.login_error_password_incorrect),
                        style =
                            TextStyle(
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.error,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            ),
                        modifier = Modifier.padding(start = 5.dp),
                    )
                }
            }
        }
    }
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
    ) {
        Button(
            onClick = { login() },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
        ) {
            Text(text = stringResource(R.string.login_button))
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            TextButton(onClick = { navigationTo(NavigationItem.SIGNUP.route) }) {
                Text(
                    text = stringResource(id = R.string.login_register),
                    textAlign = TextAlign.Center,
                    style =
                        TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        ),
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(id = R.string.login_re_register_password),
                    textAlign = TextAlign.Center,
                    style =
                        TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        ),
                )
            }
        }
    }
}
