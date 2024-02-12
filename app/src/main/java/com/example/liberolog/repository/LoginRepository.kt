package com.example.liberolog.repository

import com.example.liberolog.repository.data.BooksDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor() {
    companion object {
        private const val TAG = "LoginRepository"

        @Volatile private var instance: LoginRepository? = null
    }
}