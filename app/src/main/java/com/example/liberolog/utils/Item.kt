package com.example.liberolog.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.liberolog.R

enum class NavigationItem(
    val route: String,
    val icon: ImageVector,
    val label: Int,
) {
    HOME(route = "home", icon = Icons.Filled.Home, label = R.string.nav_home),
    BOOK_LIST(route = "bookList", icon = Icons.Filled.List, label = R.string.nav_book_list),
    COMMUNITY(route = "community", icon = Icons.Filled.Person, label = R.string.nav_community),
}
