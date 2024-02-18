package com.example.liberolog.ui.screen.signup

import android.util.Log
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.liberolog.R
import com.example.liberolog.repository.SignUpRepository
import com.example.liberolog.ui.state.SignUpState
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
        is SignUpState.SuccessState -> {
        }
        else -> {
            Column(modifier = Modifier.padding(padding)) {
                MainContents(viewModel, navigationTo)
            }
        }
    }
}

@Composable
fun MainContents(
    viewModel: SignUpViewModel,
    navigationTo: (String) -> Unit,
) {
    Column(modifier = Modifier.padding(20.dp)) {
        Column(modifier = Modifier.padding(bottom = 20.dp)) {
            Text(
                text = stringResource(id = R.string.signup_email),
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
                value = viewModel.getEmail(),
                onValueChange = { viewModel.onEmailChange(it) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
        Column(modifier = Modifier.padding(bottom = 20.dp)) {
            Text(
                text = stringResource(id = R.string.signup_password),
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
                value = viewModel.getPassword(),
                onValueChange = { viewModel.onPasswordChange(it) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
        Column(modifier = Modifier.padding(bottom = 20.dp)) {
            Text(
                text = stringResource(id = R.string.signup_password_confirm),
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
                value = viewModel.getConfirmPassword(),
                onValueChange = { viewModel.onConfirmPasswordChange(it) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
        Box {
            Button(
                onClick = {
                    viewModel.signUp()
                    navigationTo("")
                },
                modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            ) {
                Text(text = stringResource(id = R.string.signup_button))
            }
        }
    }
}

@Preview(device = Devices.PIXEL_6, showBackground = true)
@Composable
fun SignUpScreenPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        MainContents(viewModel = SignUpViewModel(SignUpRepository()), {})
    }
}
