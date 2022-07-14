package com.example.addiction2dcumpose.repositories

import com.example.addiction2dcumpose.dataClasses.MangaData

interface MangaRepository {

    suspend fun loadRandomManga(): MangaData

}
