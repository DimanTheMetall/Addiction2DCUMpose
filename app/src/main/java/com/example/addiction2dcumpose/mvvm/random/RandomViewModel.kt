package com.example.addiction2dcumpose.mvvm.random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.States.MangaResultState
import com.example.addiction2dcumpose.States.RandomScreenButtonState
import com.example.addiction2dcumpose.repositories.MangaRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class RandomViewModel @Inject constructor(private val mangaRepository: MangaRepository) :
    ViewModel() {

    private val mangaList: MutableList<MangaData> = mutableListOf()
    private var currentIndex = -1

    private val _mangaFlowData =
        MutableStateFlow<MangaResultState>(MangaResultState.Progress)
    val mangaFLowData = _mangaFlowData.asStateFlow()

    private val _buttonsStateFlow = MutableStateFlow(
        RandomScreenButtonState(
            isBackButtonActive = false,
            isNextButtonActive = true
        )
    )
    val buttonsStateFlow = _buttonsStateFlow.asStateFlow()

    private val _favoriteFlow = MutableStateFlow(false)
    val favoriteFlow = _favoriteFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _mangaFlowData.collect { mangaResult ->
                _buttonsStateFlow.emit(
                    RandomScreenButtonState.generateButtonsState(
                        list = mangaList,
                        currentIndex = currentIndex,
                        status = mangaResult
                    )
                )
            }

        }
        onNextCLick()
    }

    private fun checkForContains(mangaData: MangaData) {
        viewModelScope.launch {
            kotlin.runCatching {
                _favoriteFlow.emit(mangaRepository.containsCheck(mangaData))
            }.onFailure { _favoriteFlow.emit(false) }
        }
    }


    fun onAddInFavoriteClicked(mangaData: MangaData) {
        viewModelScope.launch {
            kotlin.runCatching {
                mangaRepository.saveMangaTitle(mangaData = mangaData)
            }.onSuccess {
                _favoriteFlow.emit(true)
            }
        }
    }

    fun onDeleteClicked(mangaData: MangaData) {
        viewModelScope.launch {
            kotlin.runCatching {
                mangaRepository.deleteMangaTitle(mangaData = mangaData)
            }.onSuccess {
                _favoriteFlow.emit(false)
            }
        }
    }


    fun onNextCLick() {
        viewModelScope.launch {
            if (currentIndex == mangaList.lastIndex) {
                kotlin.runCatching {
                    loadNextTitle()
                    getNextTitle()
                }
                    .onFailure { _mangaFlowData.emit(MangaResultState.Error) }
            } else {
                getNextTitle()
            }
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            currentIndex--
            val data = MangaResultState.Success(mangaList[currentIndex])
            _mangaFlowData.emit(data)
            checkForContains(data.mangaData)
        }
    }



    private suspend fun getNextTitle() {
        viewModelScope.launch {
            currentIndex++
            val data = MangaResultState.Success(mangaList[currentIndex])
            _mangaFlowData.emit(data)
            checkForContains(data.mangaData)
        }
    }

    private suspend fun loadNextTitle() {
        _mangaFlowData.emit(MangaResultState.Progress)
        val result = mangaRepository.loadRandomManga().data
        mangaList.add(result)
    }


}
