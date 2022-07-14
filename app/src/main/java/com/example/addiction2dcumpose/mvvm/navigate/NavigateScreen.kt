package com.example.addiction2dcumpose.mvvm.navigate

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.addiction2dcumpose.R
import com.example.addiction2dcumpose.mvvm.random.RandomScreen
import com.example.rxpractic.ui.theme.Addiction2DTheme


class NavigateScreen {

    @Composable
    fun Screen() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "navigate") {
            composable("navigate") { Navigate(navController = navController) }
            composable("search") { }
            composable("favorite") {}
            composable("random") { RandomScreen().Screen() }
        }

    }


    @Composable
    private fun Navigate(navController: NavController) {
        Addiction2DTheme {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(width = 310.dp, height = 20.dp))

                ChooseCard(title = R.string.search) {

                }

                ChooseCard(title = R.string.favorite) {

                }

                ChooseCard(title = R.string.random) {
                    navController.navigate("random")
                }
            }
        }
    }

    @Composable
    private fun ChooseCard(
        modifier: Modifier = Modifier,
        @StringRes title: Int,
        onClick: () -> Unit
    ) {
        OutlinedButton(
            modifier = modifier.size(width = 310.dp, height = 140.dp),
            onClick = onClick,
            border = BorderStroke(2.dp, Color.Red),
            shape = RoundedCornerShape(corner = CornerSize(20.dp))
        ) {
            Text(text = stringResource(id = title), style = MaterialTheme.typography.h4)
        }
    }


}