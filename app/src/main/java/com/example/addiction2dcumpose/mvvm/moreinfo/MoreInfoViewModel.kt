package com.example.addiction2dcumpose.mvvm.moreinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.repositories.MangaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoreInfoViewModel @Inject constructor(private val mangaRepository: MangaRepository) :
    ViewModel() {

    private val _favoriteFlow = MutableStateFlow(false)
    val favoriteFlow = _favoriteFlow.asStateFlow()

    fun checkForContains(mangaData: MangaData) {
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


}