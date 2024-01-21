package com.example.liberolog.model

data class HomeScreenModel(
    val title: String,
    val author: String,
) {
    val bookTitle: String = title
    val bookAuthor: String = author
}
