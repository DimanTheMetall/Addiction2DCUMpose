package com.example.addiction2dcumpose.repositories

import com.example.addiction2dcumpose.RetrofitService.RetrofitService
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.dataClasses.MangaReceive
import com.example.addiction2dcumpose.dataClasses.MangaResult
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(private val retrofitService: RetrofitService) :
    MangaRepository {

    override suspend fun loadRandomManga(): MangaReceive {
        return retrofitService.loadRandomManga()
    }
}