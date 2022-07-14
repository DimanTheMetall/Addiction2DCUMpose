package com.example.addiction2dcumpose.mvvm.random

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.StubData.MangaStubData
import com.example.addiction2dcumpose.StubData.MangaStubData.mangaData
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.rxpractic.ui.theme.Addiction2DTheme
import com.example.rxpractic.ui.theme.Shapes


class RandomScreen {


    @Composable
    fun Screen() {
        val state = remember { mutableStateOf(MangaStubData.mangaData) }
        Addiction2DTheme {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.size(16.dp))
                ImageTitleCard(
                    mangaData = state.value,
                    modifier = Modifier.size(340.dp, 400.dp)
                )
                Spacer(modifier = Modifier.size(20.dp))
                MangaInfoCard(
                    mangaData = mangaData,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.size(width = 340.dp, height = 100.dp),
                    shape = RoundedCornerShape(18.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                TwoButtons(
                    onNextClick = {},
                    onBackClick = {},
                    modifier = Modifier.size(width = 130.dp, height = 60.dp)
                )


            }

        }

    }

    @Preview
    @Composable
    fun ScreenPreview() {
        val state = remember { mutableStateOf(MangaStubData.mangaData) }
        Addiction2DTheme {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.size(20.dp))
                ImageTitleCardPreview(
                    mangaData = state.value,
                    modifier = Modifier.size(340.dp, 400.dp)
                )
                Spacer(modifier = Modifier.size(30.dp))
                MangaInfoCard(
                    mangaData = mangaData,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.size(width = 340.dp, height = 100.dp),
                    shape = RoundedCornerShape(18.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                TwoButtons(
                    onNextClick = {},
                    onBackClick = {},
                    modifier = Modifier.size(width = 130.dp, height = 60.dp)
                )

            }

        }


    }

    @Composable
    private fun TwoButtons(
        modifier: Modifier = Modifier,
        onNextClick: () -> Unit,
        onBackClick: () -> Unit
    ) {
        val buttonsModifier = Modifier
            .border(
                2.dp,
                shape = CircleShape,
                color = MaterialTheme.colors.primary
            )
            .size(40.dp)
            .background(color = MaterialTheme.colors.primary, shape = CircleShape)

        Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(
                onClick = { onBackClick.invoke() },
                modifier = buttonsModifier
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_forward),
                    contentDescription = null,
                    modifier = Modifier.rotate(180f)
                )
            }
            IconButton(onClick = { onNextClick.invoke() }, modifier = buttonsModifier) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_forward),
                    contentDescription = null
                )
            }

        }
    }


    @Composable
    private fun MangaInfoCard(
        modifier: Modifier = Modifier,
        mangaData: MangaData,
        style: androidx.compose.ui.text.TextStyle,
        shape: Shape
    ) {
        Card(
            modifier = modifier,
            shape = shape,
            border = BorderStroke(2.dp, MaterialTheme.colors.primary)
        ) {
            Column {
                Row {
                    Column {
                        Row(
                            Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = stringResource(id = R.string.volume), style = style)
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(text = mangaData.volumes.toString(), style = style)
                            Spacer(modifier = Modifier.size(20.dp))
                            Text(text = stringResource(id = R.string.chapters), style = style)
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(text = mangaData.chapters.toString(), style = style)
                        }
                        Row(
                            Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = stringResource(id = R.string.type), style = style)
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(text = mangaData.type, style = style)
                            Spacer(modifier = Modifier.size(20.dp))
                            Text(text = stringResource(id = R.string.status), style = style)
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(text = mangaData.status, style = style)
                        }
                    }
                    Column {

                    }
                }

            }
        }

    }

    @Composable
    private fun ImageTitleCard(
        modifier: Modifier = Modifier,
        mangaData: MangaData
    ) {
        val roundCornerShape = RoundedCornerShape(8.dp)
        Card(
            modifier = modifier,
            border = BorderStroke(2.dp, MaterialTheme.colors.primary),
            shape = RoundedCornerShape(18.dp)
        ) {
            Column {
                Text(text = mangaData.title, modifier = Modifier.padding(8.dp))
                com.skydoves.landscapist.glide.GlideImage(
                    imageModel = mangaData.images.jpg.imageUrl,
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

    @Composable
    private fun ImageTitleCardPreview(
        modifier: Modifier = Modifier,
        mangaData: MangaData
    ) {
        val roundCornerShape = RoundedCornerShape(8.dp)
        Card(
            modifier = modifier,
            border = BorderStroke(2.dp, MaterialTheme.colors.primary),
            shape = RoundedCornerShape(18.dp),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = mangaData.title, Modifier.padding(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = null,
                    Modifier
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 8.dp)
                        .border(
                            2.dp,
                            color = MaterialTheme.colors.primary,
                            shape = roundCornerShape
                        )
                        .clip(roundCornerShape)

                )
            }
        }
    }


}