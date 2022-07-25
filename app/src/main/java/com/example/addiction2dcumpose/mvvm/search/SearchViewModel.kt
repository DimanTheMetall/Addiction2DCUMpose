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

    init {
        viewModelScope.launch {
            startCollectSettings()
        }
    }

    fun onValueChanged(searchText: String) {
        viewModelScope.launch {
            val newSettings = _settingsFlowState.value.copy(q = searchText)
            _settingsFlowState.emit(newSettings)
        }
    }

    private suspend fun loadNextListPart(searchSettings: SearchSettings) {
        _screenFlowState.emit(_screenFlowState.value.copyWithLoading(true))
        try {
            val receive = withContext(Dispatchers.Default) {
                mangaRepository.loadMangaList(searchSettings = searchSettings)
            }
            mangaList.addAll(receive.mangaData)
            val newState = _screenFlowState.value.copy(titlesList = mangaList.toMutableList(), isLoading = false)
            _screenFlowState.emit(newState)
        } catch (e: Throwable) {
            TODO()
        }
    }

    private suspend fun startCollectSettings() {
        _settingsFlowState.collectLatest { searchSettings ->
            delay(1000)
            mangaList.clear()
            loadNextListPart(searchSettings = searchSettings)
        }
    }
}


