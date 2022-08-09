package com.example.addiction2dcumpose.mvvm.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.customLayout.CustomFlexBox
import com.example.addiction2dcumpose.dataClasses.*
import com.example.rxpractic.ui.theme.Addiction2DTheme
import kotlinx.coroutines.launch
import java.util.*

class SettingsScreen(private val viewModel: SettingsViewModel, settings: SearchSettings) {

    init {
        viewModel.onInitScreen(settings)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Screen(onSettingsChanged: (SearchSettings) -> Unit, navController: NavController) {

        val settingsState = viewModel.settingsFlow.collectAsState()
        val bottomsSheetUi = viewModel.bottomSheetEvents.collectAsState()
        val dialogsState = viewModel.dialogsFlow.collectAsState()
        val dateDialogsState = viewModel.dateDialogsFlow.collectAsState()

        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberBottomSheetState(
                initialValue = BottomSheetValue.Collapsed
            )
        )
        val scrollState = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()

        Addiction2DTheme {
            BottomSheetScaffold(sheetContent = {
                BottomsSheetContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(color = colorResource(id = R.color.sheetBackground)),
                    onItemClick = { viewModel.onGenresClick(it) },
                    event = bottomsSheetUi.value,
                    onTryAgainClicked = { viewModel.onTryAgainClicked() }
                )
            }, scaffoldState = bottomSheetScaffoldState, sheetPeekHeight = 0.dp) { paddingValues ->
                Settings(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
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
                    },
                    onGenreExcludeIconCLick = {
                        coroutineScope.launch {
                            viewModel.onBottomsSheetOpen(false)
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    },
                    onIncludeGenreItemClicked = { genre -> viewModel.onIncludeGenreClicked(genre) },
                    onExcludeGenreItemClicked = { genre -> viewModel.onExcludeGenreClicked(genre) },
                    onCheckSFWClick = { viewModel.onCheckSFWClick() },
                    onChangeScoreClick = { scoreDialogType -> viewModel.onScoreChangeClick(scoreType = scoreDialogType) },
                    onResetScoreClicked = { scoreDialogType -> viewModel.onScoreResetClick(scoreType = scoreDialogType) },
                    onDateChangeClicked = { dateDialogType ->
                        viewModel.onChangeDateClicked(
                            dateDialogType
                        )
                    },
                    onDateResetClicked = { dateDialogType ->
                        viewModel.onResetDateClicked(
                            dateDialogType
                        )
                    },
                    onApplySettingsClicked = {
                        onSettingsChanged.invoke(settingsState.value)
                        navController.popBackStack()
                    },
                    onCancelSettingsClicked = {
                        navController.popBackStack()
                    },
                    onResetSortClicked = {
                        viewModel.onResetSortClicked()
                    }
                )
                ScoreDialogs(
                    dialogsState = dialogsState.value,
                    settingsState = settingsState.value,
                    onCancelClicked = { viewModel.onCancelScoreDialogClicked() },
                    onApplyClicked = { score, scoreDialogType ->
                        viewModel.onApplyNewScoreClicked(
                            score,
                            scoreDialogType
                        )
                    }
                )
                DateDialogs(
                    settingsState = settingsState.value,
                    dialogsState = dateDialogsState.value,
                    onCancelClicked = { viewModel.onCancelDateClicked() },
                    onApplyClicked = { searchDate, dateDialogType ->
                        viewModel.onApplyNewDateClicked(
                            dialogType = dateDialogType,
                            date = searchDate
                        )
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
    onExcludeGenresIconCLick: () -> Unit,
    onIncludeGenreItemClicked: (Genre) -> Unit,
    onExcludeGenreItemClicked: (Genre) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(id = R.string.genres),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomFlexBox {
                state.genresInclude.forEach { genre ->
                    Card(
                        backgroundColor = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable {
                                onIncludeGenreItemClicked.invoke(genre)
                            }
                    ) {
                        Text(text = genre.name, modifier = Modifier.padding(2.dp))
                    }
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_box),
                    tint = MaterialTheme.colors.primary,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onGenresIconCLick.invoke() }
                        .size(30.dp)
                )
            }
        }
        Text(
            text = stringResource(id = R.string.explicit_genres),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary
        )
        Row {
            CustomFlexBox {
                state.genresExclude.forEach { genre ->
                    Card(
                        backgroundColor = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable {
                                onExcludeGenreItemClicked.invoke(genre)
                            }
                    ) {
                        Text(text = genre.name, modifier = Modifier.padding(2.dp))
                    }
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_box),
                    tint = MaterialTheme.colors.primary,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onExcludeGenresIconCLick.invoke() }
                        .size(30.dp)
                )
            }
        }
    }
}

