package com.example.liberolog.ui.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.liberolog.R
import com.example.liberolog.utils.NavigationItem

class SignUpAppBar() : AppBar() {
    override val route: String
        get() = NavigationItem.SIGNUP.route

    @Composable
    override fun getTitle(): String {
        return stringResource(id = R.string.signup_title)
    }

    override fun getNavigationIcon(): @Composable () -> Unit {
        return {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIosNew,
                    contentDescription = "Back button",
                )
            }
        }
    }
}
