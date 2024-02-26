package com.example.liberolog.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liberolog.model.LoginScreenModel
import com.example.liberolog.repository.LoginRepository
import com.example.liberolog.ui.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    internal constructor(val repository: LoginRepository) : ViewModel() {
        companion object {
            private val TAG = LoginViewModel::class.java.name
        }

        val loginState = MutableStateFlow<LoginState>(LoginState.StartState)

        var model: LoginScreenModel by mutableStateOf(LoginScreenModel())
            private set

        fun onEmailChange(email: String) {
            model = model.copy(email = email)
        }

        fun onPasswordChange(password: String) {
            model = model.copy(password = password)
        }

        fun onPasswordVisibilityChange() {
            model = model.copy(passwordVisibility = !model.passwordVisibility)
        }

        fun login() {
            viewModelScope.launch {
                Log.d(TAG, "login start")
                Log.d(TAG, "userName: ${model.email}, password: ${model.password}")
                if (model.email.isEmpty() || model.password.isEmpty()) {
                    loginState.tryEmit(LoginState.ErrorState)
                    return@launch
                }

                loginState.tryEmit(LoginState.LoadingState)

                val result = repository.login(model.email, model.password)
                Log.d(TAG, "result: $result")
                if (result) {
                    loginState.tryEmit(LoginState.SuccessState)
                } else {
                    loginState.tryEmit(LoginState.ErrorState)
                }
                Log.d(TAG, "login end")
            }
        }
    }
