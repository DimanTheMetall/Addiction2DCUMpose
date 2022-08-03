package com.example.addiction2dcumpose.customLayout

import android.app.DatePickerDialog
import android.renderscript.ScriptGroup
import android.widget.DatePicker
import android.widget.NumberPicker
import android.widget.Spinner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.compose.ui.window.Dialog
import androidx.core.view.children
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.dataClasses.DateDialogType
import com.example.addiction2dcumpose.dataClasses.ScoreDialogType
import com.example.addiction2dcumpose.dataClasses.SearchDate
import com.example.addiction2dcumpose.dataClasses.SearchSettings
import com.example.addiction2dcumpose.databinding.DatePickerBinding
import com.example.rxpractic.ui.theme.Addiction2DTheme
import com.google.android.material.datepicker.MaterialDatePicker
import kotlin.math.max

@Composable
fun CustomFlexBox(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout(content = content, modifier = modifier, measurePolicy = { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        var maxWidth = 0
        var maxHeight = placeables[0].height
        var currentWidth = 0

        placeables.forEach { placeable ->
            if (placeable.width + currentWidth > constraints.maxWidth) {
                currentWidth = 0
                maxWidth = max(a = placeable.width + currentWidth, b = maxWidth)
                maxHeight += placeable.height
            }
            currentWidth += placeable.width
        }

        layout(width = constraints.maxWidth, height = maxHeight) {
            var placeableY = 0
            var placeableX = 0

            placeables.forEach { placeable ->
                if (placeable.width + placeableX > constraints.maxWidth) {
                    placeableX = 0
                    placeableY += placeable.height
                }

                placeable.placeRelative(placeableX, placeableY)
                placeableX += placeable.width
            }

        }
    })
}

@Composable
fun ScorePicker(
    type: ScoreDialogType,
    modifier: Modifier = Modifier,
    state: SearchSettings,
    onCancelClicked: () -> Unit,
    onApplyClicked: (Int) -> Unit
) {

    val score = remember {
        mutableStateOf(
            when (type) {
                ScoreDialogType.SCORE -> {
                    state.score ?: 0
                }
                ScoreDialogType.MAX_SCORE -> {
                    state.maxScore ?: 0
                }
                ScoreDialogType.MIN_SCORE -> {
                    state.minScore ?: 0
                }
            }
        )
    }
    Card(modifier = modifier, border = BorderStroke(2.dp, MaterialTheme.colors.primary)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AndroidView(factory = { context ->
                NumberPicker(context).apply {
                    maxValue = 10
                    minValue = 0
                    value = score.value
                    this.setOnValueChangedListener { _, _, i ->
                        score.value = i
                    }
                }
            }
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { onApplyClicked.invoke(score.value) }) {
                    Text(text = stringResource(R.string.apply))
                }
                Button(onClick = { onCancelClicked.invoke() }) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        }
    }
}

@Composable
fun DatePickerDialog(
    modifier: Modifier = Modifier,
    state: SearchSettings,
    dateType: DateDialogType,
    onApplyClicked: (SearchDate, DateDialogType) -> Unit,
    onCancelClicked: () -> Unit
) {
    val date = remember {
        mutableStateOf(
            when (dateType) {
                DateDialogType.START_DATE -> {
                    state.startDate
                }
                DateDialogType.END_DATE -> {
                    state.endDate
                }
            }
        )
    }

    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card(
            modifier = modifier.size(width = 240.dp, height = 260.dp),
            border = BorderStroke(width = 2.dp, color = MaterialTheme.colors.primary)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AndroidViewBinding(DatePickerBinding::inflate, modifier = Modifier.height(180.dp)) {
                    val datePicker = this.root.children.first() as DatePicker
                    when (dateType) {
                        DateDialogType.START_DATE -> {
                            datePicker.updateDate(
                                state.startDate?.year ?: 2000,
                                state.startDate?.month ?: 1,
                                state.startDate?.day ?: 1
                            )
                            datePicker.setOnDateChangedListener { _, i, i2, i3 ->
                                date.value = SearchDate(i, i2, i3)
                            }
                        }
                        DateDialogType.END_DATE -> {
                            datePicker.updateDate(
                                state.startDate?.year ?: 2000,
                                state.startDate?.month ?: 1,
                                state.startDate?.day ?: 1
                            )
                            datePicker.setOnDateChangedListener { _, i, i2, i3 ->
                                date.value = SearchDate(i, i2, i3)
                            }
                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = {
                        onApplyClicked.invoke(
                            date.value ?: SearchDate(2000, 1, 1),
                            dateType
                        )
                    }) {
                        Text(text = stringResource(id = R.string.apply))
                    }
                    Button(onClick = { onCancelClicked.invoke() }) {
                        Text(text = stringResource(id = R.string.close))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DatePickerPreview(){
    Addiction2DTheme {
        DatePickerDialog(
            state = SearchSettings(),
            dateType = DateDialogType.START_DATE,
            onApplyClicked = {searchDate, dateDialogType ->  },
            onCancelClicked = {}
        )
    }
}


