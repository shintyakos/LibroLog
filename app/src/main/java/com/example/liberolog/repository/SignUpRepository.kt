package com.example.liberolog.repository

import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.example.liberolog.model.SignUpScreenModel
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class SignUpRepository
    @Inject
    constructor() {
        companion object {
            private const val TAG = "SignUpRepository"
            private var instance: SignUpRepository? = null

            fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: SignUpRepository().also { instance = it }
                }
        }

        suspend fun signUp(model: SignUpScreenModel): Boolean {
            return suspendCoroutine { continuation ->
                val option = AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), model.email).build()
                Amplify.Auth.signUp(
                    model.email,
                    model.password,
                    option,
                    { result ->
                        continuation.resume(result.isSignUpComplete)
                    },
                    { error ->
                        continuation.resume(false)
                    },
                )
            }
        }
    }
