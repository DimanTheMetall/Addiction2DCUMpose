package com.example.addiction2dcumpose.mvvm.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addiction2dcumpose.States.events.ui.SettingsScreenBottomSheetEvent
import com.example.addiction2dcumpose.States.events.ui.SettingsScreenBottomSheetEvent.LoadingComplete
import com.example.addiction2dcumpose.dataClasses.*
import com.example.addiction2dcumpose.repositories.GenreRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import okhttp3.internal.toImmutableList
import javax.inject.Inject

class SettingsViewModel @Inject constructor(val genreRepository: GenreRepository) : ViewModel() {

    private val _settingsFlow = MutableStateFlow(SearchSettings())
    val settingsFlow = _settingsFlow.asStateFlow()

    private val _bottomSheetEvents =
        MutableStateFlow<SettingsScreenBottomSheetEvent>(SettingsScreenBottomSheetEvent.Loading)
    val bottomSheetEvents = _bottomSheetEvents.asStateFlow()

    private var isAddInIncludeGenres = true

    fun onInitScreen(settings: SearchSettings) {
        viewModelScope.launch {
            _settingsFlow.emit(settings)
        }
    }

    init {
        viewModelScope.launch {
            _settingsFlow.collect {
                println("AAA ${it.genresInclude?.size}")
            }
        }
    }

    fun onTypeChanged(type: SettingsType) {
        if (type !is MangaType) throw IllegalStateException("type is not MangaType")
        viewModelScope.launch {
            val newSettings = _settingsFlow.value.copy(type = type)
            _settingsFlow.emit(newSettings)
        }
    }

    fun onStatusChanged(type: SettingsType) {
        if (type !is MangaStatus) throw IllegalStateException("type is not MangaStatus")
        viewModelScope.launch {
            val newSettings = _settingsFlow.value.copy(status = type)
            _settingsFlow.emit(newSettings)
        }
    }

    fun onOrderByChanged(type: SettingsType) {
        if (type !is OrderBy) throw IllegalStateException("type is not OrderBy")
        viewModelScope.launch {
            val newSettings = _settingsFlow.value.copy(orderBy = type)
            _settingsFlow.emit(newSettings)
        }
    }

    fun onSortChanged(type: SettingsType) {
        if (type !is Sort) throw IllegalStateException("type is not Sort")
        viewModelScope.launch {
            val newSettings = _settingsFlow.value.copy(sort = type)
            _settingsFlow.emit(newSettings)
        }
    }

    fun onGenresClick(genre: Genre) {
        viewModelScope.launch {
            if (isAddInIncludeGenres) {
                if (!_settingsFlow.value.genresInclude.contains(genre)) {
                    val includeGenres = _settingsFlow.value.genresInclude.toMutableList()
                    includeGenres.add(genre)
                    val newSettings = _settingsFlow.value.copy(genresInclude = includeGenres)
                    _settingsFlow.emit(newSettings)
                }
            } else {
                if (!_settingsFlow.value.genresExclude.contains(genre)) {
                    val excludeGenres = _settingsFlow.value.genresExclude.toMutableList()
                    excludeGenres.add(genre)
                    val newSettings = _settingsFlow.value.copy(genresExclude = excludeGenres)
                    _settingsFlow.emit(newSettings)
                }
            }
        }
    }

    fun onBottomsSheetOpen(isAddIncludeClicked: Boolean) {
        viewModelScope.launch {
            isAddInIncludeGenres = isAddIncludeClicked
            when (_bottomSheetEvents.value) {
                is SettingsScreenBottomSheetEvent.Loading -> {
                    val event = loadAllGenres()
                    _bottomSheetEvents.emit(event)
                }
                is LoadingComplete -> {}
                SettingsScreenBottomSheetEvent.Error -> TODO()
            }
        }
    }

    fun onIncludeGenreClicked(genre: Genre){
        viewModelScope.launch {
            val includesGenre = _settingsFlow.value.genresInclude.toMutableList()
            includesGenre.remove(genre)
            val newSettings = _settingsFlow.value.copy(genresInclude = includesGenre)
            _settingsFlow.emit(newSettings)
        }
    }

    fun onExcludeGenreClicked(genre: Genre){
        viewModelScope.launch {
            val excludesGenre = _settingsFlow.value.genresExclude.toMutableList()
            excludesGenre.remove(genre)
            val newSettings = _settingsFlow.value.copy(genresExclude = excludesGenre)
            _settingsFlow.emit(newSettings)
        }
    }

    fun onCheckSFWClick(){
        viewModelScope.launch {
            _settingsFlow.emit(_settingsFlow.value.changeCheck())
        }
    }

    private suspend fun loadAllGenres(): SettingsScreenBottomSheetEvent.LoadingComplete {
        val result = viewModelScope.async {
            val genres = withContext(Dispatchers.Default) {
                genreRepository.loadGenres(GenresFilter.GENRES)
            }
            delay(1000)
            val explicitGenres =
                withContext(Dispatchers.Default) {
                    genreRepository.loadGenres(GenresFilter.EXPLICIT_GENRES)
                }
            delay(1000)
            val themes = withContext(Dispatchers.Default) {
                genreRepository.loadGenres(GenresFilter.THEMES)
            }
            delay(1000)
            val demographics =
                withContext(Dispatchers.Default) {
                    genreRepository.loadGenres(GenresFilter.DEMOGRAPHICS)
                }


            return@async LoadingComplete(
                genres = genres,
                explicitGenres = explicitGenres,
                themes = themes,
                demographics = demographics
            )
        }
        return result.await()
    }


}