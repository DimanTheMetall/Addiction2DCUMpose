package com.example.addiction2dcumpose.repositories

import com.example.addiction2dcumpose.RetrofitService.RetrofitService
import com.example.addiction2dcumpose.dataClasses.Genre
import com.example.addiction2dcumpose.dataClasses.GenresFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenresRepositoryImpl(val retrofitService: RetrofitService) : GenreRepository {

    override suspend fun loadGenres(filter: GenresFilter): List<Genre> = withContext(Dispatchers.IO) {
        retrofitService.loadGenres(filter = filter.settingsName).genres
    }

}