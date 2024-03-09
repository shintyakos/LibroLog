package com.example.liberolog.ui.appbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.liberolog.R
import com.example.liberolog.utils.NavigationItem

class AddBookAppBar : AppBar() {
    override val route: String
        get() = NavigationItem.ADD_BOOK.route

    @Composable
    override fun getTitle(): String {
        return stringResource(id = R.string.add_book_title)
    }

    override fun getNavigationIcon(navController: NavHostController?): @Composable () -> Unit {
        return {
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
            }
        }
    }

    override fun getActions(navController: NavHostController?):
        @Composable()
        (RowScope.() -> Unit) {
        return {
            Row {}
        }
    }
}