@Composable
private fun Settings(
    modifier: Modifier = Modifier,
    state: SearchSettings,
    onTypeChanged: (SettingsType) -> Unit,
    onStatusChanged: (SettingsType) -> Unit,
    onOrderByChanged: (SettingsType) -> Unit,
    onSortChanged: (SettingsType) -> Unit,
    onGenresIncludeIconCLick: () -> Unit,
    onGenreExcludeIconCLick: () -> Unit,
    onIncludeGenreItemClicked: (Genre) -> Unit,
    onExcludeGenreItemClicked: (Genre) -> Unit,
    onCheckSFWClick: () -> Unit,
    onChangeScoreClick: (ScoreDialogType) -> Unit,
    onResetScoreClicked: (ScoreDialogType) -> Unit,
    onDateChangeClicked: (DateDialogType) -> Unit,
    onDateResetClicked: (DateDialogType) -> Unit,
    onApplySettingsClicked: () -> Unit,
    onCancelSettingsClicked: () -> Unit,
    onResetSortClicked: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val spacerModifier = Modifier.height(16.dp)
    Addiction2DTheme {
        Column(
            modifier = modifier,
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
            Button(onClick = { onResetSortClicked.invoke() }) {
                Text(text = stringResource(id = R.string.reset))
            }
            Spacer(modifier = spacerModifier)
            Text(
                text = stringResource(id = R.string.genres_settings),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(26.dp))
            GenresTextField(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                onGenresIconCLick = {
                    onGenresIncludeIconCLick.invoke()
                },
                onExcludeGenresIconCLick = {
                    onGenreExcludeIconCLick.invoke()
                },
                onIncludeGenreItemClicked = { onIncludeGenreItemClicked.invoke(it) },
                onExcludeGenreItemClicked = { onExcludeGenreItemClicked.invoke(it) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.sfw,
                    onCheckedChange = { onCheckSFWClick.invoke() },
                    modifier = Modifier.size(30.dp),
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colors.primary,
                        uncheckedColor = MaterialTheme.colors.primary
                    )
                )
                Text(text = stringResource(R.string.sfw), color = MaterialTheme.colors.primary)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.score),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.primary
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                ScoreColumn(
                    modifier = Modifier.width(140.dp),
                    type = ScoreDialogType.SCORE,
                    state = state,
                    onChangeClick = { onChangeScoreClick.invoke(ScoreDialogType.SCORE) },
                    onResetClicked = { onResetScoreClicked.invoke(ScoreDialogType.SCORE) }
                )
                ScoreColumn(
                    modifier = Modifier.width(140.dp),
                    type = ScoreDialogType.MAX_SCORE,
                    state = state,
                    onChangeClick = { onChangeScoreClick.invoke(ScoreDialogType.MAX_SCORE) },
                    onResetClicked = { onResetScoreClicked.invoke(ScoreDialogType.MAX_SCORE) }
                )
                ScoreColumn(
                    modifier = Modifier.width(140.dp),
                    type = ScoreDialogType.MIN_SCORE,
                    state = state,
                    onChangeClick = { onChangeScoreClick.invoke(ScoreDialogType.MIN_SCORE) },
                    onResetClicked = { onResetScoreClicked.invoke(ScoreDialogType.MIN_SCORE) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.date),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.primary
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                DateColumn(
                    settings = state,
                    dialogType = DateDialogType.START_DATE,
                    onChangeClick = { dateDialogType -> onDateChangeClicked.invoke(dateDialogType) },
                    onResetCLicked = { dateDialogType -> onDateResetClicked.invoke(dateDialogType) }
                )
                DateColumn(
                    settings = state,
                    dialogType = DateDialogType.END_DATE,
                    onChangeClick = { dateDialogType -> onDateChangeClicked.invoke(dateDialogType) },
                    onResetCLicked = { dateDialogType -> onDateResetClicked.invoke(dateDialogType) }
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { onCancelSettingsClicked.invoke() }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                Button(onClick = { onApplySettingsClicked.invoke() }) {
                    Text(text = stringResource(id = R.string.apply))
                }
            }


        }
    }
}

