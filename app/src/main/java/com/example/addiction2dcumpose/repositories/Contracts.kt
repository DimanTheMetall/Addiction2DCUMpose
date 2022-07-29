package com.example.addiction2dcumpose.repositories

import com.example.addiction2dcumpose.dataClasses.*
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

interface MangaRepository {

    suspend fun loadRandomManga(): MangaReceive

    suspend fun loadMangaList(searchSettings: SearchSettings): MangaListReceive

}

interface GenreRepository {
    suspend fun loadGenres(filter: GenresFilter): List<Genre>
}
