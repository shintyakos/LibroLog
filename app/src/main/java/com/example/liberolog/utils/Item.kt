package com.example.liberolog.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.liberolog.R

enum class NavigationItem(
    val route: String,
    val icon: ImageVector? = null,
    val label: Int,
) {
    LOGIN(route = "login", label = R.string.login),
    SIGNUP(route = "signup", label = R.string.signup),
    HOME(route = "home", icon = Icons.Filled.Home, label = R.string.nav_home),
    ADD_BOOK(route = "addBook", label = R.string.nav_add_book),
    BOOK_LIST(route = "bookList", icon = Icons.Filled.List, label = R.string.nav_book_list),
    COMMUNITY(route = "community", icon = Icons.Filled.Person, label = R.string.nav_community),
}
