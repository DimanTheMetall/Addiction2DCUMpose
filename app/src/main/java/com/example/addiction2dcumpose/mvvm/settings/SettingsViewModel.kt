package com.example.addiction2dcumpose.mvvm.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addiction2dcumpose.States.events.ui.SettingsScreenBottomSheetEvent
import com.example.addiction2dcumpose.States.events.ui.SettingsScreenBottomSheetEvent.LoadingComplete
import com.example.addiction2dcumpose.States.events.ui.SettingsScreenDateDialogsState
import com.example.addiction2dcumpose.States.events.ui.SettingsScreenDialogsState
import com.example.addiction2dcumpose.dataClasses.*
import com.example.addiction2dcumpose.repositories.GenreRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SettingsViewModel @Inject constructor(val genreRepository: GenreRepository) : ViewModel() {

    private val _settingsFlow = MutableStateFlow(SearchSettings())
    val settingsFlow = _settingsFlow.asStateFlow()

    private val _bottomSheetEvents =
        MutableStateFlow<SettingsScreenBottomSheetEvent>(SettingsScreenBottomSheetEvent.Loading)
    val bottomSheetEvents = _bottomSheetEvents.asStateFlow()

    private val _dialogsFlow = MutableStateFlow(
        SettingsScreenDialogsState(
            scoreDialog = false,
            maxScoreDialog = false,
            minScoreDialog = false
        )
    )
    val dialogsFlow = _dialogsFlow.asStateFlow()

    private val _dateDialogsFlow = MutableStateFlow(
        SettingsScreenDateDialogsState(
            startDate = false,
            endDate = false
        )
    )
    val dateDialogsFlow = _dateDialogsFlow.asStateFlow()

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
                    try {
                        val event = loadAllGenres()
                        _bottomSheetEvents.emit(event)
                    } catch (e: Throwable) {
                        _bottomSheetEvents.emit(SettingsScreenBottomSheetEvent.Error)
                    }

                }
                is LoadingComplete -> {
                    //Save in DB impl
                }
                is SettingsScreenBottomSheetEvent.Error -> {
                    //Maybe impl try load
                }
            }
        }
    }

    fun onTryAgainClicked() {
        viewModelScope.launch {
            try {
                _bottomSheetEvents.emit(SettingsScreenBottomSheetEvent.Loading)
                val event = loadAllGenres()
                _bottomSheetEvents.emit(event)
            } catch (e: Throwable) {
                _bottomSheetEvents.emit(SettingsScreenBottomSheetEvent.Error)
            }
        }
    }

    fun onIncludeGenreClicked(genre: Genre) {
        viewModelScope.launch {
            val includesGenre = _settingsFlow.value.genresInclude.toMutableList()
            includesGenre.remove(genre)
            val newSettings = _settingsFlow.value.copy(genresInclude = includesGenre)
            _settingsFlow.emit(newSettings)
        }
    }

    fun onExcludeGenreClicked(genre: Genre) {
        viewModelScope.launch {
            val excludesGenre = _settingsFlow.value.genresExclude.toMutableList()
            excludesGenre.remove(genre)
            val newSettings = _settingsFlow.value.copy(genresExclude = excludesGenre)
            _settingsFlow.emit(newSettings)
        }
    }

    fun onCheckSFWClick() {
        viewModelScope.launch {
            _settingsFlow.emit(_settingsFlow.value.changeCheck())
        }
    }

    fun onScoreChangeClick(scoreType: ScoreDialogType) {
        viewModelScope.launch {
            when (scoreType) {
                ScoreDialogType.SCORE -> {
                    _dialogsFlow.emit(
                        SettingsScreenDialogsState(
                            scoreDialog = true,
                            maxScoreDialog = false,
                            minScoreDialog = false
                        )
                    )

                }
                ScoreDialogType.MAX_SCORE -> {
                    _dialogsFlow.emit(
                        SettingsScreenDialogsState(
                            scoreDialog = false,
                            maxScoreDialog = true,
                            minScoreDialog = false
                        )
                    )
                }
                ScoreDialogType.MIN_SCORE -> {
                    _dialogsFlow.emit(
                        SettingsScreenDialogsState(
                            scoreDialog = false,
                            maxScoreDialog = false,
                            minScoreDialog = true
                        )
                    )
                }
            }
        }

    }

    fun onApplyNewScoreClicked(score: Int, scoreType: ScoreDialogType) {
        viewModelScope.launch {
            emitNewScore(score = score, scoreType = scoreType)
            emitCloseAllScoreDialogs()
        }
    }

    fun onCancelScoreDialogClicked() {
        viewModelScope.launch {
            emitCloseAllScoreDialogs()
        }
    }

    fun onScoreResetClick(scoreType: ScoreDialogType) {
        viewModelScope.launch {
            emitResetScore(scoreType = scoreType)
        }
    }

    fun onApplyNewDateClicked(dialogType: DateDialogType, date: SearchDate) {
        viewModelScope.launch {
            emitNewDate(date = date, dialogType = dialogType)
            emitCloseAllDateDialogs()
        }
    }

    fun onCancelDateClicked() {
        viewModelScope.launch {
            emitCloseAllDateDialogs()
        }
    }

    fun onChangeDateClicked(dialogType: DateDialogType) {
        viewModelScope.launch {
            when (dialogType) {
                DateDialogType.START_DATE -> {
                    _dateDialogsFlow.emit(
                        SettingsScreenDateDialogsState(
                            startDate = true,
                            endDate = false
                        )
                    )
                }
                DateDialogType.END_DATE -> {
                    _dateDialogsFlow.emit(
                        SettingsScreenDateDialogsState(
                            startDate = false,
                            endDate = true
                        )
                    )
                }
            }
        }
    }

    fun onResetDateClicked(dialogType: DateDialogType) {
        val currentSettings = _settingsFlow.value
        viewModelScope.launch {
            when (dialogType) {
                DateDialogType.START_DATE -> {
                    _settingsFlow.emit(currentSettings.copy(startDate = null))
                }
                DateDialogType.END_DATE -> {
                    _settingsFlow.emit(currentSettings.copy(endDate = null))
                }
            }
        }
    }

    fun onResetSortClicked() {
        viewModelScope.launch {
            val currentSettings = _settingsFlow.value
            _settingsFlow.emit(
                currentSettings.copy(
                    type = null,
                    sort = null,
                    orderBy = null,
                    status = null
                )
            )
        }
    }

    private suspend fun emitCloseAllDateDialogs() {
        _dateDialogsFlow.emit(SettingsScreenDateDialogsState(startDate = false, endDate = false))
    }

    private suspend fun emitNewDate(date: SearchDate, dialogType: DateDialogType) {
        val currentSettings = _settingsFlow.value
        when (dialogType) {
            DateDialogType.START_DATE -> {
                _settingsFlow.emit(currentSettings.copy(startDate = date))
            }
            DateDialogType.END_DATE -> {
                _settingsFlow.emit(currentSettings.copy(endDate = date))
            }
        }
    }

    private suspend fun emitNewScore(score: Int, scoreType: ScoreDialogType) {
        val currentsSettings = _settingsFlow.value
        when (scoreType) {
            ScoreDialogType.SCORE -> {
                _settingsFlow.emit(currentsSettings.copy(score = score))
            }
            ScoreDialogType.MAX_SCORE -> {
                _settingsFlow.emit(currentsSettings.copy(maxScore = score))
            }
            ScoreDialogType.MIN_SCORE -> {
                _settingsFlow.emit(currentsSettings.copy(minScore = score))
            }
        }
    }

    private suspend fun emitCloseAllScoreDialogs() {
        _dialogsFlow.emit(
            SettingsScreenDialogsState(
                scoreDialog = false,
                maxScoreDialog = false,
                minScoreDialog = false
            )
        )
    }

    private suspend fun emitResetScore(scoreType: ScoreDialogType) {
        val currentSettings = _settingsFlow.value
        when (scoreType) {
            ScoreDialogType.SCORE -> {
                _settingsFlow.emit(currentSettings.copy(score = null))
            }
            ScoreDialogType.MAX_SCORE -> {
                _settingsFlow.emit(currentSettings.copy(maxScore = null))
            }
            ScoreDialogType.MIN_SCORE -> {
                _settingsFlow.emit(currentSettings.copy(minScore = null))
            }
        }
    }

    private suspend fun loadAllGenres(): SettingsScreenBottomSheetEvent {
        val result = coroutineScope {
            async {
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
        }
        return result.await()
    }


}