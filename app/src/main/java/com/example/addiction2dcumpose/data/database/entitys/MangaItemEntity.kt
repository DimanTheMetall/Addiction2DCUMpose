package com.example.addiction2dcumpose.data.database.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.addiction2dcumpose.dataClasses.MangaItemReceive

@Entity
data class MangaItemEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String
) {
    fun toMangaItem(): MangaItemReceive {
        return MangaItemReceive(id = id, name = name)
    }

    companion object {
        fun toEntity(mangaItemReceive: MangaItemReceive): MangaItemEntity {
            return MangaItemEntity(id = mangaItemReceive.id, name = mangaItemReceive.name)
        }
    }

}
