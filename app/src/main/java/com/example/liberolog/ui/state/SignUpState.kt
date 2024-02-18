package com.example.liberolog.ui.state

import androidx.compose.runtime.State

sealed class SignUpState {
    data object StartState : SignUpState()
    data object LoadingState : SignUpState()
    data object SuccessState : SignUpState()
    data object ErrorState : SignUpState()
}