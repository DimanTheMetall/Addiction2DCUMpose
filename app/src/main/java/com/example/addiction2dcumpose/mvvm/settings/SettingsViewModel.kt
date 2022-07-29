package com.example.addiction2dcumpose.mvvm.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addiction2dcumpose.States.events.ui.SettingsScreenBottomSheetEvent
import com.example.addiction2dcumpose.dataClasses.*
import com.example.addiction2dcumpose.repositories.GenreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(genreRepository: GenreRepository): ViewModel() {

    private val _settingsFlow = MutableStateFlow(SearchSettings())
    val settingsFlow = _settingsFlow.asStateFlow()

    private val _bottomSheetEvents = MutableStateFlow(SettingsScreenBottomSheetEvent.Loading)
    val bottomSheetEvents = _bottomSheetEvents.asStateFlow()

    var addInIncludeGenres = true

    fun onInitScreen(settings: SearchSettings){
        viewModelScope.launch {
            _settingsFlow.emit(settings)
        }
    }

    fun onTypeChanged(type: SettingsType){
        if (type !is MangaType) throw IllegalStateException("type is not MangaType")
        viewModelScope.launch {
            val newSettings = _settingsFlow.value.copy(type = type)
            _settingsFlow.emit(newSettings)
        }
    }

    fun onStatusChanged(type: SettingsType){
        if (type !is MangaStatus) throw IllegalStateException("type is not MangaStatus")
        viewModelScope.launch {
            val newSettings = _settingsFlow.value.copy(status = type)
            _settingsFlow.emit(newSettings)
        }
    }

    fun onOrderByChanged(type: SettingsType){
        if (type !is OrderBy) throw IllegalStateException("type is not OrderBy")
        viewModelScope.launch {
            val newSettings = _settingsFlow.value.copy(orderBy = type)
            _settingsFlow.emit(newSettings)
        }
    }

    fun onSortChanged(type: SettingsType){
        if (type !is Sort) throw IllegalStateException("type is not Sort")
        viewModelScope.launch {
            val newSettings = _settingsFlow.value.copy(sort = type)
            _settingsFlow.emit(newSettings)
        }
    }

    fun onGenresClick(genre: Genre){
        TODO()
    }

    fun onBottomsSheetOpen(){
        TODO()
    }




}