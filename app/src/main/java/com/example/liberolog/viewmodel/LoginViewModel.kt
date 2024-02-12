package com.example.liberolog.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liberolog.model.LoginScreenModule
import com.example.liberolog.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject internal constructor(val repository: LoginRepository) : ViewModel() {
    companion object {
        val TAG = "LoginViewModel"
    }

    var model: LoginScreenModule by mutableStateOf(LoginScreenModule())
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
            Log.d(TAG, "userName: ${model.email}, password: ${model.password}")
        }
    }
}