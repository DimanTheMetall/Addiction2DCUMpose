package com.example.addiction2dcumpose.States.events.ui

import com.example.addiction2dcumpose.dataClasses.Genre

sealed class SettingsScreenBottomSheetEvent {
    object Loading : SettingsScreenBottomSheetEvent()
    class LoadingComplete(
        val genres: List<Genre>?,
        val explicitGenres: List<Genre>?,
        val themes: List<Genre>?,
        val demographics: List<Genre>?
    ) : SettingsScreenBottomSheetEvent()

}
