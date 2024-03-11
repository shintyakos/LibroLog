package com.example.liberolog.ui.screen.addbook

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.liberolog.R
import com.example.liberolog.model.AddBookTab
import com.example.liberolog.model.Book
import com.example.liberolog.viewmodel.AddBookViewModel

@Composable
fun AddBookScreen(
    padding: PaddingValues,
    viewModel: AddBookViewModel = hiltViewModel(),
) {
    Column(
        modifier =
            Modifier
                .padding(padding)
                .fillMaxSize(),
    ) {
        MainContents(viewModel)
    }
}

@Composable
fun MainContents(viewModel: AddBookViewModel) {
    TabLayout(viewModel)
    Column(modifier = Modifier.padding(20.dp)) {
        when (viewModel.getSelectedTabIndex()) {
            AddBookTab.ISBN.ordinal -> {
                IsbnContents(viewModel)
            }
            AddBookTab.TITLE.ordinal -> {
                TitleContents(viewModel)
            }
        }
    }
}

@Composable
fun TabLayout(viewModel: AddBookViewModel) {
    TabRow(
        selectedTabIndex = viewModel.getSelectedTabIndex(),
    ) {
        Tab(
            selected = viewModel.getSelectedTabIndex() == AddBookTab.ISBN.ordinal,
            onClick = { viewModel.onSelectedTabIndex(AddBookTab.ISBN.ordinal) },
            modifier =
                Modifier
                    .background(
                        if (viewModel.getSelectedTabIndex() == AddBookTab.ISBN.ordinal) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.surface,
                    )
                    .padding(15.dp),
            selectedContentColor = MaterialTheme.colorScheme.surface,
            unselectedContentColor = MaterialTheme.colorScheme.surfaceVariant,
        ) {
            Text(
                text = "ISBN",
                style =
                    TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        textAlign = TextAlign.Center,
                    ),
                color = if (viewModel.getSelectedTabIndex() == AddBookTab.ISBN.ordinal) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
            )
        }
        Tab(
            selected = viewModel.getSelectedTabIndex() == AddBookTab.TITLE.ordinal,
            onClick = { viewModel.onSelectedTabIndex(AddBookTab.TITLE.ordinal) },
            modifier =
                Modifier
                    .background(
                        if (viewModel.getSelectedTabIndex() == AddBookTab.TITLE.ordinal) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.surface,
                    )
                    .padding(15.dp),
            selectedContentColor = MaterialTheme.colorScheme.surface,
            unselectedContentColor = MaterialTheme.colorScheme.outlineVariant,
        ) {
            Text(
                text = "タイトル",
                style =
                    TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        textAlign = TextAlign.Center,
                    ),
                color = if (viewModel.getSelectedTabIndex() == AddBookTab.TITLE.ordinal) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
            )
        }
    }
}

@Composable
fun IsbnContents(viewModel: AddBookViewModel) {
    val searchedBookList =
        mutableListOf(
            Book(title = "タイトル", author = "著者", image = ""),
            Book(title = "タイトル2", author = "著者2", image = ""),
        )
    Column(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(
                text = "ISBN",
                style =
                    TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        textAlign = TextAlign.Start,
                    ),
            )
            Row(modifier = Modifier.padding(top = 5.dp).fillMaxWidth()) {
                BasicTextField(
                    value = viewModel.getSearchedIsbn(),
                    onValueChange = { viewModel.onSearchIsbnChanged(it) },
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier =
                        Modifier
                            .border(1.dp, MaterialTheme.colorScheme.onSurface, shape = MaterialTheme.shapes.extraSmall)
                            .align(Alignment.CenterVertically)
                            .fillMaxWidth(0.7f),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.height(32.dp).padding(5.dp),
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            innerTextField()
                        }
                    },
                )
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(start = 7.dp),
                    shape = MaterialTheme.shapes.small,
                ) {
                    Text(
                        text = "検索",
                        style =
                            TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                textAlign = TextAlign.Center,
                            ),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
        if (searchedBookList.isNotEmpty()) {
            Column(modifier = Modifier.padding(top = 15.dp)) {
                Text(
                    text = "検索結果",
                    style =
                        TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    modifier = Modifier.padding(bottom = 10.dp),
                )
                Spacer(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.onSurface),
                )
                LazyColumn {
                    items(searchedBookList) { book ->
                        CardView(book = book, viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun TitleContents(viewModel: AddBookViewModel) {}

@Composable
fun CardView(
    book: Book,
    viewModel: AddBookViewModel,
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(88.dp).background(MaterialTheme.colorScheme.onPrimary).padding(5.dp),
    ) {
        Checkbox(
            checked = false,
            onCheckedChange = { /*TODO*/ },
            modifier = Modifier.fillMaxHeight(),
        )
        Box(modifier = Modifier.fillMaxWidth(0.3f).fillMaxHeight())
        Column(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
            Text(text = book.title)
            Text(text = book.author)
        }
    }
    Spacer(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.onSurface),
    )
}
