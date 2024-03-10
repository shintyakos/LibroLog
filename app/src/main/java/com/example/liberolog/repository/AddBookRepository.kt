package com.example.liberolog.repository

import com.example.liberolog.repository.data.BooksDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddBookRepository
    @Inject
    constructor(private val bookDao: BooksDao) {
        companion object {
            private const val TAG = "AddBookRepository"
        }
    }
