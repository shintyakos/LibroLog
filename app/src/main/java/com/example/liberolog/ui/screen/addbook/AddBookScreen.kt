package com.example.liberolog.ui.screen.addbook

import android.util.Log
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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import com.example.liberolog.R
import com.example.liberolog.model.AddBookTab
import com.example.liberolog.model.Book
import com.example.liberolog.ui.state.SearchBookState
import com.example.liberolog.ui.theme.LiberoLogTheme
import com.example.liberolog.viewmodel.AddBookViewModel

@Composable
fun AddBookScreen(
    padding: PaddingValues,
    viewModel: AddBookViewModel = hiltViewModel(),
) {
    val searchBookState by viewModel.searchBookState.collectAsState()

    when (searchBookState) {
        SearchBookState.LoadState -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = ProgressIndicatorDefaults.CircularStrokeWidth,
                )
            }
        }

        is SearchBookState.SuccessState -> {
            Log.d("AddBookScreen", "bookMap: ${(searchBookState as? SearchBookState.SuccessState)?.searchedBookMap}")
            Column(
                modifier = Modifier.padding(padding).fillMaxSize(),
            ) {
                MainContents(
                    viewModel,
                    (searchBookState as? SearchBookState.SuccessState)?.searchedBookMap
                        ?: emptyMap(),
                )
            }
        }

        is SearchBookState.ErrorState -> {
            Dialog(onDismissRequest = { /*TODO*/ }) {
                Text(
                    text = "検索に失敗しました。",
                    style =
                        TextStyle(
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                        ),
                )
            }
        }

        else -> {
            Column(
                modifier = Modifier.padding(padding).fillMaxSize(),
            ) {
                MainContents(viewModel)
            }
        }
    }
}

@Composable
fun MainContents(
    viewModel: AddBookViewModel,
    searchedBookMap: Map<Book, MutableState<Boolean>> = emptyMap(),
) {
    val selectedTabIndex: Int =
        MutableLiveData(viewModel.model.selectedTabIndex).value ?: AddBookTab.ISBN.ordinal

    TabLayout(selectedTabIndex, viewModel::onSelectedTabIndex)
    Column(modifier = Modifier.padding(20.dp)) {
        when (selectedTabIndex) {
            AddBookTab.ISBN.ordinal -> {
                val searchedISBN = MutableLiveData(viewModel.model.searchIsbn).value ?: ""
                TabContents(
                    selectedTabIndex,
                    searchedISBN,
                    viewModel::onChangedISBN,
                    viewModel::onClickedSearchButton,
                )
            }

            AddBookTab.TITLE.ordinal -> {
                val searchedBookName = MutableLiveData(viewModel.model.searchBookName).value ?: ""
                TabContents(
                    selectedTabIndex,
                    searchedBookName,
                    viewModel::onChangedBookName,
                    viewModel::onClickedSearchButton,
                )
            }
        }
        SearchedBookList(searchedBookMap)
    }
}

@Composable
fun TabLayout(
    selectedTabIndex: Int,
    onSelectedTabIndex: (Int) -> Unit,
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
    ) {
        Tab(
            selected = selectedTabIndex == AddBookTab.ISBN.ordinal,
            onClick = { onSelectedTabIndex(AddBookTab.ISBN.ordinal) },
            modifier =
                Modifier.background(
                    if (selectedTabIndex == AddBookTab.ISBN.ordinal) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                ).padding(15.dp),
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
                color =
                    if (selectedTabIndex == AddBookTab.ISBN.ordinal) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outlineVariant
                    },
            )
        }
        Tab(
            selected = selectedTabIndex == AddBookTab.TITLE.ordinal,
            onClick = { onSelectedTabIndex(AddBookTab.TITLE.ordinal) },
            modifier =
                Modifier
                    .background(
                        if (selectedTabIndex == AddBookTab.TITLE.ordinal) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.surface
                        },
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
                color = if (selectedTabIndex == AddBookTab.TITLE.ordinal) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
            )
        }
    }
}

@Composable
fun TabContents(
    selectedTabIndex: Int,
    searchText: String,
    onChangeSearchedBox: (String) -> Unit,
    onClickedSearchButton: () -> Unit,
) {
    Column {
        Text(
            text =
                when (selectedTabIndex) {
                    AddBookTab.ISBN.ordinal -> "ISBN"
                    AddBookTab.TITLE.ordinal -> "タイトル"
                    else -> "ISBN"
                },
            style =
                TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    textAlign = TextAlign.Start,
                ),
        )
        Row(
            modifier =
                Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
        ) {
            BasicTextField(
                value = searchText,
                onValueChange = { onChangeSearchedBox(it) },
                singleLine = true,
                maxLines = 1,
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType =
                            when (selectedTabIndex) {
                                AddBookTab.ISBN.ordinal -> KeyboardType.Number
                                AddBookTab.TITLE.ordinal -> KeyboardType.Text
                                else -> KeyboardType.Number
                            },
                    ),
                modifier =
                    Modifier
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onSurface,
                            shape = MaterialTheme.shapes.extraSmall,
                        )
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth(0.7f),
                decorationBox = { innerTextField ->
                    Box(
                        modifier =
                            Modifier
                                .height(32.dp)
                                .padding(5.dp)
                                .background(MaterialTheme.colorScheme.surface),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        innerTextField()
                    }
                },
            )
            Button(
                onClick = { onClickedSearchButton() },
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
}

@Composable
fun SearchedBookList(searchedBookMap: Map<Book, MutableState<Boolean>>) {
    if (searchedBookMap.isNotEmpty()) {
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
                items(searchedBookMap.toList()) { (book, checkedState) ->
                    CardView(book = book, checkedState = checkedState)
                }
            }
        }
    }
}

@Composable
fun CardView(
    book: Book,
    checkedState: MutableState<Boolean>,
) {
    Row(
        modifier =
            Modifier
                .toggleable(
                    value = checkedState.value,
                    role = Role.Checkbox,
                    onValueChange = { checkedState.value = it },
                )
                .fillMaxWidth()
                .height(88.dp)
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(start = 5.dp),
    ) {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = null,
            modifier = Modifier.fillMaxHeight(),
        )
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(),
        )
        Column(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
        ) {
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

@Preview
@Composable
fun preView() {
    LiberoLogTheme {
        TabContents(
            selectedTabIndex = 0,
            searchText = "",
            onChangeSearchedBox = {},
            onClickedSearchButton = {},
        )
    }
}
