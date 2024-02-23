package com.example.liberolog.ui.appbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.liberolog.R

open class AppBar : Screen {
    override val route: String
        get() = ""

    @Composable
    override fun getTitle(): String {
        return ""
    }

    open fun getNavigationIcon(): @Composable () -> Unit {
        return {}
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
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = getNavigationIcon(),
        )
    }
}
