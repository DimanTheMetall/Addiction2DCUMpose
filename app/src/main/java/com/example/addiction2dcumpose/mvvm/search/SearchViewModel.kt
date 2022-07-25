package com.example.addiction2dcumpose.mvvm.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addiction2dcumpose.States.SearchMangaState
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.dataClasses.SearchSettings
import com.example.addiction2dcumpose.repositories.MangaRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class SearchViewModel @Inject constructor(private val mangaRepository: MangaRepository) :
    ViewModel() {

    private val mangaList = mutableListOf<MangaData>()
    private val _screenFlowState = MutableStateFlow(
        SearchMangaState(
            titlesList = mutableListOf(),
            isLoading = true,
            haveErrors = false,
            isPageLast = false
        )
    )
    private val _settingsFlowState = MutableStateFlow(SearchSettings())

    val settingsFlowState = _settingsFlowState.asStateFlow()
    val screenFlowState = _screenFlowState.asStateFlow()


    fun onPageScrolled() {
        viewModelScope.launch {
            emmitRisenPage()
            addNextListPart()
        }
    }

    fun onTextFieldValueChanged(searchText: String) {
        viewModelScope.launch {
            val newSettings = _settingsFlowState.value.copy(q = searchText)
            _settingsFlowState.emit(newSettings)
            delay(1000)
            resetSettingsPage()
            loadNewList()
        }
    }

    private suspend fun emmitRisenPage(){
        _settingsFlowState.emit(_settingsFlowState.value.risePage())
    }

    private suspend fun loadNewList(){
        _screenFlowState.emit(_screenFlowState.value.copyWithLoading(true))
        try {
            val receive = withContext(Dispatchers.Default) {
                mangaRepository.loadMangaList(searchSettings = _settingsFlowState.value)
            }
            mangaList.clear()
            mangaList.addAll(receive.mangaData)
            val newState = _screenFlowState.value.copy(
                titlesList = mangaList.toMutableList(),
                isLoading = false
            )
            _screenFlowState.emit(newState)
        } catch (e: Throwable) {
            makeError()
        }
    }

    private suspend fun makeError () {
        val currentState = _screenFlowState.value
        val stateWithError = currentState.copy(haveErrors = true)
        _screenFlowState.apply {
            emit(stateWithError)
            emit(currentState)
        }
    }

    private suspend fun resetSettingsPage(){
        val newSettings = _settingsFlowState.value.copy(page = 1)
        _settingsFlowState.emit(newSettings)
    }

    private suspend fun addNextListPart() {
        _screenFlowState.emit(_screenFlowState.value.copyWithLoading(true))
        try {
            val receive = withContext(Dispatchers.Default) {
                mangaRepository.loadMangaList(searchSettings = _settingsFlowState.value)
            }
            mangaList.addAll(receive.mangaData)
            val newState = _screenFlowState.value.copy(
                titlesList = mangaList.toMutableList(),
                isLoading = false
            )
            _screenFlowState.emit(newState)
        } catch (e: Throwable) {
            makeError()
        }
    }


}


