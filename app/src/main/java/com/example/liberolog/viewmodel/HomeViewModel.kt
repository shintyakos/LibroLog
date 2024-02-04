package com.example.liberolog.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liberolog.model.Book
import com.example.liberolog.model.HomeScreenModel
import com.example.liberolog.repository.HomeRepository
import com.example.liberolog.ui.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    internal constructor(
        private val saveStateHandle: SavedStateHandle,
        private val repository: HomeRepository,
    ) : ViewModel() {
        val homeState = MutableStateFlow<HomeState>(HomeState.StartState)

        init {
            loadHomeModel()
        }

        private fun loadHomeModel() {
            viewModelScope.launch {
                Log.d("", "loadHomeModel.start")
                homeState.tryEmit(HomeState.LoadingState)
//                val booksEntity: List<BooksEntity> = repository.load()
//                Log.d("", "booksEntity: $booksEntity")
//                if (booksEntity.isNotEmpty()) {
//                    Log.d("", "insertBook.Start")
//                    repository.insertBook(booksEntity)
//                    Log.d("", "insertBook.End")
//                }

                val books = repository.getAll()
                Log.d("", "books: $books")
                homeState.tryEmit(
                    HomeState.SuccessState(
                        HomeScreenModel(
                            monBookList =
                                books.map { book ->
                                    Book(
                                        title = book.title,
                                        author = book.author,
                                        image = book.coverImageURL,
                                    )
                                },
                        ),
                    ),
                )
                Log.d("", "loadHomeModel.End")
            }
        }
    }
