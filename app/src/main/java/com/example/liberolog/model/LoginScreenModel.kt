package com.example.liberolog.model

data class LoginScreenModel(
    var email: String = "",
    var password: String = "",
    var passwordVisibility: Boolean = true,
)
