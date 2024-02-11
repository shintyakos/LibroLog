package com.example.liberolog.ui.appbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.liberolog.R
import com.example.liberolog.utils.NavigationItem

class HomeAppBar : MainAppBar() {
    override val route: String = NavigationItem.HOME.route

    @Composable
    override fun getTitle(): String {
        return stringResource(id = R.string.home_title)
    }
}
