package com.example.liberolog.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.liberolog.R

/**
 * `HomeScreen` is a Composable function that sets up the main screen of the application.
 * It uses a `Scaffold` to provide a consistent layout structure.
 * The `Scaffold` includes a top bar, a bottom bar, and a content area, each defined by separate Composable functions.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { HomeScreenTopBar() },
        bottomBar = { HomeScreenBottomBar() },
        content = { HomeScreenContent() },
    )
}

/**
 * `HomeScreenTopBar` is a Composable function that defines the top bar of the `HomeScreen`.
 */
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
                                offset = androidx.compose.ui.geometry.Offset(4.0f, 4.0f),
                            ),
                    ),
            )
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(id = R.string.home_icons_menu),
                    tint = Color.White,
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = stringResource(id = R.string.home_icons_settings),
                    tint = Color.White,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2196F3)),
        modifier = Modifier.fillMaxWidth(),
    )
}

/**
 * `HomeScreenContent` is a Composable function that defines the content area of the `HomeScreen`.
 */
@Composable
fun HomeScreenContent() {
}

/**
 * `HomeScreenBottomBar` is a Composable function that defines the bottom bar of the `HomeScreen`.
 */
@Composable
fun HomeScreenBottomBar() {
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
