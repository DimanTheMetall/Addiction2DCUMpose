package com.example.addiction2dcumpose.mvvm.random

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.addiction2dcumpose.BaseViewModel
import com.example.addiction2dcumpose.dataClasses.MangaResult
import com.example.addiction2dcumpose.repositories.MangaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RandomViewModel(private val mangaRepository: MangaRepository) : BaseViewModel() {

    private val _mangaLiveData = MutableLiveData<MangaResult>()
    val mangaLiveData: LiveData<MangaResult>
        get() = _mangaLiveData


    fun loadNextRandomMangaTitle(){
        viewModelScope.launch(Dispatchers.IO) {
            _mangaLiveData.value = MangaResult.Progress
            try {
                _mangaLiveData.value = MangaResult.Success(mangaRepository.loadRandomManga())
            } catch (e: Throwable){
                _mangaLiveData.value = MangaResult.Error
            }

        }
    }
}
