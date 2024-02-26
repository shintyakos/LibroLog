package com.example.liberolog.ui.state

sealed class SignUpState {
    data object StartState : SignUpState()

    data object LoadingState : SignUpState()

    data object SuccessFromSignUpState : SignUpState()

    data object ErrorFromSignUpState : SignUpState()

    data object SuccessFromConfirmSignUpState : SignUpState()

    data object ErrorFromConfirmSignUpState : SignUpState()
}
