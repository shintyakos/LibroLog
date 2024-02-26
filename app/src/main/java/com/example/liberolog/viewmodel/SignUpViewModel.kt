package com.example.liberolog.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liberolog.model.SignUpScreenModel
import com.example.liberolog.repository.SignUpRepository
import com.example.liberolog.ui.state.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
    @Inject
    internal constructor(
        private val signUpRepository: SignUpRepository,
    ) : ViewModel() {
        companion object {
            private val TAG = SignUpViewModel::class.java.name
        }

        var model by mutableStateOf(SignUpScreenModel())
            private set
        val state = MutableStateFlow<SignUpState>(SignUpState.StartState)

        fun getUserName(): String {
            return this.model.userName
        }

        fun onUserNameChange(userName: String) {
            model = model.copy(userName = userName)
        }

        fun getEmail(): String {
            return this.model.email
        }

        fun onEmailChange(email: String) {
            model = model.copy(email = email)
        }

        fun getPassword(): String {
            return this.model.password
        }

        fun onPasswordChange(password: String) {
            model = model.copy(password = password)
        }

        fun getConfirmPassword(): String {
            return this.model.confirmPassword
        }

        fun onConfirmPasswordChange(confirmPassword: String) {
            model = model.copy(confirmPassword = confirmPassword)
        }

        fun getCode(): String {
            return this.model.code
        }

        fun onCodeChange(code: String) {
            model = model.copy(code = code)
        }

        fun signUp() {
            viewModelScope.launch {
                val result = signUpRepository.signUp(model.email, model.password)
                if (result) {
                    state.value = SignUpState.SuccessFromSignUpState
                } else {
                    state.value = SignUpState.ErrorFromSignUpState
                }
            }
        }

        fun confirmSignUp() {
            viewModelScope.launch {
                val result = signUpRepository.confirmSignUp(model.email, model.code)
                if (result) {
                    val user =
                        hashMapOf(
                            "UserName" to model.userName,
                            "Email" to model.email,
                            "birthDate" to model.birthDay,
                        )

                    if (signUpRepository.addUser(user)) {
                        signUpRepository.getUser(model.email)?.let { userEntity ->
                            signUpRepository.setUser(userEntity)
                        }
                        state.value = SignUpState.SuccessFromConfirmSignUpState
                        return@launch
                    }
                }
                state.value = SignUpState.ErrorFromConfirmSignUpState
            }
        }
    }
