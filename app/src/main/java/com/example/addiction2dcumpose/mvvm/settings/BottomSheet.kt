package com.example.addiction2dcumpose.mvvm.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.States.events.ui.SettingsScreenBottomSheetEvent
import com.example.addiction2dcumpose.dataClasses.Genre

@Composable
fun BottomsSheetContent(
    modifier: Modifier = Modifier,
    onItemClick: (Genre) -> Unit,
    event: SettingsScreenBottomSheetEvent,
    onTryAgainClicked: () -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(16.dp))
        Card(
            modifier = Modifier.size(width = 120.dp, height = 10.dp),
            backgroundColor = MaterialTheme.colors.primary
        ) { }
        when (event) {
            is SettingsScreenBottomSheetEvent.Loading -> {
                Spacer(modifier = Modifier.size(30.dp))
                CircularProgressIndicator(modifier = Modifier.size(60.dp))
            }
            is SettingsScreenBottomSheetEvent.LoadingComplete -> {
                GenresColumn(
                    event = event,
                    onGenresClick = { onItemClick.invoke(it) })
            }
            is SettingsScreenBottomSheetEvent.Error -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(id = R.string.some_error), style = MaterialTheme.typography.h5, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(onClick = { onTryAgainClicked.invoke() }) {
                        Text(text = stringResource(R.string.try_again))
                    }
                }

            }
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
        contentPadding = PaddingValues(12.dp),
        columns = GridCells.Fixed(3),
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
private fun GenreCard(modifier: Modifier = Modifier, genre: Genre) {
    Card(modifier = modifier.padding(4.dp), backgroundColor = MaterialTheme.colors.primaryVariant) {
        Text(text = genre.name, textAlign = TextAlign.Center)
    }
}

@Composable
private fun GenresClassCard(@StringRes stringRes: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier, backgroundColor = MaterialTheme.colors.primary) {
        Text(
            text = stringResource(id = stringRes),
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center
        )
    }
}