package com.example.liberolog.repository

import android.util.Log
import com.example.liberolog.repository.data.BooksDao
import com.example.liberolog.repository.data.entity.BooksEntity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class HomeRepository
    @Inject
    constructor(private val booksDao: BooksDao) {
        suspend fun getAll(): List<BooksEntity> {
            return withContext(Dispatchers.IO) { booksDao.getAll() }
        }

        suspend fun load(): List<BooksEntity> =
            withContext(Dispatchers.Default) {
                val books = getBookFromFirebase()
                return@withContext books
            }

        suspend fun getBookFromFirebase(): List<BooksEntity> {
            return suspendCoroutine { continuation ->
                Log.d("", "getBookFromFirebase.start")
                val db = Firebase.firestore
                val books = mutableListOf<BooksEntity>()

                db.collection("Books").get().addOnSuccessListener { result ->
                    result.documents.forEach { document ->
                        Log.d("", "${document.id} => ${document.data}")
                        val book =
                            BooksEntity(
                                bookId = document.id,
                                title = document.getString("Title") ?: "",
                                author = document.getString("Author") ?: "",
                                coverImageURL = document.getString("CoverImageURL") ?: "",
                                publicationDate =
                                    SimpleDateFormat("yyyy-MM-dd").format(
                                        document.getTimestamp(
                                            "publicationDate",
                                        )?.toDate() ?: Date(),
                                    ),
                                totalPages = document.getLong("TotalPages") ?: 0,
                            )
                        books.add(book)
                    }
                }.addOnCompleteListener {
                    Log.d("", "books: $books")
                    continuation.resume(books)
                }
            }
        }

        suspend fun insertBook(books: List<BooksEntity>) {
            return withContext(Dispatchers.IO) {
                booksDao.insertAll(books)
            }
        }

        companion object {
            @Volatile private var instance: HomeRepository? = null

            fun getInstance(bookListDao: BooksDao) =
                instance ?: synchronized(this) {
                    instance ?: HomeRepository(bookListDao).also { instance = it }
                }
        }
    }
