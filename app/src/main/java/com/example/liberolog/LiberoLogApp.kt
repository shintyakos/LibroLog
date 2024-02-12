package com.example.liberolog

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.liberolog.ui.appbar.HomeAppBar
import com.example.liberolog.ui.appbar.LoginAppBar
import com.example.liberolog.ui.screen.home.HomeScreen
import com.example.liberolog.ui.screen.login.LoginScreen
import com.example.liberolog.utils.NavigationItem

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
    val currentBackStack by navController.currentBackStackEntryAsState()
    val screens = listOf(LoginAppBar(), HomeAppBar())

    Scaffold(
        topBar = { screens.find { currentBackStack?.destination?.route == it.route }?.TopBar() ?: Unit },
        bottomBar = { screens.find { currentBackStack?.destination?.route == it.route }?.BottomBar(navController) ?: Unit },
    ) { padding ->
        NavHost(navController = navController, startDestination = NavigationItem.LOGIN.route) {
            composable(NavigationItem.LOGIN.route) {
                LoginScreen(padding)
            }
            composable(NavigationItem.HOME.route) {
                HomeScreen(padding)
            }
        }
    }
}
