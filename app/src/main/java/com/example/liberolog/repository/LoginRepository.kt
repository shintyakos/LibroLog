package com.example.liberolog.repository

import android.util.Log
import com.amplifyframework.core.Amplify
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class LoginRepository
    @Inject
    constructor() {
        private val tag = "LoginRepository"

        suspend fun login(
            email: String,
            password: String,
        ): Boolean {
            Log.d(tag, "login.start")
            return suspendCoroutine { continuation ->
                Amplify.Auth.signIn(
                    email,
                    password,
                    { result ->
                        Log.d(tag, "result: ${result.isSignedIn}")
                        continuation.resume(result.isSignedIn)
                    },
                    { error ->
                        Log.e(tag, "Error signing in: ${error.message}")
                        continuation.resume(false)
                    },
                )
            }
        }
    }
