package com.example.addiction2dcumpose.repositories

import com.example.addiction2dcumpose.dataClasses.MangaReceive

interface MangaRepository {

    suspend fun loadRandomManga(): MangaReceive

}
