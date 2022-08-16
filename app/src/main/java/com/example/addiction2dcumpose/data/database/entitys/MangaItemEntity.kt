package com.example.addiction2dcumpose.data.database.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.addiction2dcumpose.dataClasses.MangaData
import com.example.addiction2dcumpose.dataClasses.MangaItemReceive
import com.example.addiction2dcumpose.dataClasses.MangaItemType

@Entity(
    tableName = "manga_items",
    foreignKeys = [
        ForeignKey(
            entity = MangaEntity::class,
            parentColumns = ["mal_id"],
            childColumns = ["manga_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MangaItemEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "manga_id")
    val mangaId: Long,

    @ColumnInfo(name = "item_type")
    val type: String

) {
    fun toMangaItem(): MangaItemReceive {
        return MangaItemReceive(id = id, name = name)
    }

    companion object {
        private fun toEntity(
            mangaItemReceive: MangaItemReceive,
            mangaData: MangaData,
            type: MangaItemType
        ): MangaItemEntity {
            return MangaItemEntity(
                id = mangaItemReceive.id,
                name = mangaItemReceive.name,
                mangaId = mangaData.malId,
                type = type.typeName
            )
        }

        fun toListEntity(
            items: List<MangaItemReceive>,
            mangaData: MangaData,
            type: MangaItemType
        ): List<MangaItemEntity> {
            return items.map { toEntity(it, mangaData, type) }
        }

    }

}