@Composable
private fun DateColumn(
    modifier: Modifier = Modifier,
    settings: SearchSettings,
    dialogType: DateDialogType,
    onChangeClick: (DateDialogType) -> Unit,
    onResetCLicked: (DateDialogType) -> Unit
) {
    val date = when (dialogType) {
        DateDialogType.START_DATE -> {
            settings.startDate?.getDate() ?: "No"
        }
        DateDialogType.END_DATE -> {
            settings.endDate?.getDate() ?: "No"
        }
    }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = dialogType.dateName,
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primary
        )
        Text(text = date, style = MaterialTheme.typography.h5)
        Button(
            onClick = { onChangeClick.invoke(dialogType) },
            shape = RoundedCornerShape(corner = CornerSize(12.dp))
        ) {
            Text(text = stringResource(id = R.string.change))
        }
        Button(
            onClick = { onResetCLicked.invoke(dialogType) },
            shape = RoundedCornerShape(corner = CornerSize(12.dp))
        ) {
            Text(text = stringResource(id = R.string.reset))
        }
    }
}

@Composable
private fun ScoreColumn(
    modifier: Modifier = Modifier,
    type: ScoreDialogType,
    state: SearchSettings,
    onChangeClick: () -> Unit,
    onResetClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val score = when (type) {
            ScoreDialogType.SCORE -> state.score ?: "No"
            ScoreDialogType.MAX_SCORE -> state.maxScore ?: "No"
            ScoreDialogType.MIN_SCORE -> state.minScore ?: "No"
        }
        Text(
            text = type.scoreName,
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primary
        )
        Text(text = score.toString(), style = MaterialTheme.typography.h6)
        Button(
            onClick = { onChangeClick.invoke() },
            shape = RoundedCornerShape(corner = CornerSize(12.dp))
        ) {
            Text(text = stringResource(R.string.change))
        }
        Button(
            onClick = { onResetClicked.invoke() },
            shape = RoundedCornerShape(corner = CornerSize(12.dp))
        ) {
            Text(text = stringResource(R.string.reset))
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
        },
            onDismissRequest = { state.value = false })
    }
}

@Composable
private fun <T : SettingsType> DropMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    list: Array<T>,
    onSelectType: (SettingsType) -> Unit,
    textStyle: TextStyle = TextStyle(),
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = { onDismissRequest.invoke() }) {
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


@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    Settings(
        modifier = Modifier.fillMaxSize(),
        state = SearchSettings(),
        onTypeChanged = { },
        onStatusChanged = { },
        onOrderByChanged = { },
        onSortChanged = {},
        onGenresIncludeIconCLick = {},
        onGenreExcludeIconCLick = {},
        onExcludeGenreItemClicked = {},
        onIncludeGenreItemClicked = {},
        onCheckSFWClick = {},
        onResetScoreClicked = {},
        onChangeScoreClick = {},
        onDateResetClicked = {},
        onDateChangeClicked = {},
        onApplySettingsClicked = {},
        onCancelSettingsClicked = {},
        onResetSortClicked = {})
}

