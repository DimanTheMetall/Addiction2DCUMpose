package com.example.addiction2dcumpose.repositories

import com.example.addiction2dcumpose.RetrofitService.RetrofitService
import com.example.addiction2dcumpose.dataClasses.MangaListReceive
import com.example.addiction2dcumpose.dataClasses.MangaReceive
import com.example.addiction2dcumpose.dataClasses.SearchSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(private val retrofitService: RetrofitService) :
    MangaRepository {

    override suspend fun loadRandomManga(): MangaReceive = coroutineScope {
        withContext(Dispatchers.IO) { retrofitService.loadRandomManga() }
    }

    override suspend fun loadMangaList(searchSettings: SearchSettings): MangaListReceive {
        return coroutineScope {
            withContext(Dispatchers.IO) {
                retrofitService.loadNewList(
                    page = searchSettings.page,
                    limit = searchSettings.limit,
                    type = searchSettings.type?.typeName,
                    q = searchSettings.q,
                    minScore = searchSettings.minScore,
                    maxScore = searchSettings.maxScore,
                    status = searchSettings.status?.statusName,
                    sfw = searchSettings.sfw,
                    includeGenres = searchSettings.getIncludeGenresAsString(),
                    genresExclude = searchSettings.getExcludeGenresAsString(),
                    order_by = searchSettings.orderBy?.orderedName,
                    sort = searchSettings.sort?.sortName,
                    letters = searchSettings.letters,
                    start_date = searchSettings.startDate?.getDate(),
                    end_date = searchSettings.endDate?.getDate()
                )
            }
        }
    }

}