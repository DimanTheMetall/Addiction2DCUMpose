package com.example.addiction2dcumpose.mvvm.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.addiction2dcumpose.Constants
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.States.SearchMangaState
import com.example.addiction2dcumpose.StubData.MangaStubData
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.dataClasses.SearchSettings
import com.example.rxpractic.ui.theme.Addiction2DTheme
import com.skydoves.landscapist.CircularReveal

class SearchScreen(private val viewModel: SearchViewModel) {

    @Composable
    fun Screen() {
        val navController = rememberNavController()
        val settingsState = viewModel.settingsFlowState.collectAsState()
        val screenState = viewModel.screenFlowState.collectAsState()

        NavHost(navController = navController, startDestination = "searchList") {
            composable("searchList") {
                SearchingList(
                    screenState = screenState.value,
                    settingsState = settingsState.value,
                    onValueChanged = { value -> viewModel.onValueChanged(value) },
                    onIconClicked = {  },
                    onPagingScroll = {}
                )
            }
            composable("MangaSettings") {}
            composable("moreInfo") {}
        }
    }

}

@Composable
fun SearchingList(
    screenState: SearchMangaState,
    settingsState: SearchSettings,
    onValueChanged: (String) -> Unit,
    onIconClicked: () -> Unit,
    onPagingScroll: () -> Unit
) {
    val scrollState = rememberLazyListState()
    val onPagingItemScrollStateBoolean = remember {
        derivedStateOf {
            if (!screenState.titlesList.isNullOrEmpty()) {
                scrollState.firstVisibleItemIndex == screenState.titlesList.lastIndex - Constants.PAGINATION_DIFF
            } else {
                true
            }
        }
    }

    if (onPagingItemScrollStateBoolean.value) {
        onPagingScroll.invoke()
    }

    Addiction2DTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = settingsState.q ?: "",
                onValueChange = { value -> onValueChanged.invoke(value) },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                maxLines = 1,
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                onIconClicked.invoke()
                            }
                    )
                }
            )
            if (!screenState.titlesList.isNullOrEmpty()) LazyColumn(
                verticalArrangement = Arrangement.spacedBy(
                    8.dp
                ),
                state = scrollState
            ) {
                screenState.titlesList.forEach { mangaData ->
                    item {
                        CardItem(
                            mangaData = mangaData,
                            modifier = Modifier.size(width = 350.dp, height = 220.dp)
                        )
                    }
                }
            }
        }

    }

}

@Composable
private fun CardItem(modifier: Modifier = Modifier, mangaData: MangaData) {
    val roundCornerShape = RoundedCornerShape(8.dp)
    Card(
        modifier = modifier, border = BorderStroke(2.dp, color = MaterialTheme.colors.primary),
        shape = RoundedCornerShape(10)
    ) {
        Row {
            com.skydoves.landscapist.glide.GlideImage(
                imageModel = mangaData.images?.jpg?.imageUrl,
                placeHolder = painterResource(id = R.drawable.placeholder),
                modifier = Modifier
                    .padding(8.dp)
                    .border(
                        2.dp,
                        color = MaterialTheme.colors.primary,
                        shape = roundCornerShape
                    )
                    .clip(roundCornerShape)
                    .width(150.dp),
                previewPlaceholder = R.drawable.placeholder,
                circularReveal = CircularReveal(),
                error = painterResource(id = R.drawable.placeholder)
            )
            MangaInform(mangaData = mangaData)

        }
    }
}

@Composable
private fun MangaInform(modifier: Modifier = Modifier, mangaData: MangaData) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = mangaData.title ?: "No title",
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Type ${mangaData.type ?: "No type"}",
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(
                start = 8.dp
            )
        )
        Text(
            text = "Status ${mangaData.status ?: "No status"}",
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(
                start = 8.dp
            )
        )
    }

}


@Preview(showBackground = true)
@Composable
private fun SearchingListPreview() {
    SearchingList(
        screenState = SearchMangaState(
            titlesList = MutableList(10) { MangaStubData.mangaData },
            isLoading = true,
            haveErrors = false
        ),
        settingsState = SearchSettings(),
        onValueChanged = {},
        onIconClicked = {},
        onPagingScroll = {}
    )
}


