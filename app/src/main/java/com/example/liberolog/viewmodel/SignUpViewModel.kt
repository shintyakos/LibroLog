package com.example.liberolog.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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

        private val model by mutableStateOf(SignUpScreenModel())
        val state = MutableStateFlow<SignUpState>(SignUpState.StartState)

        fun getUserName(): String {
            return this.model.userName
        }

        fun onUserNameChange(userName: String) {
            this.model.userName = userName
        }

        fun getEmail(): String {
            return this.model.email
        }

        fun onEmailChange(email: String) {
            this.model.email = email
        }

        fun getPassword(): String {
            return this.model.password
        }

        fun onPasswordChange(password: String) {
            this.model.password = password
        }

        fun getConfirmPassword(): String {
            return this.model.confirmPassword
        }

        fun onConfirmPasswordChange(confirmPassword: String) {
            this.model.confirmPassword = confirmPassword
        }

        fun getCode(): String {
            return this.model.code
        }

        fun onCodeChange(code: String) {
            this.model.code = code
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
                            "userName" to model.userName,
                            "email" to model.email,
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
