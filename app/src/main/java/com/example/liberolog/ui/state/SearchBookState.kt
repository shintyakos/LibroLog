package com.example.liberolog.ui.state

import androidx.compose.runtime.MutableState
import com.example.liberolog.model.Book

sealed class SearchBookState {
    data object StartState : SearchBookState()

    data object LoadState : SearchBookState()

    data class SuccessState(val searchedBookMap: Map<Book, MutableState<Boolean>>) : SearchBookState()

    data object ErrorState : SearchBookState()
}
