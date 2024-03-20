package com.example.liberolog.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liberolog.api.data.BookData
import com.example.liberolog.model.AddBookScreenModel
import com.example.liberolog.model.AddBookTab
import com.example.liberolog.model.Book
import com.example.liberolog.repository.AddBookRepository
import com.example.liberolog.ui.state.SearchBookState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel
    @Inject
    constructor(
        val repository: AddBookRepository,
    ) : ViewModel() {
        companion object {
            val TAG = AddBookViewModel::class.simpleName
        }

        var model: AddBookScreenModel by mutableStateOf(AddBookScreenModel())
            private set

        val searchBookState = MutableStateFlow<SearchBookState>(SearchBookState.StartState)

        fun onSelectedTabIndex(selectedTabIndex: Int) {
            model = model.copy(selectedTabIndex = selectedTabIndex)
        }

        fun onChangedISBN(searchIsbn: String) {
            model = model.copy(searchIsbn = searchIsbn)
        }

        fun onChangedBookName(searchBookName: String) {
            model = model.copy(searchBookName = searchBookName)
        }

        fun onClickedSearchButton() {
            viewModelScope.launch {
                var result: List<BookData> = emptyList()
                when (model.selectedTabIndex) {
                    AddBookTab.ISBN.ordinal -> {
                        searchBookState.value = SearchBookState.LoadState
                        try {
                            result = repository.searchBook(isbn = model.searchIsbn, title = null)
                        } catch (e: Exception) {
                            searchBookState.value = SearchBookState.ErrorState
                            return@launch
                        }
                    }

                    AddBookTab.TITLE.ordinal -> {
                        searchBookState.value = SearchBookState.LoadState
                        result = repository.searchBook(isbn = null, title = model.searchBookName)
                    }

                    else -> {}
                }

                if (result.isNotEmpty()) {
                    var bookMap: Map<Book, MutableState<Boolean>> = emptyMap()
                    result.forEach { books ->
                        val book =
                            Book().apply {
                                title = books.title
                                author = books.author.first()
                                image = books.coverImageURL
                            }
                        bookMap = bookMap.plus(book to mutableStateOf(false))
                    }
                    searchBookState.value = SearchBookState.SuccessState(bookMap)
                } else {
                    searchBookState.value = SearchBookState.ErrorState
                }
            }
        }
    }
