package com.example.addiction2dcumpose.mvvm.random

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addiction2dcumpose.StubData.MangaStubData
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.dataClasses.MangaResult
import com.example.addiction2dcumpose.repositories.MangaRepository
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RandomViewModel @Inject constructor(private val mangaRepository: MangaRepository) :
    ViewModel() {

    private val titlesList: MutableList<MangaData> = mutableListOf()

    private val _mangaFlowData =
        MutableStateFlow<MangaResult>(MangaResult.Success(MangaStubData.mangaData))
    val mangaFLowData = _mangaFlowData.asStateFlow()


    fun loadNextRandomMangaTitle() {
        viewModelScope.launch {
            _mangaFlowData.emit(MangaResult.Progress)
            try {
                titlesList.add(mangaRepository.loadRandomManga().data)
                _mangaFlowData.emit(MangaResult.Success(titlesList.last()))
            } catch (e: Throwable) {
                _mangaFlowData.emit(MangaResult.Error)
            }
        }
    }

    fun getPreviousMangaTitle(){

    }


}
