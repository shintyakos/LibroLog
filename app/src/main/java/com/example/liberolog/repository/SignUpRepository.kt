package com.example.liberolog.repository

import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.example.liberolog.repository.data.UserDao
import com.example.liberolog.repository.data.entity.UserEntity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class SignUpRepository
    @Inject
    constructor(private val userDao: UserDao) {
        private var email: String = ""

        suspend fun signUp(
            email: String,
            password: String,
        ): Boolean {
            return suspendCoroutine { continuation ->
                val option =
                    AuthSignUpOptions.builder().userAttribute(
                        AuthUserAttributeKey.email(),
                        email,
                    ).build()

                Amplify.Auth.signUp(
                    email,
                    password,
                    option,
                    { result ->
                        this.email = email
                        continuation.resume(result.isSignUpComplete)
                    },
                    { _ ->
                        continuation.resume(false)
                    },
                )
            }
        }

        suspend fun confirmSignUp(
            email: String,
            code: String,
        ): Boolean {
            return suspendCoroutine { continuation ->
                Amplify.Auth.confirmSignUp(
                    email,
                    code,
                    { result ->
                        continuation.resume(result.isSignUpComplete)
                    },
                    { _ ->
                        continuation.resume(false)
                    },
                )
            }
        }

        suspend fun addUser(user: Map<String, String>): Boolean =
            withContext(Dispatchers.IO) {
                return@withContext setUserToFirebase(user)
            }

        private suspend fun setUserToFirebase(user: Map<String, String>): Boolean {
            return suspendCoroutine { continuation ->
                val firebase = Firebase.firestore

                firebase.collection("Users").add(user)
                    .addOnSuccessListener {
                        continuation.resume(true)
                    }
                    .addOnFailureListener {
                        continuation.resume(false)
                    }
            }
        }

        suspend fun getUser(email: String): UserEntity? =
            withContext(Dispatchers.IO) {
                return@withContext getUserFromFirebase(email)
            }

        private suspend fun getUserFromFirebase(email: String): UserEntity? {
            return suspendCoroutine { continuation ->
                val firebase = Firebase.firestore

                firebase
                    .collection("Users")
                    .whereEqualTo("Email", email)
                    .get()
                    .addOnSuccessListener { result ->
                        result.documents.forEach { document ->
                            val user =
                                UserEntity(
                                    userId = document.id,
                                    userName = document.getString("UserName") ?: "",
                                    email = document.getString("Email") ?: "",
                                )
                            continuation.resume(user)
                        }
                    }
                    .addOnFailureListener {
                        continuation.resume(null)
                    }
            }
        }

        suspend fun setUser(user: UserEntity) =
            withContext(Dispatchers.IO) {
                userDao.insertUser(user)
            }
    }
