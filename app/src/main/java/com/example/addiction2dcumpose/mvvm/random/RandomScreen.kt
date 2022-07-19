package com.example.addiction2dcumpose.mvvm.random

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.addiction2dcumpose.BaseScreen
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.StubData.MangaStubData.mangaData
import com.example.addiction2dcumpose.dataClasses.Genre
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.dataClasses.MangaResult
import com.example.addiction2dcumpose.dataClasses.RandomScreenButtonState
import com.example.rxpractic.ui.theme.Addiction2DTheme


class RandomScreen(val viewModel: RandomViewModel) : BaseScreen() {

    @Composable
    fun Screen() {
        val state = viewModel.mangaFLowData.collectAsState()
        val buttonsState = viewModel.buttonsStateFlow.collectAsState()
        println("AAA $buttonsState")

        when (state.value) {
            is MangaResult.Success -> {
                ResultScreen(
                    mangaData = (state.value as MangaResult.Success).mangaData,
                    onBackClick = {viewModel.onBackClick()},
                    onNextClick = { viewModel.onNextCLick() },
                    buttonState = buttonsState.value
                )


                println("AAA OnSuccessScreen")
            }
            MangaResult.Error -> {
                println("AAA OnErrorScreen")
            }
            MangaResult.Progress -> {
                println("AAA OnProgressScreen")
            }
        }

    }
}

@Composable
private fun ResultScreen(
    mangaData: MangaData,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    buttonState: RandomScreenButtonState
) {
    val scrollState = rememberScrollState()
    val isScrolling = scrollState.isScrollInProgress
    val cardsWidth = 340.dp
    val cardShape = RoundedCornerShape(18.dp)



    Addiction2DTheme {
        val cardBorder = BorderStroke(2.dp, MaterialTheme.colors.primary)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            ImageTitleCard(
                mangaData = mangaData,
                modifier = Modifier.size(width = cardsWidth, height = 500.dp),
                shape = cardShape,
                borderStroke = cardBorder
            )
            Spacer(modifier = Modifier.size(16.dp))
            if (!mangaData.genres.isNullOrEmpty()) {
                GenresCard(
                    genresList = mangaData.genres,
                    shape = cardShape,
                    modifier = Modifier
                        .width(cardsWidth)
                        .height(90.dp)
                        .wrapContentSize(),
                    borderStroke = cardBorder
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            MangaInfoCard(
                mangaData = mangaData,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .wrapContentSize()
                    .width(cardsWidth),
                shape = cardShape,
                borderStroke = cardBorder
            )
        }
        if (!isScrolling) TwoButtons(
            onNextClick = { onNextClick.invoke() },
            onBackClick = { onBackClick.invoke() },
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            state = buttonState
        )

    }

}

@Composable
private fun GenresCard(
    modifier: Modifier = Modifier,
    genresList: List<Genre>,
    shape: Shape,
    borderStroke: BorderStroke
) {
    Card(modifier = modifier, shape = shape, border = borderStroke) {
        CustomFlexBox {
            genresList.forEach { genre ->
                GenresItem(genre = genre, modifier = Modifier.wrapContentSize())
            }
        }
    }
}

@Composable
private fun GenresItem(modifier: Modifier = Modifier, genre: Genre) {
    Card(
        modifier = modifier.padding(6.dp),
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = genre.name, modifier = Modifier.padding(4.dp))
    }
}


@Composable
private fun TwoButtons(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    state: RandomScreenButtonState
) {
    val buttonsModifier = Modifier
        .size(40.dp)
        .background(color = MaterialTheme.colors.primary, shape = CircleShape)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        FloatingActionButton(
            onClick = onBackClick,
            modifier = if (state.isBackButtonActive) buttonsModifier else Modifier.size(0.dp),
            backgroundColor = MaterialTheme.colors.background
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_forward),
                contentDescription = null,
                modifier = Modifier.rotate(180f)
            )
        }
        FloatingActionButton(
            onClick = onNextClick,
            modifier = if (state.isNextButtonActive) buttonsModifier else Modifier.size(0.dp),
            backgroundColor = MaterialTheme.colors.background
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_forward),
                contentDescription = null
            )
        }

    }
}

@Composable
private fun CustomFlexBox(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout(content = content, modifier = modifier, measurePolicy = { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        layout(width = constraints.maxWidth, height = constraints.maxHeight) {
            var y = 0
            var x = 0

            placeables.forEach { placeable ->
                if (placeable.width + x > constraints.maxWidth) {
                    x = 0
                    y += placeable.height
                }

                placeable.place(x, y)
                x += placeable.width
            }

        }
    })
}


@Composable
private fun MangaInfoCard(
    modifier: Modifier = Modifier,
    mangaData: MangaData,
    style: TextStyle,
    shape: Shape,
    borderStroke: BorderStroke
) {
    Card(
        modifier = modifier,
        shape = shape,
        border = borderStroke
    ) {
        Row(
            Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FirstColumn(mangaData = mangaData, style = style, modifier = Modifier.fillMaxHeight())
            SecondColumn(mangaData = mangaData, style = style, modifier = Modifier.fillMaxHeight())
        }
    }

}

@Composable
private fun FirstColumn(modifier: Modifier = Modifier, mangaData: MangaData, style: TextStyle) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = stringResource(id = R.string.volume) + " ${mangaData.volumes ?: "?"}",
            style = style
        )
        Text(text = stringResource(id = R.string.type) + " ${mangaData.type ?: "?"}", style = style)
    }
}

@Composable
private fun SecondColumn(modifier: Modifier = Modifier, mangaData: MangaData, style: TextStyle) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = stringResource(id = R.string.chapters) + " ${mangaData.chapters ?: "?"}",
            style = style
        )
        Text(
            text = stringResource(id = R.string.status) + " ${mangaData.status ?: "?"}",
            style = style
        )
    }
}

@Composable
private fun ImageTitleCard(
    modifier: Modifier = Modifier,
    mangaData: MangaData,
    shape: Shape,
    borderStroke: BorderStroke
) {
    val roundCornerShape = RoundedCornerShape(8.dp)
    Card(
        modifier = modifier,
        border = borderStroke,
        shape = shape
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = mangaData.title ?: "No title",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
            com.skydoves.landscapist.glide.GlideImage(
                imageModel = mangaData.images?.jpg?.imageUrl,
                placeHolder = painterResource(id = R.drawable.placeholder),
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 8.dp)
                    .border(
                        2.dp,
                        color = MaterialTheme.colors.primary,
                        shape = roundCornerShape
                    )
                    .clip(roundCornerShape),
                previewPlaceholder = R.drawable.placeholder
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnSuccessScreenPreview() {
    ResultScreen(
        mangaData = mangaData,
        onBackClick = { /*TODO*/ },
        onNextClick = { TODO() },
        buttonState = RandomScreenButtonState(
            isBackButtonActive = false,
            isNextButtonActive = false
        )
    )
}


