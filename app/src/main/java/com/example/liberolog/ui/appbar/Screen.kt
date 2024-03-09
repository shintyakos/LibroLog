package com.example.liberolog.ui.appbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface Screen {
    val route: String

    @Composable
    fun getTitle(): String

    @Composable
    fun TopBar(navController: NavHostController? = null) {}

    @Composable
    fun BottomBar(navController: NavHostController): Unit? = null

    @Composable
    fun FloatingButton(navController: NavHostController): Unit? = null
}
