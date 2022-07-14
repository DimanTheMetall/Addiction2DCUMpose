package com.example.addiction2dcumpose.RetrofitService

import com.example.addiction2dcumpose.dataClasses.MangaData
import retrofit2.http.GET

interface RetrofitService {

    @GET("random/anime")
    suspend fun loadRandomManga(): MangaData
}