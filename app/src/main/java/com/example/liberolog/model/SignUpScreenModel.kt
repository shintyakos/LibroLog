package com.example.liberolog.model

data class SignUpScreenModel(
    var userName: String = "",
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var birthDay: String = "",
    var code: String = "",
)
