package com.example.addiction2dcumpose.mvvm.random

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addiction2dcumpose.StubData.MangaStubData
import com.example.addiction2dcumpose.dataClasses.MangaResult
import com.example.addiction2dcumpose.repositories.MangaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RandomViewModel @Inject constructor(private val mangaRepository: MangaRepository) :
    ViewModel() {

    private val _mangaLiveData =
        MutableLiveData<MangaResult>(MangaResult.Success(MangaStubData.mangaData))
    val mangaLiveData: LiveData<MangaResult>
        get() = _mangaLiveData


    fun loadNextRandomMangaTitle() {
        viewModelScope.launch(Dispatchers.IO) {
            _mangaLiveData.postValue(MangaResult.Progress)
            try {
                _mangaLiveData.postValue(MangaResult.Success(mangaRepository.loadRandomManga().data))
            } catch (e: Throwable) {
                _mangaLiveData.postValue(MangaResult.Error)
            }
        }
    }
}
