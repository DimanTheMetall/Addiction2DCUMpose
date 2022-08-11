package com.example.addiction2dcumpose.data.database.entitys

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.addiction2dcumpose.dataClasses.Images
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.dataClasses.MangaItemReceive

@Entity(tableName = "manga")
data class MangaEntity(
    @PrimaryKey
    @ColumnInfo(name = "mal_id")
    val malId: Long,

    @Embedded
    val images: Images?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "status")
    val status: String?,

    @ColumnInfo(name = "chapters")
    val chapters: Int?,

    @ColumnInfo(name = "volumes")
    val volumes: Int?,

    @ColumnInfo(name = "type")
    val type: String?,

    @ColumnInfo(name = "synopsis")
    val synopsis: String?,
) {
    fun toMangaData(
        genres: List<MangaItemReceive>?,
        authors: List<MangaItemReceive>?,
        themes: List<MangaItemReceive>?,
        serialization: List<MangaItemReceive>?
    ): MangaData {
        return MangaData(
            malId = malId,
            images = images,
            title = title,
            status = status,
            chapters = chapters,
            volumes = volumes,
            type = type,
            synopsis = synopsis,
            genres = genres,
            authors = authors,
            themes = themes,
            serializations = serialization
        )
    }

    companion object {
        fun toEntity(mangaData: MangaData): MangaEntity {
            return MangaEntity(
                malId = mangaData.malId,
                images = mangaData.images,
                title = mangaData.title,
                status = mangaData.status,
                chapters = mangaData.chapters,
                volumes = mangaData.volumes,
                type = mangaData.type,
                synopsis = mangaData.synopsis
            )
        }
    }
}
