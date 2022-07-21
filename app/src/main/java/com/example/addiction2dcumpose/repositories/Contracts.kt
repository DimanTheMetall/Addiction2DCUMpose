package com.example.addiction2dcumpose.repositories

import com.example.addiction2dcumpose.dataClasses.MangaListReceive
import com.example.addiction2dcumpose.dataClasses.MangaReceive
import com.example.addiction2dcumpose.dataClasses.SearchSettings
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

interface MangaRepository {

    suspend fun loadRandomManga(): MangaReceive

    suspend fun loadMangaList(searchSettings: SearchSettings):MangaListReceive

}
