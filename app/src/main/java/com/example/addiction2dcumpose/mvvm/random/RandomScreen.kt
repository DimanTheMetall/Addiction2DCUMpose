package com.example.addiction2dcumpose.mvvm.random

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.addiction2dcumpose.BaseScreen
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.States.MangaResultState
import com.example.addiction2dcumpose.States.RandomScreenButtonState
import com.example.addiction2dcumpose.StubData.MangaStubData.mangaData
import com.example.addiction2dcumpose.customLayout.CustomFlexBox
import com.example.addiction2dcumpose.dataClasses.*
import com.example.rxpractic.ui.theme.Addiction2DTheme
import com.skydoves.landscapist.CircularReveal


class RandomScreen(val viewModel: RandomViewModel) : BaseScreen() {

    @Composable
    fun Screen() {
        val state = viewModel.mangaFLowData.collectAsState()
        val buttonsState = viewModel.buttonsStateFlow.collectAsState()

        when (state.value) {
            is MangaResultState.Success -> {
                MangaInformation(
                    mangaData = (state.value as MangaResultState.Success).mangaData
                )
            }
            MangaResultState.Error -> {
                OnErrorScreen()
            }
            MangaResultState.Progress -> {
                OnLoadingScreen()
            }
        }
        TwoButtons(
            onNextClick = { viewModel.onNextCLick() },
            onBackClick = { viewModel.onBackClick() },
            state = buttonsState.value,
            verticalAlignment =Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)
        )

    }
}

@Composable
private fun OnLoadingScreen() {
    Addiction2DTheme {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            strokeWidth = 16.dp
        )
    }
}

@Composable
private fun OnErrorScreen(
) {
    Addiction2DTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Card(
                shape = CircleShape,
                border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = null
                )
            }
            Card(
                shape = CircleShape,
                border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.some_error),
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}

@Composable
fun MangaInformation(
    mangaData: MangaData,
) {
    val scrollState = rememberScrollState()
    val cardsWidth = 340.dp
    val cardShape = RoundedCornerShape(18.dp)

    val space = 16.dp

    Addiction2DTheme {
        val cardBorder = BorderStroke(2.dp, MaterialTheme.colors.primary)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(space))
            ImageTitleCard(
                mangaData = mangaData,
                modifier = Modifier.size(width = cardsWidth, height = 500.dp),
                shape = cardShape,
                borderStroke = cardBorder
            )
            Spacer(modifier = Modifier.size(space))
            if (!mangaData.genres.isNullOrEmpty()) {
                ItemsCard(
                    itemsList = mangaData.genres,
                    shape = cardShape,
                    modifier = Modifier
                        .width(cardsWidth)
                        .wrapContentSize(),
                    borderStroke = cardBorder,
                    title = stringResource(id = R.string.genres)
                )
                Spacer(modifier = Modifier.size(space))
            }

            if (!mangaData.themes.isNullOrEmpty()){
                ItemsCard(
                    itemsList = mangaData.themes,
                    shape = cardShape,
                    modifier = Modifier
                        .width(cardsWidth)
                        .wrapContentSize(),
                    borderStroke = cardBorder,
                    title = stringResource(id = R.string.themes)
                )
                Spacer(modifier = Modifier.size(space))
            }
            if (!mangaData.serializations.isNullOrEmpty()){
                ItemsCard(
                    itemsList = mangaData.serializations,
                    shape = cardShape,
                    modifier = Modifier
                        .width(cardsWidth)
                        .wrapContentSize(),
                    borderStroke = cardBorder,
                    title = stringResource(id = R.string.serializations)
                )
                Spacer(modifier = Modifier.size(space))
            }
            if (!mangaData.authors.isNullOrEmpty()) {
                ItemsCard(
                    itemsList = mangaData.authors,
                    shape = cardShape,
                    borderStroke = cardBorder,
                    modifier = Modifier
                        .width(cardsWidth)
                        .wrapContentSize(),
                    title = stringResource(id = R.string.authors)

                )
                Spacer(modifier = Modifier.size(space))
            }
            MangaInfoCard(
                mangaData = mangaData,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .wrapContentSize()
                    .width(cardsWidth),
                shape = cardShape,
                borderStroke = cardBorder
            )
            Spacer(modifier = Modifier.size(space))
            if (!mangaData.synopsis.isNullOrEmpty()) SynopsysCard(
                synopsys = mangaData.synopsis,
                shape = cardShape,
                modifier = Modifier
                    .wrapContentSize()
                    .width(cardsWidth),
                borderStroke = cardBorder
            )
        }
    }
}


@Composable
private fun SynopsysCard(
    modifier: Modifier = Modifier,
    synopsys: String,
    shape: Shape,
    borderStroke: BorderStroke
) {
    Card(modifier = modifier, shape = shape, border = borderStroke) {
        Text(text = synopsys, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Justify)
    }
}

@Composable
private fun ItemsCard(
    modifier: Modifier = Modifier,
    itemsList: List<MangaItemReceive>,
    shape: Shape,
    borderStroke: BorderStroke,
    title: String
) {
    Card(modifier = modifier, shape = shape, border = borderStroke) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = title, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            CustomFlexBox {
                itemsList.forEach { items ->
                    TextItem(string = items.name ?: "", modifier = Modifier.wrapContentSize())
                }
            }
        }
    }
}

@Composable
private fun TextItem(modifier: Modifier = Modifier, string: String) {
    Card(
        modifier = modifier.padding(6.dp),
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = string, modifier = Modifier.padding(4.dp))
    }
}


@Composable
private fun TwoButtons(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    state: RandomScreenButtonState,
    verticalAlignment: Alignment.Vertical
) {
    Addiction2DTheme {
        val buttonsModifier = Modifier
            .size(40.dp)
            .background(color = MaterialTheme.colors.primary, shape = CircleShape)
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = verticalAlignment,
        ) {
            AnimatedVisibility(visible = state.isBackButtonActive) {
                FloatingActionButton(
                    onClick = onBackClick,
                    modifier = buttonsModifier,
                    backgroundColor = MaterialTheme.colors.background
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_forward),
                        contentDescription = null,
                        modifier = Modifier.rotate(180f)
                    )
                }
            }
            Spacer(modifier = Modifier.size(80.dp))
            AnimatedVisibility(visible = state.isNextButtonActive) {
                FloatingActionButton(
                    onClick = onNextClick,
                    modifier = buttonsModifier,
                    backgroundColor = MaterialTheme.colors.background
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_forward),
                        contentDescription = null
                    )
                }
            }
        }
    }
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
                previewPlaceholder = R.drawable.placeholder,
                circularReveal = CircularReveal(),
                error = painterResource(id = R.drawable.placeholder)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnSuccessScreenPreview() {
    MangaInformation(
        mangaData = mangaData,
    )
}

@Preview
@Composable
private fun OnErrorScreenPreview() {
    Addiction2DTheme {
        OnErrorScreen()
    }

}


