package com.example.liberolog.repository

import android.util.Log
import com.example.liberolog.api.GoogleBookSearchService
import com.example.liberolog.api.data.BookData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.suspendCoroutine

@Singleton
class AddBookRepository
    @Inject
    constructor(private val googleBookSearchService: GoogleBookSearchService) {
        companion object {
            private const val TAG = "AddBookRepository"
        }

        suspend fun searchBookByIsbn(isbn: String) =
            withContext(Dispatchers.IO) {
                return@withContext searchBookApiByISBN(isbn = isbn)
            }

        suspend fun searchBookByTitle(title: String) =
            withContext(Dispatchers.IO) {
                return@withContext searchBookApiByISBN(title = title)
            }

        private suspend fun searchBookApiByISBN(
            isbn: String? = null,
            title: String? = null,
        ): List<BookData> {
            return suspendCoroutine { continuation ->
                val result =
                    googleBookSearchService.getSearchBooks(isbn = isbn, title = title).execute()
                Log.d(TAG, "result: ${result.body()}")
                if (result.isSuccessful) {
                    continuation.resumeWith(
                        Result.success(
                            result.body()?.items?.map {
                                BookData(
                                    bookId = it.id ?: "",
                                    title = it.volumeInfo?.title ?: "",
                                    author = it.volumeInfo?.authors ?: emptyList(),
                                    coverImageURL = it.volumeInfo?.imageLinks?.smallThumbnail ?: "",
                                    publicationDate = it.volumeInfo?.publishedDate ?: "",
                                )
                            } ?: emptyList(),
                        ),
                    )
                } else {
                    continuation.resumeWith(Result.failure(Exception("Failed to fetch data")))
                }
            }
        }
    }
