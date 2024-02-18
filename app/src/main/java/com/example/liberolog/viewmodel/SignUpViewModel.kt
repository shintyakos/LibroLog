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
    internal constructor(val signUpRepository: SignUpRepository) : ViewModel() {
        private val model by mutableStateOf(SignUpScreenModel())
        val state = MutableStateFlow<SignUpState>(SignUpState.StartState)

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

        fun signUp() {
            viewModelScope.launch {
                signUpRepository.signUp(model)
            }
        }
    }
