package com.example.addiction2dcumpose.mvvm.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.States.events.ui.SettingsScreenBottomSheetEvent
import com.example.addiction2dcumpose.dataClasses.*
import com.example.rxpractic.ui.theme.Addiction2DTheme
import kotlinx.coroutines.launch

class SettingsScreen(private val viewModel: SettingsViewModel, settings: SearchSettings) {

    init {
        viewModel.onInitScreen(settings)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Screen(onSettingsChanged: (SearchSettings) -> Unit) {

        val settingsState = viewModel.settingsFlow.collectAsState()
        val bottomsSheetUi = viewModel.bottomSheetEvents.collectAsState()

        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberBottomSheetState(
                initialValue = BottomSheetValue.Collapsed
            )
        )
        val coroutineScope = rememberCoroutineScope()
        Addiction2DTheme {
            BottomSheetScaffold(sheetContent = {
                BottomsSheetContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(460.dp),
                    onItemClick = { viewModel.onGenresClick(it) },
                    event = bottomsSheetUi.value
                )
            }, scaffoldState = bottomSheetScaffoldState, sheetPeekHeight = 0.dp) { paddingValues ->
                Settings(
                    state = settingsState.value,
                    onTypeChanged = { viewModel.onTypeChanged(it) },
                    onStatusChanged = { viewModel.onStatusChanged(it) },
                    onOrderByChanged = { viewModel.onOrderByChanged(it) },
                    onSortChanged = { viewModel.onSortChanged(it) },
                    onGenresIncludeIconCLick = {
                        coroutineScope.launch {
                            viewModel.onBottomsSheetOpen(true)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                )
            }
        }


    }

}

@Composable
private fun GenresTextField(
    state: SearchSettings,
    modifier: Modifier = Modifier,
    onGenresIconCLick: () -> Unit,
    onExcludeGenresIconCLick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(text = stringResource(id = R.string.genres))
        Row {
            LazyRow {
                state.genresInclude?.forEach { genre ->
                    item {
                        Card {
                            Text(text = genre.name)
                        }
                    }
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_add_box),
                contentDescription = null,
                modifier = Modifier.clickable { onGenresIconCLick.invoke() })
        }
        Text(text = stringResource(id = R.string.explicit_genres))
        Row {
            LazyRow {
                state.genresExclude?.forEach { genre ->
                    item {
                        Card {
                            Text(text = genre.name)
                        }
                    }
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_add_box),
                contentDescription = null,
                modifier = Modifier.clickable { onExcludeGenresIconCLick.invoke() })
        }
    }
}

@Composable
private fun Settings(
    state: SearchSettings,
    onTypeChanged: (SettingsType) -> Unit,
    onStatusChanged: (SettingsType) -> Unit,
    onOrderByChanged: (SettingsType) -> Unit,
    onSortChanged: (SettingsType) -> Unit,
    onGenresIncludeIconCLick: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val spacerModifier = Modifier.height(16.dp)
    Addiction2DTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = spacerModifier)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
            ) {
                Column(Modifier.width((screenWidth / 2).dp)) {
                    RowItems(
                        type = state.type,
                        onValueChanged = { onTypeChanged.invoke(it) },
                        list = MangaType.values(),
                        modifier = Modifier
                    )
                    Spacer(modifier = spacerModifier)
                    RowItems(
                        type = state.orderBy,
                        onValueChanged = { onOrderByChanged.invoke(it) },
                        list = OrderBy.values()
                    )
                }
                Column(Modifier.width((screenWidth / 2).dp)) {
                    RowItems(
                        type = state.status,
                        onValueChanged = { onStatusChanged.invoke(it) },
                        list = MangaStatus.values(),
                    )
                    Spacer(modifier = spacerModifier)
                    RowItems(
                        type = state.sort,
                        onValueChanged = { onSortChanged.invoke(it) },
                        list = Sort.values()
                    )
                }
            }
            Spacer(modifier = spacerModifier)
            Text(
                text = stringResource(id = R.string.genres_settings),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(26.dp))
            GenresTextField(state = state, modifier = Modifier.fillMaxWidth(), onGenresIconCLick = {
                onGenresIncludeIconCLick.invoke()
            }) {

            }

        }
    }
}


