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
import com.example.addiction2dcumpose.DI.DaggerViewModelCreator
import com.example.addiction2dcumpose.DI.daggerViewModel
import com.example.addiction2dcumpose.DI.randomComponent.DaggerRandomComponent
import com.example.addiction2dcumpose.DI.searchComponent.DaggerSearchComponent
import com.example.addiction2dcumpose.mvvm.navigate.NavigateScreen
import com.example.addiction2dcumpose.mvvm.random.RandomScreen
import com.example.addiction2dcumpose.mvvm.random.RandomViewModel
import com.example.addiction2dcumpose.mvvm.search.SearchScreen
import com.example.addiction2dcumpose.mvvm.search.SearchViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), DaggerViewModelCreator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "navigate") {
                composable("navigate") { NavigateScreen(navController = navController).Screen() }
                composable("search") {
                    val searchViewModel = daggerViewModel(viewModelInstanceCreator = {
                        DaggerSearchComponent.factory()
                            .create(application.getAsAddiction().addictionComponent).getViewModel()
                    })
                    SearchScreen(
                        viewModel = searchViewModel
                    ).Screen()
                }
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





}