package com.example.addiction2dcumpose.customLayout

import android.widget.NumberPicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.dataClasses.ScoreDialogType
import com.example.addiction2dcumpose.dataClasses.SearchSettings
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
    onApplyClicked: (Int) -> Unit) {

    val score = remember {
        mutableStateOf(when (type){
            ScoreDialogType.SCORE -> {
                state.score?:0
            }
            ScoreDialogType.MAX_SCORE -> {
                state.maxScore?:0
            }
            ScoreDialogType.MIN_SCORE -> {
                state.minScore?:0
            }
        })
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
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
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