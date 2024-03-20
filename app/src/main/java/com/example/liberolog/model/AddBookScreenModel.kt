package com.example.liberolog.model

import androidx.compose.runtime.MutableState

enum class AddBookTab(id: Int, name: String) {
    ISBN(0, "ISBN"),
    TITLE(1, "タイトル"),
}

data class AddBookScreenModel(
    val searchBookName: String = "",
    val searchIsbn: String = "",
    var selectedTabIndex: Int = AddBookTab.ISBN.ordinal,
    val searchedBookMap: Map<Book, MutableState<Boolean>> = emptyMap(),
)
