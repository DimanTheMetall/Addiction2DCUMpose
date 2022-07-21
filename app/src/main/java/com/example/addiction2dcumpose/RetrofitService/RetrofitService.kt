package com.example.addiction2dcumpose.RetrofitService

import com.example.addiction2dcumpose.dataClasses.MangaListReceive
import com.example.addiction2dcumpose.dataClasses.MangaReceive
import com.example.addiction2dcumpose.dataClasses.SearchSettings
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("random/manga")
    suspend fun loadRandomManga(): MangaReceive

    @GET("manga")
    suspend fun loadNewList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("q") q: String?,
        @Query("type") type: String?,
        @Query("min_score") minScore: Int?,
        @Query("max_score") maxScore: Int?,
        @Query("status") status: String?,
        @Query("sfw") sfw: Boolean,
        @Query("genres") includeGenres: String?,
        @Query("genres_exclude") genresExclude: String?,
        @Query("order_by") order_by: String?,
        @Query("sort") sort: String?,
        @Query("letters") letters: String?,
        @Query("start_date") start_date: String?,
        @Query("end_date") end_date: String?,
    ): MangaListReceive

}