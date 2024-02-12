package com.example.liberolog.ui.appbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.liberolog.R
import com.example.liberolog.utils.NavigationItem

open class MainAppBar : Screen {
    override val route: String
        get() = ""

    @Composable
    override fun getTitle(): String {
        return ""
    }

    @Composable
    override fun TopBar() {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = getTitle(),
                    style =
                        TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 24.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontWeight = FontWeight(500),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            shadow =
                                Shadow(
                                    color = Color(0f, 0f, 0f, 0.25f),
                                    blurRadius = 0.0f,
                                    offset = Offset(4.0f, 4.0f),
                                ),
                        ),
                )
            },
            navigationIcon = {
                // Menuボタン
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(id = R.string.home_icons_menu),
                        tint = Color.White,
                    )
                }
            },
            actions = {
                // 設定アイコン
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = stringResource(id = R.string.home_icons_settings),
                        tint = Color.White,
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth(),
        )
    }

    @Composable
    override fun BottomBar(navController: NavHostController) {
        val currentRoute = this.route

        NavigationBar(containerColor = Color.White) {
            NavigationItem.entries.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route)
                        }
                    },
                    icon = { item.icon?.let { Icon(imageVector = it, contentDescription = item.route) } },
                    label = { Text(text = stringResource(id = item.label), maxLines = 1) },
                    colors =
                        NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = Color.White,
                        ),
                    alwaysShowLabel = true,
                )
            }
        }
    }
}
