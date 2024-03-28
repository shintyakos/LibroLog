package com.example.liberolog

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.liberolog.ui.appbar.AddBookAppBar
import com.example.liberolog.ui.appbar.HomeAppBar
import com.example.liberolog.ui.appbar.LoginAppBar
import com.example.liberolog.ui.appbar.SignUpAppBar
import com.example.liberolog.ui.screen.addbook.AddBookScreen
import com.example.liberolog.ui.screen.home.HomeScreen
import com.example.liberolog.ui.screen.login.LoginScreen
import com.example.liberolog.ui.screen.signup.SignUpScreen
import com.example.liberolog.utils.NavigationItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private var paddingValues: PaddingValues? = null

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
    val screens =
        listOf(
            LoginAppBar(),
            SignUpAppBar(),
            HomeAppBar(),
            AddBookAppBar(),
        )

    Scaffold(
        topBar = {
            screens.find { currentBackStack?.destination?.route == it.route }?.TopBar(navController) ?: Unit
        },
        bottomBar = {
            screens.find { currentBackStack?.destination?.route == it.route }?.BottomBar(navController) ?: Unit
        },
        floatingActionButton = {
            screens.find { currentBackStack?.destination?.route == it.route }
                ?.FloatingButton(navController) ?: Unit
        },
    ) { padding ->
        paddingValues = padding
        NavHost(navController = navController, startDestination = NavigationItem.LOGIN.route) {
            composable(
                route = NavigationItem.LOGIN.route,
            ) {
                LoginScreen(padding, navigationTo(navController))
            }
            composable(
                route = NavigationItem.SIGNUP.route,
            ) {
                SignUpScreen(padding, navigationTo(navController))
            }
            composable(
                route = NavigationItem.HOME.route,
            ) {
                HomeScreen(padding, navigationTo(navController))
            }
            composable(
                route = NavigationItem.ADD_BOOK.route,
            ) {
                AddBookScreen(padding)
            }
        }
    }
}

fun navigationTo(navController: NavHostController): (String) -> Unit =
    { navScreen ->
        CoroutineScope(Dispatchers.Main).launch {
            when (navScreen) {
                NavigationItem.LOGIN.route -> {
                    navController.navigate(NavigationItem.LOGIN.route)
                }
                NavigationItem.SIGNUP.route -> {
                    navController.navigate(NavigationItem.SIGNUP.route)
                }
                NavigationItem.HOME.route -> {
                    navController.navigate(NavigationItem.HOME.route)
                }
            }
        }
    }
