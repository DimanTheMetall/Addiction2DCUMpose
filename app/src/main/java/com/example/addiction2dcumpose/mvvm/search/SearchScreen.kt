package com.example.addiction2dcumpose.mvvm.search

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.addiction2dcumpose.Constants
import com.example.addiction2dcumpose.DI.DaggerViewModelCreator
import com.example.addiction2dcumpose.DI.daggerViewModel
import com.example.addiction2dcumpose.DI.moreinfoComponent.DaggerMoreInfoComponent
import com.example.addiction2dcumpose.DI.settingsComponent.DaggerSettingsComponent
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.States.SearchMangaState
import com.example.addiction2dcumpose.StubData.MangaStubData
import com.example.addiction2dcumpose.customLayout.CustomFlexBox
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.dataClasses.SearchSettings
import com.example.addiction2dcumpose.findActivity
import com.example.addiction2dcumpose.getAsAddiction
import com.example.addiction2dcumpose.mvvm.moreinfo.MoreInfoScreen
import com.example.addiction2dcumpose.mvvm.settings.SettingsScreen
import com.example.rxpractic.ui.theme.Addiction2DTheme
import com.google.gson.Gson

class SearchScreen(private val viewModel: SearchViewModel) : DaggerViewModelCreator {

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
                    onValueChanged = { value -> viewModel.onTextFieldValueChanged(value) },
                    onIconClicked = { navController.navigate("MangaSettings") },
                    onPagingScroll = { viewModel.onPageScrolled() },
                    onItemClicked = { mangaData ->
                        val dataJson = Uri.encode(Gson().toJson(mangaData))
                        navController.navigate(
                            route = "moreInfo/$dataJson"
                        )
                    }
                )
            }
            composable("MangaSettings") {
                val activity = LocalContext.current.findActivity()
                val settingsViewModel = daggerViewModel {
                    DaggerSettingsComponent.factory()
                        .create(activity.application.getAsAddiction().addictionComponent)
                        .getViewModel()
                }
                SettingsScreen(
                    viewModel = settingsViewModel,
                    settings = settingsState.value
                ).Screen(onSettingsChanged = { newSettings ->
                    viewModel.changeSettings(newSettings)
                }, navController = navController)
            }
            composable(
                "moreInfo/{mangaData}",
                arguments = listOf(
                    navArgument(
                        "mangaData",
                        builder = { type = MangaData.MangaDataArgument })
                )
            ) {
                val activity = LocalContext.current.findActivity()
                val moreInfoViewModel = DaggerMoreInfoComponent.factory()
                    .create(activity.application.getAsAddiction().addictionComponent)
                    .getViewModel()

                MoreInfoScreen(
                    moreInfoViewModel,
                    it.arguments?.getParcelable("mangaData")!!
                ).Screen()
            }
        }
    }

}

@Composable
fun SearchingList(
    screenState: SearchMangaState,
    settingsState: SearchSettings,
    onValueChanged: (String) -> Unit,
    onIconClicked: () -> Unit,
    onPagingScroll: () -> Unit,
    onItemClicked: (MangaData) -> Unit
) {
    val scrollState = rememberLazyListState()
    val onPagingItemScrollStateBoolean by rememberUpdatedState(newValue = derivedStateOf {
        scrollState.firstVisibleItemIndex == (screenState.titlesList?.lastIndex
            ?: 0) - Constants.PAGINATION_DIFF
    })

    if (onPagingItemScrollStateBoolean.value && !screenState.isLoading && !screenState.isPageLast) {
        onPagingScroll.invoke()
    }

    if (screenState.haveErrors) {
        Toast.makeText(
            LocalContext.current,
            stringResource(id = R.string.toast_error),
            Toast.LENGTH_SHORT
        ).show()
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
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                onIconClicked.invoke()
                            }
                            .padding(end = 4.dp)

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
                            modifier = Modifier
                                .size(width = 350.dp, height = 220.dp)
                                .clickable {
                                    onItemClicked.invoke(mangaData)
                                }
                        )
                    }
                }
            }
        }
        if (screenState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                CircularProgressIndicator(modifier = Modifier.size(40.dp))
            }

        }

    }

}

@Composable
fun CardItem(modifier: Modifier = Modifier, mangaData: MangaData) {
    val roundCornerShape = RoundedCornerShape(12.dp)
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
                error = painterResource(id = R.drawable.placeholder)
            )
            MangaInform(mangaData = mangaData, modifier = Modifier.padding(4.dp))
        }
    }
}


@Composable
private fun MangaInform(modifier: Modifier = Modifier, mangaData: MangaData) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            text = mangaData.title ?: "No title",
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center,
            maxLines = 3
        )
        Text(
            text = "Type: ${mangaData.type ?: "No type"}",
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(
                start = 8.dp
            )
        )
        Text(
            text = "Status: ${mangaData.status ?: "No status"}",
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(
                start = 8.dp
            )
        )
        if (!mangaData.genres.isNullOrEmpty()) CustomFlexBox(maxLines = 2) {
            mangaData.genres.forEach { genre ->
                TextItem(string = genre.name)
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


@Preview(showBackground = true)
@Composable
private fun SearchingListPreview() {
    SearchingList(
        screenState = SearchMangaState(
            titlesList = MutableList(10) { MangaStubData.mangaData },
            isLoading = true,
            haveErrors = false,
            //FIXME
            isPageLast = false
        ),
        settingsState = SearchSettings(),
        onValueChanged = {},
        onIconClicked = {},
        onPagingScroll = {},
        onItemClicked = {}
    )
}


