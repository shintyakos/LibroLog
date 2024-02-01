package com.example.liberolog.model

data class HomeScreenModel(
    var monBookList: List<Book>? = null,
    val recBookList: List<Book>? = null,
)

data class Book(
    val title: String,
    val author: String,
    val image: String,
)
