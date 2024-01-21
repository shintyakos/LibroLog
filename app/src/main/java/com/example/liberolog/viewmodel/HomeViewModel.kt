package com.example.liberolog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.liberolog.model.HomeScreenModel
import com.example.liberolog.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val saveStateHandle: SavedStateHandle,
        private val repository: HomeRepository,
    ) : ViewModel() {
        val homeModel: MutableLiveData<HomeScreenModel> = MutableLiveData<HomeScreenModel>().also {
            loadHomeModel()
        }

        private fun loadHomeModel() {
            // TODO: implement
        }
    }
