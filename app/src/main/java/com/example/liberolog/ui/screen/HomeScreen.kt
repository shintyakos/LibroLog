package com.example.liberolog.ui.screen

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
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
    val homeModel = viewModel.homeModel.observeAsState()

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(padding),
    ) {
        MainContents(homeModel.value?.monBookList, homeModel.value?.recBookList)
    }
}

@Composable
private fun MainContents(
    monBooks: List<Book>?,
    recBooks: List<Book>?,
) {
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
        monBooks?.forEach {
            CardView(it)
        }
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
        recBooks?.forEach {
            CardView(it)
        }
    }
}

@Composable
@Suppress("ktlint:standard:max-line-length")
private fun CardView(book: Book) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(88.dp)
                .background(Color.White),
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://play.pokemonshowdown.com/sprites/ani/pikachu.gif"),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(150.dp),
        )
        Text(book.title)
    }
    Spacer(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFE0E0E0)),
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenPreview() {
    val monBooks = listOf(Book("title", "author"), Book("title2", "author2"))
    val recBooks = listOf(Book("title", "author"), Book("title2", "author2"))
    MainContents(monBooks, recBooks)
}
