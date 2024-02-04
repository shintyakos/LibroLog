package com.example.liberolog.ui.state

import com.example.liberolog.model.HomeScreenModel

sealed class HomeState {
    data object StartState : HomeState()

    data object LoadingState : HomeState()

    data class SuccessState(val model: HomeScreenModel) : HomeState()
}
