package com.example.liberolog.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.liberolog.model.AddBookScreenModel
import com.example.liberolog.repository.AddBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel
    @Inject
    constructor(
        val repository: AddBookRepository,
    ) : ViewModel() {
        private var model: AddBookScreenModel by mutableStateOf(AddBookScreenModel())

        fun getSearchedBookName(): String {
            return model.searchBookName
        }

        fun onSearchBookNameChanged(searchBookName: String) {
            model = model.copy(searchBookName = searchBookName)
        }

        fun getSearchedIsbn(): String {
            return model.searchIsbn
        }

        fun onSearchIsbnChanged(searchIsbn: String) {
            model = model.copy(searchIsbn = searchIsbn)
        }

        fun getSelectedTabIndex(): Int {
            return model.selectedTabIndex
        }

        fun onSelectedTabIndex(selectedTabIndex: Int) {
            model = model.copy(selectedTabIndex = selectedTabIndex)
        }
    }
