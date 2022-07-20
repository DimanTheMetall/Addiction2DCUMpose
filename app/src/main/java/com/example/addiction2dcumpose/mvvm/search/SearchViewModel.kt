package com.example.addiction2dcumpose.mvvm.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addiction2dcumpose.States.SearchMangaState
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.dataClasses.SearchSettings
import com.example.addiction2dcumpose.repositories.MangaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchViewModel @Inject constructor(private val mangaRepository: MangaRepository) :
    ViewModel() {

    private val mangaList = mutableListOf<MangaData>()
    private val _stateFlowData = MutableStateFlow(
        SearchMangaState(
            searchingSettings = SearchSettings(),
            titlesList = mangaList,
            isLoading = true,
            haveErrors = false
        )
    )
    val stateFLowData = _stateFlowData.asStateFlow()

    fun onValueChanged(newLetters: String) {
        viewModelScope.launch {
            val newSettings = _stateFlowData.value.searchingSettings.copy(letters = newLetters)
            _stateFlowData.emit(_stateFlowData.value.copy(searchingSettings = newSettings))
        }
    }


}