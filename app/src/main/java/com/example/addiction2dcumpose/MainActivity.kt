package com.example.addiction2dcumpose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.addiction2dcumpose.DI.randomComponent.DaggerRandomComponent
import com.example.addiction2dcumpose.mvvm.navigate.NavigateScreen
import com.example.addiction2dcumpose.mvvm.random.RandomScreen
import com.example.addiction2dcumpose.mvvm.random.RandomViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "navigate") {
                composable("navigate") { NavigateScreen(navController = navController).Screen() }
                composable("search") { }
                composable("favorite") {}
                composable("random") {

                    val randomViewModel = daggerViewModel(viewModelInstanceCreator = {
                        DaggerRandomComponent.factory()
                            .create(application.getAsAddiction().addictionComponent).getViewModel()
                    })

                    RandomScreen(viewModel = randomViewModel).Screen()
                }
            }

        }
    }



    @Composable
    private inline fun <reified T : ViewModel> daggerViewModel(
        key: String? = null,
        crossinline viewModelInstanceCreator: () -> T
    ): T =
        viewModel(
            modelClass = T::class.java,
            key = key,
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return viewModelInstanceCreator() as T
                }
            }
        )


}