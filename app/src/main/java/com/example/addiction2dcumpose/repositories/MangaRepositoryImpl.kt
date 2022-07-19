package com.example.addiction2dcumpose.repositories

import com.example.addiction2dcumpose.RetrofitService.RetrofitService
import com.example.addiction2dcumpose.dataClasses.MangaReceive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(private val retrofitService: RetrofitService) :
    MangaRepository {

    override suspend fun loadRandomManga(): MangaReceive = coroutineScope  {
        withContext(Dispatchers.IO) { retrofitService.loadRandomManga() }
    }

}