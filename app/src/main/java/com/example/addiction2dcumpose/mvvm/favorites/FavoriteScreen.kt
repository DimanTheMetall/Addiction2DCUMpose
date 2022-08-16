package com.example.addiction2dcumpose.mvvm.favorites

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.addiction2dcumpose.DI.DaggerViewModelCreator
import com.example.addiction2dcumpose.DI.moreinfoComponent.DaggerMoreInfoComponent
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.findActivity
import com.example.addiction2dcumpose.getAsAddiction
import com.example.addiction2dcumpose.mvvm.moreinfo.MoreInfoScreen
import com.example.addiction2dcumpose.mvvm.search.CardItem
import com.example.rxpractic.ui.theme.Addiction2DTheme
import com.google.gson.Gson

class FavoriteScreen(private val viewModel: MangaFavoriteViewModel) : DaggerViewModelCreator {

    @Composable
    fun Screen() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "favorite") {
            composable(route = "favorite") {
                val state = viewModel.mangaState.collectAsState()
                Addiction2DTheme {
                    if (!state.value.isNullOrEmpty()) {
                        FavoriteList(
                            mangaData = state.value!!,
                            onItemClicked = { mangaData ->
                                val dataJson = Uri.encode(Gson().toJson(mangaData))
                                navController.navigate(route = "moreInfo/$dataJson")
                            })
                    } else {
                        OnNullData(modifier = Modifier.fillMaxSize())
                    }
                }
            }

            composable(
                route = "moreInfo/{mangaData}", arguments =
                listOf(
                    navArgument(
                        name = "mangaData",
                        builder = { type = MangaData.MangaDataArgument })
                )
            ) {
                val activity = LocalContext.current.findActivity()
                val moreInfoViewModel = DaggerMoreInfoComponent.factory()
                    .create(activity.application.getAsAddiction().addictionComponent)
                    .getViewModel()
                MoreInfoScreen(
                    mangaData = it.arguments?.getParcelable("mangaData")!!,
                    viewModel = moreInfoViewModel
                ).Screen()
            }
        }

    }

}

@Composable
fun FavoriteList(mangaData: List<MangaData>, onItemClicked: (MangaData) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        mangaData.forEach {
            item {
                CardItem(mangaData = it, modifier = Modifier.clickable {
                    onItemClicked.invoke(it)
                })
            }
        }
    }
}

@Composable
fun OnNullData(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.null_favorite),
        textAlign = TextAlign.Center
    )
}
