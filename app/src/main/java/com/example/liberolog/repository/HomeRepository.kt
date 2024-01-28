package com.example.liberolog.repository

import com.example.liberolog.repository.data.BooksDao
import com.example.liberolog.repository.data.entity.BooksEntity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository
    @Inject
    constructor(private val booksDao: BooksDao) {
        fun getAll() = booksDao.getAll()

        fun getBookFromFirebase() {
            val db = Firebase.firestore

            db.collection("Books").get().addOnSuccessListener { result ->
                insertBook(
                    result.documents.map { snapshot ->
                        BooksEntity(
                            bookId = snapshot.id,
                            title = snapshot.data?.get("title") as String,
                            author = snapshot.data?.get("author") as String,
                            coverImageURL = snapshot.data?.get("coverImageURL") as String,
                            publicationDate = snapshot.data?.get("publicationDate") as Date,
                            totalPages = snapshot.data?.get("totalPages") as Int,
                        )
                    },
                )
            }
        }

        private fun insertBook(books: List<BooksEntity>) {
            booksDao.insertAll(books)
        }

        companion object {
            @Volatile private var instance: HomeRepository? = null

            fun getInstance(bookListDao: BooksDao) =
                instance ?: synchronized(this) {
                    instance ?: HomeRepository(bookListDao).also { instance = it }
                }
        }
    }
