package com.example.addiction2dcumpose.States

import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.dataClasses.SearchSettings


data class SearchMangaState(
    val titlesList: List<MangaData>?,
    val isLoading: Boolean,
    val haveErrors: Boolean,
    val isPageLast: Boolean
){
    fun copyWithLoading(loading: Boolean): SearchMangaState {
        return this.copy(isLoading = loading)
    }

}