@Composable
private fun <T : SettingsType> RowItems(
    modifier: Modifier = Modifier,
    type: SettingsType?,
    onValueChanged: (SettingsType) -> Unit,
    list: Array<T>
) {
    val textStyle = MaterialTheme.typography.h6
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = list[0].getTypeName(), style = textStyle, color = MaterialTheme.colors.primary)
        TextDropMenu(
            currentItem = type,
            list = list,
            textStyle = textStyle,
            onSelectType = { onValueChanged.invoke(it) })
    }
}

@Composable
private fun <T : SettingsType> TextDropMenu(
    currentItem: SettingsType?,
    list: Array<T>,
    onSelectType: (SettingsType) -> Unit,
    textStyle: TextStyle = TextStyle()
) {
    val state = remember { mutableStateOf(false) }

    Row(modifier = Modifier.clickable { state.value = !state.value }) {
        Text(
            text = currentItem?.settingsName ?: "",
            style = textStyle,
            color = MaterialTheme.colors.primary
        )
        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
        DropMenu(expanded = state.value, list = list, textStyle = textStyle, onSelectType = {
            onSelectType.invoke(it)
            state.value = false
        })
    }
}

@Composable
private fun <T : SettingsType> DropMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    list: Array<T>,
    onSelectType: (SettingsType) -> Unit,
    textStyle: TextStyle = TextStyle()
) {
    DropdownMenu(modifier = modifier, expanded = expanded, onDismissRequest = { }) {
        list.forEach { settingsType ->
            DropdownMenuItem(onClick = { onSelectType.invoke(settingsType) }) {
                Text(
                    text = settingsType.settingsName,
                    style = textStyle,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
private fun BottomsSheetContent(
    modifier: Modifier = Modifier,
    onItemClick: (Genre) -> Unit,
    event: SettingsScreenBottomSheetEvent
) {

    when (event) {
        is SettingsScreenBottomSheetEvent.Loading -> {
            CircularProgressIndicator(modifier = modifier)
        }
        is SettingsScreenBottomSheetEvent.LoadingComplete -> {
            GenresColumn(
                event = event,
                modifier = modifier,
                onGenresClick = { onItemClick.invoke(it) })
        }
    }
}

@Composable
private fun GenresColumn(
    modifier: Modifier = Modifier,
    event: SettingsScreenBottomSheetEvent.LoadingComplete,
    onGenresClick: (Genre) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        contentPadding = PaddingValues(10.dp),
        columns = GridCells.Fixed(3)
    ) {
        item(span = {
            GridItemSpan(maxLineSpan)
        }, content = {
            GenresClassCard(stringRes = R.string.genres)
        })

        event.genres?.forEach { genre ->
            item {
                GenreCard(
                    genre = genre,
                    modifier = Modifier.clickable { onGenresClick.invoke(genre) })
            }
        }

        item(span = {
            GridItemSpan(maxLineSpan)
        }, content = {
            GenresClassCard(stringRes = R.string.explicit_genres)
        })

        event.explicitGenres?.forEach { genre ->
            item {
                GenreCard(
                    genre = genre,
                    modifier = Modifier.clickable { onGenresClick.invoke(genre) })
            }
        }

        item(span = {
            GridItemSpan(maxLineSpan)
        }, content = {
            GenresClassCard(stringRes = R.string.themes)
        })

        event.themes?.forEach { genre ->
            item {
                GenreCard(
                    genre = genre,
                    modifier = Modifier.clickable { onGenresClick.invoke(genre) })
            }
        }

        item(span = {
            GridItemSpan(maxLineSpan)
        }, content = {
            GenresClassCard(stringRes = R.string.demographics)
        })

        event.demographics?.forEach { genre ->
            item {
                GenreCard(
                    genre = genre,
                    modifier = Modifier.clickable { onGenresClick.invoke(genre) })
            }
        }

    }
}

@Composable
private fun GenresClassCard(@StringRes stringRes: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Text(text = stringResource(id = stringRes))
    }
}

@Composable
private fun GenreCard(modifier: Modifier = Modifier, genre: Genre) {
    Card(modifier = modifier) {
        Text(text = genre.name)
    }
}


@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    Settings(
        state = SearchSettings(),
        onTypeChanged = { },
        onStatusChanged = { },
        onOrderByChanged = { },
        onSortChanged = {},
        onGenresIncludeIconCLick = {})
}

