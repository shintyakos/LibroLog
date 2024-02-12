package com.example.liberolog.ui.appbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface Screen {
    val route: String

    @Composable
    fun getTitle(): String

    @Composable
    fun TopBar() {}

    @Composable
    fun BottomBar(navController: NavHostController) {}
}