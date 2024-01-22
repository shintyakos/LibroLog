package com.example.liberolog.model

data class HomeScreenModel(
    val monBookList: List<Book>,
    val recBookList: List<Book>
)

data class Book(
    val title: String,
    val author: String,
)
