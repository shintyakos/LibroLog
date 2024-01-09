package com.example.liberolog

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.liberolog.ui.screen.HomeScreen

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
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen() }
    }
}
