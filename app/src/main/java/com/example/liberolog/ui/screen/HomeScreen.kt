package com.example.liberolog.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.liberolog.R
import com.example.liberolog.model.Book
import com.example.liberolog.ui.state.HomeState
import com.example.liberolog.utils.HOME_SCREEN_TAG
import com.example.liberolog.viewmodel.HomeViewModel

/**
 * `HomeScreen` is a Composable function that sets up the main screen of the application.
 * It uses a `Scaffold` to provide a consistent layout structure.
 * The `Scaffold` includes a top bar, a bottom bar, and a content area, each defined by separate Composable functions.
 */
@Composable
@Suppress(
    "ktlint:standard:argument-list-wrapping",
    "ktlint:standard:function-signature",
    "ktlint:standard:wrapping",
)
fun HomeScreen(
    padding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(padding),
    ) {
        val homeState by viewModel.homeState.collectAsState()
        Log.d(HOME_SCREEN_TAG, "homeState: $homeState")

        when (homeState) {
            is HomeState.StartState -> {
                viewModel.loadHomeModel()
            }

            is HomeState.LoadingState -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = ProgressIndicatorDefaults.CircularStrokeWidth,
                    )
                }
            }

            is HomeState.SuccessState -> {
                val model = (homeState as HomeState.SuccessState).model
                MainContents(model.monBookList)
            }

            else -> {
                Log.d(HOME_SCREEN_TAG, "Error: $homeState")
            }
        }
    }
}

@Composable
private fun MainContents(monBooks: List<Book>?) {
    Column {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(start = 10.dp, top = 11.dp, bottom = 11.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.month_book),
                style =
                    TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        color = Color(0xFF000000),
                    ),
            )
        }
        CardView(monBooks)
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(start = 10.dp, top = 11.dp, bottom = 11.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.recommend_book),
                style =
                    TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        color = Color(0xFF000000),
                    ),
            )
        }
    }
}

@Composable
@Suppress("ktlint:standard:max-line-length")
private fun CardView(books: List<Book>?) {
    LazyColumn {
        items(books ?: emptyList()) { book ->
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(88.dp)
                        .background(Color.White),
            ) {
                Image(
                    painter =
                        rememberAsyncImagePainter(
                            model = "https://books.google.com/books/content?id=DAjLCwAAQBAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api",
                        ),
                    modifier = Modifier.fillMaxHeight().width(68.dp).padding(2.dp),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(book.title)
                    Text(book.author)
                }
            }
            Spacer(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFE0E0E0)),
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MainContents(
        monBooks =
            listOf(
                Book(
                    title = "title",
                    author = "author",
                    image = "https://books.google.com/books/content?id=DAjLCwAAQBAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api",
                ),
            ),
    )
}
