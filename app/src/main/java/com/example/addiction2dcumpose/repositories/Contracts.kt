package com.example.addiction2dcumpose.repositories

import com.example.addiction2dcumpose.dataClasses.MangaReceive
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

interface MangaRepository {

    suspend fun loadRandomManga(): MangaReceive

}
