package com.example.addiction2dcumpose.mvvm.moreinfo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.mvvm.random.MangaInformation

class MoreInfoScreen(private val viewModel: MoreInfoViewModel, private val mangaData: MangaData) {

    @Composable
    fun Screen(){
        Column {
            MangaInformation(mangaData = mangaData)
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.ic_favorite), contentDescription = null)
            }
        }

    }

}

