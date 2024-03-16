package com.example.liberolog.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.liberolog.model.AddBookScreenModel
import com.example.liberolog.model.AddBookTab
import com.example.liberolog.repository.AddBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
            when (model.selectedTabIndex) {
                AddBookTab.ISBN.ordinal -> {
                    Log.d(TAG, "searchIsbn: ${model.searchIsbn}")
                }

                AddBookTab.TITLE.ordinal -> {}
                else -> {}
            }
        }
    }
