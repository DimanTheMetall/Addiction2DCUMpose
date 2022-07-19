package com.example.addiction2dcumpose.mvvm.random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.dataClasses.MangaResult
import com.example.addiction2dcumpose.dataClasses.RandomScreenButtonState
import com.example.addiction2dcumpose.repositories.MangaRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class RandomViewModel @Inject constructor(private val mangaRepository: MangaRepository) :
    ViewModel() {

    private val titlesList: MutableList<MangaData> = mutableListOf()
    private var currentIndex = -1

    private val _mangaFlowData =
        MutableStateFlow<MangaResult>(MangaResult.Progress)
    val mangaFLowData = _mangaFlowData.asStateFlow()

    private val _buttonsStateFlow = MutableStateFlow(
        RandomScreenButtonState(
            isBackButtonActive = false,
            isNextButtonActive = true
        )
    )
    val buttonsStateFlow = _buttonsStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _mangaFlowData.collect { mangaResult ->
                _buttonsStateFlow.emit(
                    RandomScreenButtonState.generateButtonsState(
                        list = titlesList,
                        currentIndex = currentIndex,
                        status = mangaResult
                    )
                )
            }

        }

        onNextCLick()
    }


    fun onNextCLick() {
        viewModelScope.launch {
            if (currentIndex == titlesList.lastIndex) {
                kotlin.runCatching {
                    loadNextTitle()
                    getNextTitle()
                }
                    .onFailure { _mangaFlowData.emit(MangaResult.Error) }

            } else {
                getNextTitle()
            }
        }

    }

    private suspend fun getNextTitle() {
        viewModelScope.launch {
            currentIndex++
            _mangaFlowData.emit(MangaResult.Success(titlesList[currentIndex]))
        }
    }

    private suspend fun loadNextTitle() {
        _mangaFlowData.emit(MangaResult.Progress)
        val result = mangaRepository.loadRandomManga().data
        titlesList.add(result)

    }

    fun onBackClick() {
        viewModelScope.launch {
            currentIndex--
            _mangaFlowData.emit(MangaResult.Success(titlesList[currentIndex]))
        }
    }


}
