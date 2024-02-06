package com.example.liberolog.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liberolog.model.Book
import com.example.liberolog.model.HomeScreenModel
import com.example.liberolog.repository.HomeRepository
import com.example.liberolog.repository.data.entity.BooksEntity
import com.example.liberolog.ui.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * `HomeViewModel` is a ViewModel class that provides the state for the `HomeScreen`.
 * It uses Hilt for dependency injection and Coroutines for asynchronous operations.
 *
 * @property repository The `HomeRepository` that provides the data for the `HomeViewModel`.
 * @property homeState The state of the `HomeScreen` as a `MutableStateFlow`.
 */
@HiltViewModel
class HomeViewModel
    @Inject
    internal constructor(
        private val repository: HomeRepository,
    ) : ViewModel() {
        companion object {
            private const val TAG = "HomeViewModel"
        }

        val homeState = MutableStateFlow<HomeState>(HomeState.StartState)

        /**
         * `loadHomeModel` is a function that loads the data for the `HomeScreen`.
         * It first checks if there is any data in the repository. If not, it loads the data from the network and saves it in the repository.
         * Then it gets the data from the repository and emits a `HomeState.SuccessState` with the data.
         */
        fun loadHomeModel() {
            viewModelScope.launch {
                Log.d(TAG, "loadHomeModel.start")
                homeState.tryEmit(HomeState.LoadingState)
                if (repository.getAll().isEmpty()) {
                    val booksEntity: List<BooksEntity> = repository.load()
                    Log.d(TAG, "booksEntity: $booksEntity")
                    if (booksEntity.isNotEmpty()) {
                        Log.d(TAG, "insertBook.Start")
                        repository.insertBook(booksEntity)
                        Log.d(TAG, "insertBook.End")
                    }
                }

                val books = repository.getAll()
                Log.d(TAG, "books: $books")
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
                Log.d(TAG, "loadHomeModel.End")
            }
        }
    }
