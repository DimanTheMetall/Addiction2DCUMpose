package com.example.addiction2dcumpose.RetrofitService

import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.dataClasses.MangaReceive
import retrofit2.http.GET

interface RetrofitService {

    @GET("random/manga")
    suspend fun loadRandomManga(): MangaReceive
}