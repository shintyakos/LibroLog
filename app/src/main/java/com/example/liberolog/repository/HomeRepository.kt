package com.example.liberolog.repository

import com.example.liberolog.repository.data.BookListDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository
    @Inject
    constructor(private val bookListDao: BookListDao) {
        fun getAll() = bookListDao.getAll()

        companion object {
            @Volatile private var instance: HomeRepository? = null

            fun getInstance(bookListDao: BookListDao) =
                instance ?: synchronized(this) {
                    instance ?: HomeRepository(bookListDao).also { instance = it }
                }
        }
    }
