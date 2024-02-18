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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.liberolog.R
import com.example.liberolog.utils.NavigationItem

class SignUpAppBar() : Screen {
    override val route: String
        get() = NavigationItem.SIGNUP.route

    @Composable
    override fun getTitle(): String {
        return stringResource(id = R.string.signup_title)
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
        )
    }
}
