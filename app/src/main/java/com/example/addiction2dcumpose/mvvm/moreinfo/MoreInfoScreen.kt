package com.example.addiction2dcumpose.mvvm.moreinfo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.mvvm.random.MangaInformation
import com.example.rxpractic.ui.theme.Addiction2DTheme

class MoreInfoScreen(private val viewModel: MoreInfoViewModel, private val mangaData: MangaData) {

    init {
        viewModel.checkForContains(mangaData = mangaData)
    }

    @Composable
    fun Screen() {
        val buttonState = viewModel.favoriteFlow.collectAsState()

        Addiction2DTheme {
            Box(contentAlignment = Alignment.BottomEnd) {
                MangaInformation(mangaData = mangaData)
                IconButton(modifier = Modifier.padding(16.dp), onClick = {
                    if (!buttonState.value) {
                        viewModel.onAddInFavoriteClicked(mangaData = mangaData)
                    } else {
                        viewModel.onDeleteClicked(mangaData = mangaData)
                    }
                }) {
                    Icon(
                        modifier = Modifier.size(46.dp),
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = null,
                        tint = if (buttonState.value) MaterialTheme.colors.primary else Color.DarkGray
                    )
                }
            }
        }


    }

}

