package com.example.liberolog.model

data class HomeScreenModel(
    var monBookList: List<Book>? = null,
    val recBookList: List<Book>? = null,
)

class Book {
    var title: String = ""
    var author: String = ""
    var image: String = ""
        set(value) {
            field = value.replace("http://", "https://")
        }
}
