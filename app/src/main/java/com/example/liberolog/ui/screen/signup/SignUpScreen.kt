package com.example.liberolog.ui.screen.signup

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.liberolog.R
import com.example.liberolog.ui.state.SignUpState
import com.example.liberolog.ui.theme.LiberoLogTheme
import com.example.liberolog.utils.NavigationItem
import com.example.liberolog.viewmodel.SignUpViewModel
import java.text.DateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SignUpScreen(
    padding: PaddingValues,
    navigationTo: (String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current
    val signUpState by viewModel.state.collectAsState()

    when (signUpState) {
        is SignUpState.LoadingState -> {
            Log.d("SignUpScreen", "signUpState: $signUpState")
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

        is SignUpState.SuccessFromSignUpState -> {
            ConfirmSignUpContents(
                viewModel.model.email,
                viewModel.model.code,
                viewModel::onCodeChange,
                viewModel::confirmSignUp,
            )
        }

        is SignUpState.SuccessFromConfirmSignUpState -> {
            navigationTo(NavigationItem.LOGIN.route)
        }

        is SignUpState.ErrorFromConfirmSignUpState -> {
            ConfirmSignUpContents(
                viewModel.model.email,
                viewModel.model.code,
                viewModel::onCodeChange,
                viewModel::confirmSignUp,
            )
        }

        else -> {
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
                    viewModel.model.userName,
                    viewModel::onUserNameChange,
                    viewModel.model.email,
                    viewModel::onEmailChange,
                    viewModel.model.password,
                    viewModel::onPasswordChange,
                    viewModel.model.confirmPassword,
                    viewModel::onConfirmPasswordChange,
                    viewModel::signUp,
                )
            }
        }
    }
}

@Composable
fun MainContents(
    userName: String,
    onChangeUserName: (String) -> Unit,
    email: String,
    onChangeEmail: (String) -> Unit,
    password: String,
    onChangePassword: (String) -> Unit,
    confirmPassword: String,
    onChangeConfirmPassword: (String) -> Unit,
    signUp: () -> Unit,
) {
    val selectedDate = rememberDatePickerState()
    var selectedDateText by remember { mutableStateOf("日付を選択") }
    var state by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(20.dp)) {
        // ユーザー名
        SignUpTextView(
            text = stringResource(id = R.string.signup_username),
            value = userName,
            valueChange = { onChangeUserName(it) },
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        // メールアドレス
        SignUpTextView(
            text = stringResource(id = R.string.signup_email),
            value = email,
            valueChange = { onChangeEmail(it) },
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        // パスワード
        SignUpTextView(
            text = stringResource(id = R.string.signup_password),
            value = password,
            valueChange = { onChangePassword(it) },
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        // パスワード確認
        SignUpTextView(
            text = stringResource(id = R.string.signup_password_confirm),
            value = confirmPassword,
            valueChange = { onChangeConfirmPassword(it) },
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        Box {
            TextField(
                value = selectedDateText,
                onValueChange = { selectedDateText = selectedDate.toString() },
                readOnly = true,
                textStyle =
                    TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
            )
            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .clickable { state = true }
            )
        }

        if (state) {
            val format = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.JAPAN)
            DatePickerDialog(
                onDismissRequest = { state = false },
                confirmButton = {
                    Button(
                        onClick = {
                            selectedDate.setSelection(selectedDate.selectedDateMillis)
                            selectedDateText = format.format(Date(selectedDate.selectedDateMillis!!))
                            state = false
                        },
                    ) {
                        Text("OK")
                    }
                },
            ) {
                DatePicker(state = selectedDate)
            }
        }
    }

    Box(modifier = Modifier.padding(20.dp)) {
        Button(
            onClick = {
                signUp()
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
        ) {
            Text(text = stringResource(id = R.string.signup_button))
        }
    }
}

@Composable
fun ConfirmSignUpContents(
    email: String,
    code: String,
    onChangeCode: (String) -> Unit,
    confirmSignUp: () -> Unit,
) {
    Column(modifier = Modifier.padding(20.dp)) {
        Column(modifier = Modifier.padding(bottom = 36.dp)) {
            Text(
                text = stringResource(id = R.string.confirm_signup_code),
                style =
                    TextStyle(
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(id = R.string.confirm_signup_code_description, email),
                style =
                    TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    ),
                textAlign = TextAlign.Center,
                maxLines = 2,
                minLines = 1,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        BasicTextField(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                    ),
            value = code,
            onValueChange = { onChangeCode(it) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = VisualTransformation.None,
            decorationBox = { innerTextField ->
                Box(
                    modifier =
                        Modifier
                            .height(28.dp)
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    innerTextField()
                }
            },
        )
        Button(
            onClick = { confirmSignUp() },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp),
        ) {
            Text(
                text = stringResource(id = R.string.confirm_signup_send_code),
                style =
                    TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    ),
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(id = R.string.confirm_signup_re_send_code),
                    style =
                        TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        ),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Composable
private fun SignUpTextView(
    text: String,
    value: String,
    valueChange: (String) -> Unit,
    keyOption: KeyboardOptions,
) {
    Column(modifier = Modifier.padding(bottom = 20.dp)) {
        Text(
            text = text,
            style =
                TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                ),
            modifier = Modifier.padding(bottom = 10.dp),
        )
        BasicTextField(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(5.dp),
                    )
                    .focusRequester(FocusRequester()),
            value = value,
            onValueChange = valueChange,
            singleLine = true,
            keyboardOptions = keyOption,
            visualTransformation = VisualTransformation.None,
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
    }
}

@Preview
@Composable
fun preView() {
    LiberoLogTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            MainContents(
                userName = "test",
                onChangeUserName = {},
                email = "",
                onChangeEmail = {},
                password = "",
                onChangePassword = {},
                confirmPassword = "",
                onChangeConfirmPassword = {},
                signUp = {},
            )
        }
    }
}
