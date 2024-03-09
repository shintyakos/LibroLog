package com.example.liberolog.ui.screen.addbook

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.liberolog.viewmodel.HomeViewModel

@Composable
fun AddBookScreen(
    padding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    Column(modifier = Modifier.padding(padding)) {
    }
}
