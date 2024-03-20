package com.example.liberolog.api.data

data class BookData(
    val bookId: String,
    val title: String,
    val author: List<String>,
    val coverImageURL: String,
    val publicationDate: String,
)
