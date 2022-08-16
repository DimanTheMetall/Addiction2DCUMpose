package com.example.addiction2dcumpose.repositories

import com.example.addiction2dcumpose.RetrofitService.RetrofitService
import com.example.addiction2dcumpose.StubData.MangaStubData.mangaData
import com.example.addiction2dcumpose.data.database.AddictionDataBase
import com.example.addiction2dcumpose.data.database.entitys.MangaItemEntity
import com.example.addiction2dcumpose.dataClasses.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService,
    private val dataBase: AddictionDataBase
) : MangaRepository {

    //HTTP operation

    override suspend fun loadRandomManga(): MangaReceive = coroutineScope {
        withContext(Dispatchers.IO) { retrofitService.loadRandomManga() }
    }

    override suspend fun loadMangaList(searchSettings: SearchSettings): MangaListReceive {
        return coroutineScope {
            withContext(Dispatchers.IO) {
                retrofitService.loadNewList(
                    page = searchSettings.page,
                    limit = searchSettings.limit,
                    type = searchSettings.type?.settingsName,
                    q = searchSettings.q,
                    minScore = searchSettings.minScore,
                    maxScore = searchSettings.maxScore,
                    status = searchSettings.status?.settingsName,
                    sfw = searchSettings.sfw,
                    includeGenres = searchSettings.getIncludeGenresAsString(),
                    genresExclude = searchSettings.getExcludeGenresAsString(),
                    order_by = searchSettings.orderBy?.settingsName,
                    sort = searchSettings.sort?.settingsName,
                    letters = searchSettings.letters,
                    start_date = searchSettings.startDate?.getDate(),
                    end_date = searchSettings.endDate?.getDate()
                )
            }
        }
    }

    //DataBase operation

    override suspend fun saveMangaTitle(mangaData: MangaData) {
        withContext(Dispatchers.IO) {
            dataBase.getMangaDataDao().saveMangaTitle(mangaData = mangaData)
        }
    }

    override suspend fun containsCheck(mangaData: MangaData): Boolean {
        return withContext(Dispatchers.IO) {
            dataBase.getMangaDataDao().containsCheck(mangaData = mangaData)
        }

    }

    override suspend fun deleteMangaTitle(mangaData: MangaData) {
        withContext(Dispatchers.IO) {
            dataBase.getMangaDataDao().deleteMangaTitle(mangaData.malId)
        }
    }

    override suspend fun getAllMangaTitlesWithItems(): List<MangaData> {
        val resultList = mutableListOf<MangaData>()
        val map = dataBase.getMangaDataDao().getAllMangaTitlesWithItems()
        for (mangaEntity in map.keys ) {
            val typeMap = map[mangaEntity]?.groupBy { it.type }
            val genresList = typeMap?.get(MangaItemType.GENRES.typeName)?.map { it.toMangaItem() }
            val authorsList = typeMap?.get(MangaItemType.AUTHORS.typeName)?.map { it.toMangaItem() }
            val themesList = typeMap?.get(MangaItemType.THEMES.typeName)?.map { it.toMangaItem() }
            val serializationList =
                typeMap?.get(MangaItemType.SERIALIZATIONS.typeName)?.map { it.toMangaItem() }

            val mangaData = mangaEntity.toMangaData(
                genres = genresList,
                authors = authorsList,
                themes = themesList,
                serialization = serializationList
            )
            resultList.add(mangaData)
        }

        return resultList
    }


}