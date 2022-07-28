package com.example.addiction2dcumpose.mvvm.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.dataClasses.*
import com.example.rxpractic.ui.theme.Addiction2DTheme
import com.example.rxpractic.ui.theme.Shapes

class SettingsScreen(private val viewModel: SettingsViewModel, settings: SearchSettings) {

    init {
        viewModel.onInitScreen(settings)
    }

    @Composable
    fun Screen(onSettingsChanged: (SearchSettings) -> Unit) {
        val settingsState = viewModel.settingsFlow.collectAsState()
        Settings(
            state = settingsState.value,
            onTypeChanged = { viewModel.onTypeChanged(it) },
            onStatusChanged = { viewModel.onStatusChanged(it) },
            onOrderByChanged = { viewModel.onOrderByChanged(it) },
            onSortChanged = { viewModel.onSortChanged(it) }
        )
    }

}

@Composable
private fun Settings(
    state: SearchSettings,
    onTypeChanged: (SettingsType) -> Unit,
    onStatusChanged: (SettingsType) -> Unit,
    onOrderByChanged: (SettingsType) -> Unit,
    onSortChanged: (SettingsType) -> Unit
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

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    Settings(
        state = SearchSettings(),
        onTypeChanged = { },
        onStatusChanged = { },
        onOrderByChanged = { },
        onSortChanged = {})
}

