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
        companion object {
            private const val TAG = "LoginRepository"

            @Volatile
            private var instance: LoginRepository? = null

            fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: LoginRepository().also { instance = it }
                }
        }

        suspend fun login(
            email: String,
            password: String,
        ): Boolean {
            Log.d(TAG, "login.start")
            return suspendCoroutine { continuation ->
                Amplify.Auth.signIn(
                    email,
                    password,
                    { result ->
                        Log.d(TAG, "result: ${result.isSignedIn}")
                        continuation.resume(result.isSignedIn)
                    },
                    { error ->
                        Log.e(TAG, "Error signing in: ${error.message}")
                        continuation.resume(false)
                    },
                )
            }
        }
    }
