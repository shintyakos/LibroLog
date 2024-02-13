package com.example.liberolog.ui.state

sealed class LoginState {
    data object StartState : LoginState()
    data object LoadingState : LoginState()
    data object SuccessState : LoginState()
    data object ErrorState : LoginState()
}