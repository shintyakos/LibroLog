package com.example.liberolog.ui.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.liberolog.R
import com.example.liberolog.utils.NavigationItem

class LoginAppBar : AppBar() {
    override val route: String
        get() = NavigationItem.LOGIN.route

    override fun getNavigationIcon(navController: NavHostController?): @Composable () -> Unit {
        return {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIosNew,
                    contentDescription = "Back button",
                )
            }
        }
    }

    @Composable
    override fun getTitle(): String {
        return stringResource(id = R.string.login_title)
    }
}
