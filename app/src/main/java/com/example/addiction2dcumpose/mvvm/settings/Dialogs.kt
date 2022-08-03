package com.example.addiction2dcumpose.mvvm.settings

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.addiction2dcumpose.States.events.ui.SettingsScreenDateDialogsState
import com.example.addiction2dcumpose.States.events.ui.SettingsScreenDialogsState
import com.example.addiction2dcumpose.customLayout.DatePickerDialog
import com.example.addiction2dcumpose.customLayout.ScorePicker
import com.example.addiction2dcumpose.dataClasses.DateDialogType
import com.example.addiction2dcumpose.dataClasses.ScoreDialogType
import com.example.addiction2dcumpose.dataClasses.SearchDate
import com.example.addiction2dcumpose.dataClasses.SearchSettings

@Composable
fun ScoreDialogs(
    dialogsState: SettingsScreenDialogsState,
    settingsState: SearchSettings,
    onCancelClicked: () -> Unit,
    onApplyClicked: (Int, ScoreDialogType) -> Unit
) {
    if (dialogsState.scoreDialog) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            ScorePicker(
                modifier = Modifier.size(width = 220.dp, height = 250.dp),
                type = ScoreDialogType.SCORE,
                state = settingsState,
                onCancelClicked = { onCancelClicked.invoke() },
                onApplyClicked = { score -> onApplyClicked.invoke(score, ScoreDialogType.SCORE) }
            )
        }
    }
    if (dialogsState.maxScoreDialog) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            ScorePicker(
                modifier = Modifier.size(width = 220.dp, height = 250.dp),
                type = ScoreDialogType.MAX_SCORE,
                state = settingsState,
                onCancelClicked = { onCancelClicked.invoke() },
                onApplyClicked = { score -> onApplyClicked(score, ScoreDialogType.MAX_SCORE) }
            )
        }
    }
    if (dialogsState.minScoreDialog) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            ScorePicker(
                modifier = Modifier.size(width = 220.dp, height = 250.dp),
                type = ScoreDialogType.MIN_SCORE,
                state = settingsState,
                onCancelClicked = { onCancelClicked.invoke() },
                onApplyClicked = { score -> onApplyClicked(score, ScoreDialogType.MIN_SCORE) }
            )
        }
    }
}

@Composable
fun DateDialogs(
    settingsState: SearchSettings,
    dialogsState: SettingsScreenDateDialogsState,
    onCancelClicked: () -> Unit,
    onApplyClicked: (SearchDate, DateDialogType) -> Unit,
) {
    if (dialogsState.startDate) {
        DatePickerDialog(
            state = settingsState,
            dateType = DateDialogType.START_DATE,
            onApplyClicked = { searchDate, dialogType ->
                onApplyClicked.invoke(
                    searchDate,
                    dialogType
                )
            },
            onCancelClicked = { onCancelClicked.invoke() }
        )
    }
    if (dialogsState.endDate){
        DatePickerDialog(
            state = settingsState,
            dateType = DateDialogType.END_DATE,
            onApplyClicked = { searchDate, dialogType ->
                onApplyClicked.invoke(
                    searchDate,
                    dialogType
                )
            },
            onCancelClicked = { onCancelClicked.invoke() }
        )
    }

}
