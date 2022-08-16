package com.example.addiction2dcumpose.mvvm.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.repositories.MangaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MangaFavoriteViewModel @Inject constructor(private val mangaRepository: MangaRepository) :
    ViewModel() {


    private val _mangaState = MutableStateFlow<List<MangaData>?>(null)
    val mangaState = _mangaState.asStateFlow()

    init {
        onInit()
    }

    private fun onInit() {
        viewModelScope.launch {
            val data = mangaRepository.getAllMangaTitlesWithItems()
            _mangaState.emit(data)
        }
    }
}