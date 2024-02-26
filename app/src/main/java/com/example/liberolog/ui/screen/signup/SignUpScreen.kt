package com.example.liberolog.ui.screen.signup

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.liberolog.R
import com.example.liberolog.ui.state.SignUpState
import com.example.liberolog.utils.NavigationItem
import com.example.liberolog.viewmodel.SignUpViewModel

@Composable
fun SignUpScreen(
    padding: PaddingValues,
    navigationTo: (String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
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
            ConfirmSignUpContents(viewModel)
        }

        is SignUpState.SuccessFromConfirmSignUpState -> {
            navigationTo(NavigationItem.LOGIN.route)
        }

        is SignUpState.ErrorFromConfirmSignUpState -> {
            ConfirmSignUpContents(viewModel)
        }

        else -> {
            Column(modifier = Modifier.padding(padding)) {
                MainContents(viewModel)
            }
        }
    }
}

@Composable
fun MainContents(viewModel: SignUpViewModel) {
    var selectedDate = rememberDatePickerState()
    var selectedDateText by remember { mutableStateOf("日付を選択") }
    var state by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(20.dp)) {
        // ユーザー名
        SignUpTextView(
            text = stringResource(id = R.string.signup_username),
            value = viewModel.getUserName(),
            valueChange = { viewModel.onUserNameChange(it) },
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        // メールアドレス
        SignUpTextView(
            text = stringResource(id = R.string.signup_email),
            value = viewModel.getEmail(),
            valueChange = { viewModel.onEmailChange(it) },
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        // パスワード
        SignUpTextView(
            text = stringResource(id = R.string.signup_password),
            value = viewModel.getPassword(),
            valueChange = { viewModel.onPasswordChange(it) },
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        // パスワード確認
        SignUpTextView(
            text = stringResource(id = R.string.signup_password_confirm),
            value = viewModel.getConfirmPassword(),
            valueChange = { viewModel.onConfirmPasswordChange(it) },
            keyOption = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        Box {
            TextField(
                value = selectedDateText,
                onValueChange = { selectedDateText = selectedDate.toString() },
                readOnly = true,
            )
            Box(modifier = Modifier.matchParentSize().clickable { state = true })
        }

        if (state) {
            DatePickerDialog(
                onDismissRequest = { state = false },
                confirmButton = {
                    Button(onClick = {
                        state = false
                    }) {
                        Text("OK")
                    }
                },
            ) {
                DatePicker(selectedDate)
            }
        }
    }

    Box(modifier = Modifier.padding(20.dp)) {
        Button(
            onClick = {
                viewModel.signUp()
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
fun ConfirmSignUpContents(viewModel: SignUpViewModel) {
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
                text = stringResource(id = R.string.confirm_signup_code_description, viewModel.getEmail()),
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
                        color = androidx.compose.ui.graphics.Color.Black,
                    ),
            value = viewModel.getCode(),
            onValueChange = { viewModel.onCodeChange(it) },
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
            onClick = { viewModel.confirmSignUp() },
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
