package com.example.liberolog

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.liberolog.ui.screen.HomeScreen
import com.example.liberolog.utils.NavigationItem
import com.example.liberolog.viewmodel.HomeViewModel

/**
 * `LiberoLogApp` is a Composable function that sets up the navigation for the application.
 * It initializes a `NavController` and passes it to `LiberoLogAppNavyHost`.
 */
@Composable
fun LiberoLogApp() {
    val navController = rememberNavController()
    LiberoLogAppNavHost(navController)
}

/**
 * `LiberoLogAppNavyHost` is a Composable function that sets up the `NavHost` for the application.
 * It takes a `NavController` as a parameter and sets the start destination to "home".
 * It also defines a route for the `HomeScreen`.
 *
 * @param navController The `NavController` for navigating between composables.
 */
@Composable
fun LiberoLogAppNavHost(navController: NavHostController) {
    Scaffold(
        topBar = { HomeScreenTopBar() },
        bottomBar = { HomeScreenBottomBar(navController) },
    ) { padding ->
        NavHost(navController = navController, startDestination = NavigationItem.HOME.route) {
            composable(NavigationItem.HOME.route) {
                val viewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(padding, viewModel)
            }
        }
    }
}

@Composable
fun HomeScreenTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.home_title),
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
fun HomeScreenBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color.White) {
        NavigationItem.entries.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.route) },
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
