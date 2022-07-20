package com.example.addiction2dcumpose.States

import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.dataClasses.SearchSettings


data class SearchMangaState(
    val searchingSettings: SearchSettings,
    val titlesList: List<MangaData>?,
    val isLoading: Boolean,
    val haveErrors: Boolean
)




