package com.example.addiction2dcumpose.data.database

import androidx.room.*
import com.example.addiction2dcumpose.data.database.entitys.MangaEntity
import com.example.addiction2dcumpose.data.database.entitys.MangaItemEntity
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.dataClasses.MangaItemType

@Dao
interface MangaDataDao {

    @Transaction
    suspend fun containsCheck(mangaData: MangaData): Boolean {
        getAllMangaTitles().forEach { mangaEntity ->
            if (mangaEntity.malId==mangaData.malId) return true
        }
        return false
    }

    @Query("DELETE FROM manga WHERE manga.mal_id =:titleId")
    suspend fun deleteMangaTitle(titleId: Long)

    @Query("SELECT * FROM manga")
    suspend fun getAllMangaTitles(): List<MangaEntity>

    @Query("SELECT * FROM manga_items WHERE manga_items.manga_id = :id")
    suspend fun getAllMangaItemsById(id: Long): List<MangaItemEntity>

    @Query("SELECT * FROM manga JOIN manga_items " +
            "ON manga.mal_id = manga_items.manga_id")
    suspend fun getAllMangaTitlesWithItems(): Map<MangaEntity, List<MangaItemEntity>>

    @Transaction
    suspend fun saveMangaTitle(mangaData: MangaData) {
        saveMangaData(MangaEntity.toEntity(mangaData = mangaData))

        if (!mangaData.themes.isNullOrEmpty()) {
            saveMangaItems(
                MangaItemEntity.toListEntity(
                    items = mangaData.themes,
                    mangaData = mangaData,
                    type = MangaItemType.THEMES
                )
            )
        }

        if (!mangaData.serializations.isNullOrEmpty()) {
            saveMangaItems(
                MangaItemEntity.toListEntity(
                    items = mangaData.serializations,
                    mangaData = mangaData,
                    type = MangaItemType.SERIALIZATIONS
                )
            )
        }

        if (!mangaData.genres.isNullOrEmpty()) {
            saveMangaItems(
                MangaItemEntity.toListEntity(
                    items = mangaData.genres,
                    mangaData = mangaData,
                    type = MangaItemType.GENRES
                )
            )
        }

        if (!mangaData.authors.isNullOrEmpty()) {
            saveMangaItems(
                MangaItemEntity.toListEntity(
                    items = mangaData.authors,
                    mangaData = mangaData,
                    type = MangaItemType.AUTHORS
                )
            )
        }

    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMangaData(mangaEntity: MangaEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMangaItems(items: List<MangaItemEntity>)


}